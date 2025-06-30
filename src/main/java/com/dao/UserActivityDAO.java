package com.dao;

import com.entity.UserActivity;
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

public class UserActivityDAO {

    private static final Logger LOGGER = Logger.getLogger(UserActivityDAO.class.getName());

    // Create Operations
    public void saveUserActivity(UserActivity userActivity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(userActivity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving user activity: " + userActivity, e);
        }
    }

    public void saveAllUserActivity(List<UserActivity> activityList) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (UserActivity activity : activityList) {
                session.save(activity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving user activity list", e);
        }
    }

    // Read Operations
    public Optional<UserActivity> findById(Long activityId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            UserActivity activity = session.get(UserActivity.class, activityId);
            return Optional.ofNullable(activity);
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding user activity by ID: " + activityId, he);
            return Optional.empty();
        }
    }

    public List<UserActivity> getAllUserActivity() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM UserActivity ORDER BY generatedAt DESC", UserActivity.class).list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all user activity", e);
            return List.of();
        }
    }

    public Optional<UserActivity> findByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<UserActivity> query = session.createQuery(
                    "FROM UserActivity a WHERE a.user.userId = :userId ORDER BY a.generatedAt DESC",
                    UserActivity.class
            );
            query.setParameter("userId", userId);
            query.setMaxResults(1);
            return query.uniqueResultOptional();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding user activity for user: " + userId, he);
            return Optional.empty();
        }
    }

    public Optional<UserActivity> findByUserIdAndTimeRange(Long userId, String timeRange) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<UserActivity> query = session.createQuery(
                    "FROM UserActivity a WHERE a.user.userId = :userId AND a.timeRange = :timeRange ORDER BY a.generatedAt DESC",
                    UserActivity.class
            );
            query.setParameter("userId", userId);
            query.setParameter("timeRange", timeRange);
            query.setMaxResults(1);
            return query.uniqueResultOptional();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding user activity for user " + userId + " and time range " + timeRange, he);
            return Optional.empty();
        }
    }

    public List<UserActivity> findActiveUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
            Query<UserActivity> query = session.createQuery(
                    "FROM UserActivity a WHERE a.lastCrawlActivity > :weekAgo ORDER BY a.lastCrawlActivity DESC",
                    UserActivity.class
            );
            query.setParameter("weekAgo", weekAgo);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding active users", e);
            return List.of();
        }
    }

    public List<UserActivity> findPowerUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<UserActivity> query = session.createQuery(
                    "FROM UserActivity a WHERE a.totalSessions >= 50 AND a.averageSuccessRate >= 85.0 ORDER BY a.totalSessions DESC",
                    UserActivity.class
            );
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding power users", e);
            return List.of();
        }
    }

    public List<UserActivity> findByActivityType(String activityType) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<UserActivity> query = session.createQuery(
                    "FROM UserActivity a WHERE a.activityType = :activityType ORDER BY a.generatedAt DESC",
                    UserActivity.class
            );
            query.setParameter("activityType", activityType);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding user activity by type: " + activityType, e);
            return List.of();
        }
    }

    public List<UserActivity> findByAccountTier(String accountTier) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<UserActivity> query = session.createQuery(
                    "FROM UserActivity a WHERE a.accountTier = :accountTier ORDER BY a.totalSessions DESC",
                    UserActivity.class
            );
            query.setParameter("accountTier", accountTier);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding user activity by account tier: " + accountTier, e);
            return List.of();
        }
    }

    // Analytics Queries for Dashboard Service Layer
    public Long getTotalActiveUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(a) FROM UserActivity a WHERE a.lastCrawlActivity > :weekAgo",
                    Long.class
            );
            query.setParameter("weekAgo", weekAgo);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting total active users", e);
            return 0L;
        }
    }

    public Double getAverageProductivityScore() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Double> query = session.createQuery(
                    "SELECT COALESCE(AVG(a.productivityScore), 0.0) FROM UserActivity a WHERE a.productivityScore IS NOT NULL",
                    Double.class
            );
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting average productivity score", e);
            return 0.0;
        }
    }

    public List<Object[]> getTopUsersByActivity(int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT a.user.username, a.totalSessions, a.averageSuccessRate, a.productivityScore " +
                            "FROM UserActivity a ORDER BY a.totalSessions DESC, a.averageSuccessRate DESC",
                    Object[].class
            );
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting top users by activity", e);
            return List.of();
        }
    }

    public List<Object[]> getUserActivityTrends(String timeRange) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT DATE(a.generatedAt), COUNT(a), AVG(a.totalSessions), AVG(a.averageSuccessRate) " +
                            "FROM UserActivity a WHERE a.timeRange = :timeRange " +
                            "GROUP BY DATE(a.generatedAt) ORDER BY DATE(a.generatedAt) DESC",
                    Object[].class
            );
            query.setParameter("timeRange", timeRange);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting user activity trends for range: " + timeRange, e);
            return List.of();
        }
    }

    public List<Object[]> getDomainPopularity() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT a.mostCrawledDomain, SUM(a.mostCrawledDomainCount), COUNT(a) " +
                            "FROM UserActivity a WHERE a.mostCrawledDomain IS NOT NULL " +
                            "GROUP BY a.mostCrawledDomain ORDER BY SUM(a.mostCrawledDomainCount) DESC",
                    Object[].class
            );
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting domain popularity", e);
            return List.of();
        }
    }

    // Update Operations
    public void updateUserActivity(UserActivity userActivity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(userActivity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error updating user activity: " + userActivity, e);
        }
    }

    public void updateLastActivity(Long userId, LocalDateTime lastActivity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                    "UPDATE UserActivity a SET a.lastCrawlActivity = :lastActivity WHERE a.user.userId = :userId"
            );
            query.setParameter("lastActivity", lastActivity);
            query.setParameter("userId", userId);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error updating last activity for user: " + userId, e);
        }
    }

    // Delete Operations
    public void deleteUserActivity(Long activityId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            UserActivity activity = session.get(UserActivity.class, activityId);
            if (activity != null) {
                session.delete(activity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting user activity with ID: " + activityId, e);
        }
    }

    public int deleteOldActivity(int daysToKeep) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysToKeep);
            Query query = session.createQuery(
                    "DELETE FROM UserActivity a WHERE a.generatedAt < :cutoffDate"
            );
            query.setParameter("cutoffDate", cutoffDate);
            int deleted = query.executeUpdate();
            transaction.commit();
            return deleted;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting old user activity", e);
            return 0;
        }
    }

    public int deleteByUserId(Long userId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                    "DELETE FROM UserActivity a WHERE a.user.userId = :userId"
            );
            query.setParameter("userId", userId);
            int deleted = query.executeUpdate();
            transaction.commit();
            return deleted;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting activity for user: " + userId, e);
            return 0;
        }
    }

    // Utility Methods
    public boolean existsByUserId(Long userId) {
        try {
            return findByUserId(userId).isPresent();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error checking existence for user: " + userId, e);
            return false;
        }
    }

    public long countByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(a) FROM UserActivity a WHERE a.user.userId = :userId",
                    Long.class
            );
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting activity for user: " + userId, e);
            return 0L;
        }
    }

    public List<UserActivity> findRecentActivity(int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<UserActivity> query = session.createQuery(
                    "FROM UserActivity a ORDER BY a.lastCrawlActivity DESC",
                    UserActivity.class
            );
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding recent user activity", e);
            return List.of();
        }
    }

    // Pagination Support
    public List<UserActivity> findWithPagination(int offset, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<UserActivity> query = session.createQuery(
                    "FROM UserActivity ORDER BY generatedAt DESC",
                    UserActivity.class
            );
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding user activity with pagination", e);
            return List.of();
        }
    }

    public long getTotalCount() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(a) FROM UserActivity a",
                    Long.class
            );
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting total activity count", e);
            return 0L;
        }
    }
}
