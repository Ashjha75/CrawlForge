package com.dao;

import com.entity.CrawlStatistics;
import com.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrawlStatisticsDAO {

    private static final Logger LOGGER = Logger.getLogger(CrawlStatisticsDAO.class.getName());

    // Create Operations
    public void saveCrawlStatistics(CrawlStatistics crawlStatistics) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(crawlStatistics);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving crawl statistics: " + crawlStatistics, e);
        }
    }

    public void saveAllCrawlStatistics(List<CrawlStatistics> statisticsList) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (CrawlStatistics stats : statisticsList) {
                session.save(stats);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving crawl statistics list", e);
        }
    }

    // Read Operations
    public Optional<CrawlStatistics> findById(Long statisticsId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CrawlStatistics statistics = session.get(CrawlStatistics.class, statisticsId);
            return Optional.ofNullable(statistics);
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding crawl statistics by ID: " + statisticsId, he);
            return Optional.empty();
        }
    }

    public List<CrawlStatistics> getAllCrawlStatistics() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM CrawlStatistics ORDER BY generatedAt DESC", CrawlStatistics.class).list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all crawl statistics", e);
            return List.of();
        }
    }

    public List<CrawlStatistics> findByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CrawlStatistics> query = session.createQuery(
                    "FROM CrawlStatistics s WHERE s.user.userId = :userId ORDER BY s.generatedAt DESC",
                    CrawlStatistics.class
            );
            query.setParameter("userId", userId);
            return query.list();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding crawl statistics for user: " + userId, he);
            return List.of();
        }
    }

    public Optional<CrawlStatistics> findLatestByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CrawlStatistics> query = session.createQuery(
                    "FROM CrawlStatistics s WHERE s.user.userId = :userId ORDER BY s.generatedAt DESC",
                    CrawlStatistics.class
            );
            query.setParameter("userId", userId);
            query.setMaxResults(1);
            return query.uniqueResultOptional();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding latest crawl statistics for user: " + userId, he);
            return Optional.empty();
        }
    }

    public Optional<CrawlStatistics> findByUserIdAndTimeRange(Long userId, String timeRange) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CrawlStatistics> query = session.createQuery(
                    "FROM CrawlStatistics s WHERE s.user.userId = :userId AND s.timeRange = :timeRange ORDER BY s.generatedAt DESC",
                    CrawlStatistics.class
            );
            query.setParameter("userId", userId);
            query.setParameter("timeRange", timeRange);
            query.setMaxResults(1);
            return query.uniqueResultOptional();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding crawl statistics for user " + userId + " and time range " + timeRange, he);
            return Optional.empty();
        }
    }

    public List<CrawlStatistics> findByStatisticsType(String statisticsType) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CrawlStatistics> query = session.createQuery(
                    "FROM CrawlStatistics s WHERE s.statisticsType = :statisticsType ORDER BY s.generatedAt DESC",
                    CrawlStatistics.class
            );
            query.setParameter("statisticsType", statisticsType);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding crawl statistics by type: " + statisticsType, e);
            return List.of();
        }
    }

    public List<CrawlStatistics> findByTimeRange(String timeRange) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CrawlStatistics> query = session.createQuery(
                    "FROM CrawlStatistics s WHERE s.timeRange = :timeRange ORDER BY s.generatedAt DESC",
                    CrawlStatistics.class
            );
            query.setParameter("timeRange", timeRange);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding crawl statistics by time range: " + timeRange, e);
            return List.of();
        }
    }

    // Analytics Queries for Dashboard Service Layer
    public Long getTotalSessionsAcrossAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COALESCE(SUM(s.totalSessions), 0L) FROM CrawlStatistics s",
                    Long.class
            );
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting total sessions across all users", e);
            return 0L;
        }
    }

    public Double getGlobalAverageSuccessRate() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Double> query = session.createQuery(
                    "SELECT COALESCE(AVG(s.overallSuccessRate), 0.0) FROM CrawlStatistics s WHERE s.overallSuccessRate IS NOT NULL",
                    Double.class
            );
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting global average success rate", e);
            return 0.0;
        }
    }

    public List<Object[]> getTopPerformingUsers(int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT s.user.userId, s.user.username, s.overallSuccessRate, s.totalPagesCrawled " +
                            "FROM CrawlStatistics s WHERE s.overallSuccessRate IS NOT NULL " +
                            "ORDER BY s.overallSuccessRate DESC, s.totalPagesCrawled DESC",
                    Object[].class
            );
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting top performing users", e);
            return List.of();
        }
    }

    public List<Object[]> getDomainStatistics() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT s.mostCrawledDomain, SUM(s.mostCrawledDomainCount), AVG(s.averageLoadTimeMs) " +
                            "FROM CrawlStatistics s WHERE s.mostCrawledDomain IS NOT NULL " +
                            "GROUP BY s.mostCrawledDomain ORDER BY SUM(s.mostCrawledDomainCount) DESC",
                    Object[].class
            );
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting domain statistics", e);
            return List.of();
        }
    }

    public List<Object[]> getTimeBasedTrends(String timeRange) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT DATE(s.generatedAt), SUM(s.totalSessions), SUM(s.totalPagesCrawled), AVG(s.overallSuccessRate) " +
                            "FROM CrawlStatistics s WHERE s.timeRange = :timeRange " +
                            "GROUP BY DATE(s.generatedAt) ORDER BY DATE(s.generatedAt) DESC",
                    Object[].class
            );
            query.setParameter("timeRange", timeRange);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting time-based trends for range: " + timeRange, e);
            return List.of();
        }
    }

    // Update Operations
    public void updateCrawlStatistics(CrawlStatistics crawlStatistics) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(crawlStatistics);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error updating crawl statistics: " + crawlStatistics, e);
        }
    }

    // Delete Operations
    public void deleteCrawlStatistics(Long statisticsId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CrawlStatistics statistics = session.get(CrawlStatistics.class, statisticsId);
            if (statistics != null) {
                session.delete(statistics);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting crawl statistics with ID: " + statisticsId, e);
        }
    }

    public int deleteOldStatistics(int daysToKeep) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysToKeep);
            Query query = session.createQuery(
                    "DELETE FROM CrawlStatistics s WHERE s.generatedAt < :cutoffDate"
            );
            query.setParameter("cutoffDate", cutoffDate);
            int deleted = query.executeUpdate();
            transaction.commit();
            return deleted;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting old statistics", e);
            return 0;
        }
    }
    // src/main/java/com/entity/CrawlStatistics.java
    public static CrawlStatistics empty(Long userId) {
        return CrawlStatistics.builder()
            .user(null) // or fetch user by userId if needed
            .totalSessions(0L)
            .activeSessions(0L)
            .completedSessions(0L)
            .failedSessions(0L)
            .totalPagesCrawled(0L)
            .totalPagesFailed(0L)
            .totalPagesSuccessful(0L)
            .overallSuccessRate(0.0)
            .averagePagesPerSession(0.0)
            .averageLoadTimeMs(0.0)
            .totalKeywordsExtracted(0L)
            .totalLinksFound(0L)
            .crawlsLast24Hours(0L)
            .crawlsLast7Days(0L)
            .crawlsLast30Days(0L)
            .timeRange(null)
            .statisticsType("USER")
            .dataVersion(1)
            .generatedAt(java.time.LocalDateTime.now())
            .build();
    }

    public int deleteByUserId(Long userId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                    "DELETE FROM CrawlStatistics s WHERE s.user.userId = :userId"
            );
            query.setParameter("userId", userId);
            int deleted = query.executeUpdate();
            transaction.commit();
            return deleted;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting statistics for user: " + userId, e);
            return 0;
        }
    }

    // Utility Methods
    public boolean existsByUserIdAndTimeRange(Long userId, String timeRange) {
        try {
            return findByUserIdAndTimeRange(userId, timeRange).isPresent();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error checking existence for user " + userId + " and time range " + timeRange, e);
            return false;
        }
    }

    public long countByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(s) FROM CrawlStatistics s WHERE s.user.userId = :userId",
                    Long.class
            );
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting statistics for user: " + userId, e);
            return 0L;
        }
    }

    public List<CrawlStatistics> findRecentByUserId(Long userId, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CrawlStatistics> query = session.createQuery(
                    "FROM CrawlStatistics s WHERE s.user.userId = :userId ORDER BY s.generatedAt DESC",
                    CrawlStatistics.class
            );
            query.setParameter("userId", userId);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding recent statistics for user: " + userId, e);
            return List.of();
        }
    }

    // Pagination Support
    public List<CrawlStatistics> findWithPagination(int offset, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CrawlStatistics> query = session.createQuery(
                    "FROM CrawlStatistics ORDER BY generatedAt DESC",
                    CrawlStatistics.class
            );
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding statistics with pagination", e);
            return List.of();
        }
    }

    public long getTotalCount() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(s) FROM CrawlStatistics s",
                    Long.class
            );
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting total statistics count", e);
            return 0L;
        }
    }
}
