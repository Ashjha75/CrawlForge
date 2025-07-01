package com.dao;

import com.entity.CrawlSession;
import com.entity.User;
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

public class CrawlSessionDAO {

    private static final Logger LOGGER = Logger.getLogger(CrawlSessionDAO.class.getName());

    // Create Operations
    public void saveCrawlSession(CrawlSession crawlSession) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(crawlSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving crawl session: " + crawlSession, e);
        }
    }

    // Read Operations
    public Optional<CrawlSession> findById(Long sessionId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CrawlSession crawlSession = session.get(CrawlSession.class, sessionId);
            return Optional.ofNullable(crawlSession);
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding crawl session by ID: " + sessionId, he);
            return Optional.empty();
        }
    }

    public List<CrawlSession> getAllCrawlSessions() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM CrawlSession ORDER BY createdAt DESC", CrawlSession.class).list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all crawl sessions", e);
            return List.of();
        }
    }

    public List<CrawlSession> findByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CrawlSession> query = session.createQuery(
                    "FROM CrawlSession cs WHERE cs.user.userId = :userId ORDER BY cs.createdAt DESC",
                    CrawlSession.class
            );
            query.setParameter("userId", userId);
            return query.list();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding crawl sessions for user: " + userId, he);
            return List.of();
        }
    }

    public List<CrawlSession> findByStatus(CrawlSession.CrawlStatus status) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CrawlSession> query = session.createQuery(
                    "FROM CrawlSession cs WHERE cs.status = :status ORDER BY cs.createdAt DESC",
                    CrawlSession.class
            );
            query.setParameter("status", status);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding crawl sessions by status: " + status, e);
            return List.of();
        }
    }

    public List<CrawlSession> findActiveSessions() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CrawlSession> query = session.createQuery(
                    "FROM CrawlSession cs WHERE cs.status IN (:statuses) ORDER BY cs.startedAt DESC",
                    CrawlSession.class
            );
            query.setParameterList("statuses", List.of(CrawlSession.CrawlStatus.RUNNING, CrawlSession.CrawlStatus.PAUSED));
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding active crawl sessions", e);
            return List.of();
        }
    }

    public List<CrawlSession> findByUserIdAndStatus(Long userId, CrawlSession.CrawlStatus status) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CrawlSession> query = session.createQuery(
                    "FROM CrawlSession cs WHERE cs.user.userId = :userId AND cs.status = :status ORDER BY cs.createdAt DESC",
                    CrawlSession.class
            );
            query.setParameter("userId", userId);
            query.setParameter("status", status);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding crawl sessions for user " + userId + " with status " + status, e);
            return List.of();
        }
    }

    public List<CrawlSession> findRecentSessions(int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CrawlSession> query = session.createQuery(
                    "FROM CrawlSession cs ORDER BY cs.createdAt DESC",
                    CrawlSession.class
            );
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding recent crawl sessions", e);
            return List.of();
        }
    }

    public List<CrawlSession> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CrawlSession> query = session.createQuery(
                    "FROM CrawlSession cs WHERE cs.createdAt BETWEEN :startDate AND :endDate ORDER BY cs.createdAt DESC",
                    CrawlSession.class
            );
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding crawl sessions by date range", e);
            return List.of();
        }
    }

    // Update Operations
    public void updateCrawlSession(CrawlSession crawlSession) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(crawlSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error updating crawl session: " + crawlSession, e);
        }
    }

    public void updateSessionStatus(Long sessionId, CrawlSession.CrawlStatus status) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                    "UPDATE CrawlSession cs SET cs.status = :status WHERE cs.sessionId = :sessionId"
            );
            query.setParameter("status", status);
            query.setParameter("sessionId", sessionId);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error updating session status for ID: " + sessionId, e);
        }
    }

    public void updateSessionProgress(Long sessionId, Integer pagesCrawled, Integer pagesFailed, Integer totalLinksFound) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                    "UPDATE CrawlSession cs SET cs.pagesCrawled = :pagesCrawled, cs.pagesFailed = :pagesFailed, cs.totalLinksFound = :totalLinksFound WHERE cs.sessionId = :sessionId"
            );
            query.setParameter("pagesCrawled", pagesCrawled);
            query.setParameter("pagesFailed", pagesFailed);
            query.setParameter("totalLinksFound", totalLinksFound);
            query.setParameter("sessionId", sessionId);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error updating session progress for ID: " + sessionId, e);
        }
    }

    // Delete Operations
    public void deleteCrawlSession(Long sessionId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CrawlSession crawlSession = session.get(CrawlSession.class, sessionId);
            if (crawlSession != null) {
                session.delete(crawlSession);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting crawl session with ID: " + sessionId, e);
        }
    }

    // Analytics and Statistics Methods
    public long countByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(cs) FROM CrawlSession cs WHERE cs.user.userId = :userId",
                    Long.class
            );
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting sessions for user: " + userId, e);
            return 0L;
        }
    }

    public long countByStatus(CrawlSession.CrawlStatus status) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(cs) FROM CrawlSession cs WHERE cs.status = :status",
                    Long.class
            );
            query.setParameter("status", status);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting sessions by status: " + status, e);
            return 0L;
        }
    }

    public Double getAverageSuccessRateByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT SUM(cs.pagesCrawled), SUM(cs.pagesFailed) FROM CrawlSession cs WHERE cs.user.userId = :userId",
                    Object[].class
            );
            query.setParameter("userId", userId);
            Object[] result = query.uniqueResult();

            if (result != null && result[0] != null) {
                Long totalCrawled = ((Number) result[0]).longValue();
                Long totalFailed = result[1] != null ? ((Number) result[1]).longValue() : 0L;

                if (totalCrawled > 0) {
                    return ((double) (totalCrawled - totalFailed) / totalCrawled) * 100;
                }
            }
            return 0.0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error calculating average success rate for user: " + userId, e);
            return 0.0;
        }
    }

    public List<Object[]> getSessionStatsByUser() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT cs.user.userId, cs.user.username, COUNT(cs), AVG(cs.pagesCrawled), MAX(cs.createdAt) " +
                            "FROM CrawlSession cs GROUP BY cs.user.userId, cs.user.username ORDER BY COUNT(cs) DESC",
                    Object[].class
            );
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting session stats by user", e);
            return List.of();
        }
    }

    // Utility Methods
    public boolean sessionExists(Long sessionId) {
        try {
            return findById(sessionId).isPresent();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error checking if session exists: " + sessionId, e);
            return false;
        }
    }

    public boolean userHasActiveSessions(Long userId) {
        try {
            return !findByUserIdAndStatus(userId, CrawlSession.CrawlStatus.RUNNING).isEmpty() ||
                    !findByUserIdAndStatus(userId, CrawlSession.CrawlStatus.PAUSED).isEmpty();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error checking active sessions for user: " + userId, e);
            return false;
        }
    }

    public Optional<CrawlSession> findLatestByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CrawlSession> query = session.createQuery(
                    "FROM CrawlSession cs WHERE cs.user.userId = :userId ORDER BY cs.createdAt DESC",
                    CrawlSession.class
            );
            query.setParameter("userId", userId);
            query.setMaxResults(1);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding latest session for user: " + userId, e);
            return Optional.empty();
        }
    }
}
