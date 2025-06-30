package com.dao;

import com.entity.ChartData;
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

public class ChartDataDAO {

    private static final Logger LOGGER = Logger.getLogger(ChartDataDAO.class.getName());

    // Create Operations
    public void saveChartData(ChartData chartData) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(chartData);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving chart data: " + chartData, e);
        }
    }

    public void saveAllChartData(List<ChartData> chartDataList) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (ChartData chart : chartDataList) {
                session.save(chart);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving chart data list", e);
        }
    }

    // Read Operations
    public Optional<ChartData> findById(Long chartId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ChartData chartData = session.get(ChartData.class, chartId);
            return Optional.ofNullable(chartData);
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding chart data by ID: " + chartId, he);
            return Optional.empty();
        }
    }

    public List<ChartData> getAllChartData() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM ChartData ORDER BY generatedAt DESC", ChartData.class).list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all chart data", e);
            return List.of();
        }
    }

    public List<ChartData> findByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ChartData> query = session.createQuery(
                    "FROM ChartData c WHERE c.user.userId = :userId ORDER BY c.generatedAt DESC",
                    ChartData.class
            );
            query.setParameter("userId", userId);
            return query.list();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding chart data for user: " + userId, he);
            return List.of();
        }
    }

    public List<ChartData> findByUserIdAndChartType(Long userId, ChartData.ChartType chartType) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ChartData> query = session.createQuery(
                    "FROM ChartData c WHERE c.user.userId = :userId AND c.chartType = :chartType ORDER BY c.generatedAt DESC",
                    ChartData.class
            );
            query.setParameter("userId", userId);
            query.setParameter("chartType", chartType);
            return query.list();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding chart data for user " + userId + " and type " + chartType, he);
            return List.of();
        }
    }

    public Optional<ChartData> findByUserIdAndTimeRange(Long userId, String timeRange) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ChartData> query = session.createQuery(
                    "FROM ChartData c WHERE c.user.userId = :userId AND c.timeRange = :timeRange ORDER BY c.generatedAt DESC",
                    ChartData.class
            );
            query.setParameter("userId", userId);
            query.setParameter("timeRange", timeRange);
            query.setMaxResults(1);
            return query.uniqueResultOptional();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding chart data for user " + userId + " and time range " + timeRange, he);
            return Optional.empty();
        }
    }

    public List<ChartData> findValidCachedCharts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ChartData> query = session.createQuery(
                    "FROM ChartData c WHERE c.isCached = true AND c.cacheExpiresAt > :now ORDER BY c.generatedAt DESC",
                    ChartData.class
            );
            query.setParameter("now", LocalDateTime.now());
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding valid cached chart data", e);
            return List.of();
        }
    }

    public List<ChartData> findByChartType(ChartData.ChartType chartType) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ChartData> query = session.createQuery(
                    "FROM ChartData c WHERE c.chartType = :chartType ORDER BY c.generatedAt DESC",
                    ChartData.class
            );
            query.setParameter("chartType", chartType);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding chart data by type: " + chartType, e);
            return List.of();
        }
    }

    public List<ChartData> findRealtimeCharts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ChartData> query = session.createQuery(
                    "FROM ChartData c WHERE c.isRealtime = true ORDER BY c.generatedAt DESC",
                    ChartData.class
            );
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding realtime chart data", e);
            return List.of();
        }
    }

    // Analytics Queries for Dashboard Service Layer
    public Long getTotalChartsForUser(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(c) FROM ChartData c WHERE c.user.userId = :userId",
                    Long.class
            );
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting total charts for user: " + userId, e);
            return 0L;
        }
    }

    public List<Object[]> getChartTypeDistribution() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT c.chartType, COUNT(c) FROM ChartData c GROUP BY c.chartType ORDER BY COUNT(c) DESC",
                    Object[].class
            );
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting chart type distribution", e);
            return List.of();
        }
    }

    public List<Object[]> getChartUsageByTimeRange() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT c.timeRange, COUNT(c), AVG(c.totalDataPoints) FROM ChartData c " +
                            "GROUP BY c.timeRange ORDER BY COUNT(c) DESC",
                    Object[].class
            );
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting chart usage by time range", e);
            return List.of();
        }
    }

    public List<Object[]> getTopChartsByDataPoints(int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT c.chartTitle, c.chartType, c.totalDataPoints, c.user.username " +
                            "FROM ChartData c WHERE c.totalDataPoints IS NOT NULL " +
                            "ORDER BY c.totalDataPoints DESC",
                    Object[].class
            );
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting top charts by data points", e);
            return List.of();
        }
    }

    // Update Operations
    public void updateChartData(ChartData chartData) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(chartData);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error updating chart data: " + chartData, e);
        }
    }

    public void markCacheExpired(Long chartId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                    "UPDATE ChartData c SET c.isCached = false, c.cacheExpiresAt = null WHERE c.chartId = :chartId"
            );
            query.setParameter("chartId", chartId);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error marking cache expired for chart ID: " + chartId, e);
        }
    }

    public void markAllCacheExpiredForUser(Long userId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                    "UPDATE ChartData c SET c.isCached = false, c.cacheExpiresAt = null WHERE c.user.userId = :userId"
            );
            query.setParameter("userId", userId);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error marking all cache expired for user ID: " + userId, e);
        }
    }

    // Delete Operations
    public void deleteChartData(Long chartId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            ChartData chartData = session.get(ChartData.class, chartId);
            if (chartData != null) {
                session.delete(chartData);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting chart data with ID: " + chartId, e);
        }
    }

    public int deleteExpiredCache() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                    "DELETE FROM ChartData c WHERE c.isCached = true AND c.cacheExpiresAt < :now"
            );
            query.setParameter("now", LocalDateTime.now());
            int deleted = query.executeUpdate();
            transaction.commit();
            return deleted;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting expired cache", e);
            return 0;
        }
    }

    public int deleteOldChartsForUser(Long userId, int daysToKeep) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysToKeep);
            Query query = session.createQuery(
                    "DELETE FROM ChartData c WHERE c.user.userId = :userId AND c.generatedAt < :cutoffDate"
            );
            query.setParameter("userId", userId);
            query.setParameter("cutoffDate", cutoffDate);
            int deleted = query.executeUpdate();
            transaction.commit();
            return deleted;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting old charts for user: " + userId, e);
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
                    "SELECT COUNT(c) FROM ChartData c WHERE c.user.userId = :userId",
                    Long.class
            );
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting chart data for user: " + userId, e);
            return 0L;
        }
    }

    public List<ChartData> findRecentByUserId(Long userId, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ChartData> query = session.createQuery(
                    "FROM ChartData c WHERE c.user.userId = :userId ORDER BY c.generatedAt DESC",
                    ChartData.class
            );
            query.setParameter("userId", userId);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding recent chart data for user: " + userId, e);
            return List.of();
        }
    }

    // Pagination Support
    public List<ChartData> findWithPagination(int offset, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ChartData> query = session.createQuery(
                    "FROM ChartData ORDER BY generatedAt DESC",
                    ChartData.class
            );
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding chart data with pagination", e);
            return List.of();
        }
    }

    public long getTotalCount() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(c) FROM ChartData c",
                    Long.class
            );
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting total chart count", e);
            return 0L;
        }
    }
}
