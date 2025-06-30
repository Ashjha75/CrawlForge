package com.dao;

import com.entity.DashboardData;
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

public class DashboardDataDAO {

    private static final Logger LOGGER = Logger.getLogger(DashboardDataDAO.class.getName());

    // Create Operations
    public void saveDashboardData(DashboardData dashboardData) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(dashboardData);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving dashboard data: " + dashboardData, e);
        }
    }

    public void saveAllDashboardData(List<DashboardData> dashboardDataList) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (DashboardData data : dashboardDataList) {
                session.save(data);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving dashboard data list", e);
        }
    }

    // Read Operations
    public Optional<DashboardData> findById(Long dashboardId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            DashboardData dashboardData = session.get(DashboardData.class, dashboardId);
            return Optional.ofNullable(dashboardData);
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding dashboard data by ID: " + dashboardId, he);
            return Optional.empty();
        }
    }

    public List<DashboardData> getAllDashboardData() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM DashboardData ORDER BY generatedAt DESC", DashboardData.class).list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all dashboard data", e);
            return List.of();
        }
    }

    public List<DashboardData> findByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<DashboardData> query = session.createQuery(
                    "FROM DashboardData d WHERE d.user.userId = :userId ORDER BY d.generatedAt DESC",
                    DashboardData.class
            );
            query.setParameter("userId", userId);
            return query.list();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding dashboard data for user: " + userId, he);
            return List.of();
        }
    }

    public Optional<DashboardData> findLatestByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<DashboardData> query = session.createQuery(
                    "FROM DashboardData d WHERE d.user.userId = :userId ORDER BY d.generatedAt DESC",
                    DashboardData.class
            );
            query.setParameter("userId", userId);
            query.setMaxResults(1);
            return query.uniqueResultOptional();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding latest dashboard data for user: " + userId, he);
            return Optional.empty();
        }
    }

    public Optional<DashboardData> findByUserIdAndTimeRange(Long userId, String timeRange) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<DashboardData> query = session.createQuery(
                    "FROM DashboardData d WHERE d.user.userId = :userId AND d.timeRange = :timeRange ORDER BY d.generatedAt DESC",
                    DashboardData.class
            );
            query.setParameter("userId", userId);
            query.setParameter("timeRange", timeRange);
            query.setMaxResults(1);
            return query.uniqueResultOptional();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding dashboard data for user " + userId + " and time range " + timeRange, he);
            return Optional.empty();
        }
    }

    public List<DashboardData> findValidCachedData() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<DashboardData> query = session.createQuery(
                    "FROM DashboardData d WHERE d.isCached = true AND d.cacheExpiresAt > :now ORDER BY d.generatedAt DESC",
                    DashboardData.class
            );
            query.setParameter("now", LocalDateTime.now());
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding valid cached dashboard data", e);
            return List.of();
        }
    }

    public List<DashboardData> findByDashboardType(String dashboardType) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<DashboardData> query = session.createQuery(
                    "FROM DashboardData d WHERE d.dashboardType = :dashboardType ORDER BY d.generatedAt DESC",
                    DashboardData.class
            );
            query.setParameter("dashboardType", dashboardType);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding dashboard data by type: " + dashboardType, e);
            return List.of();
        }
    }

    // Analytics Queries for Dashboard Service Layer
    public Long getTotalSessionsForUser(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COALESCE(SUM(d.totalSessions), 0L) FROM DashboardData d WHERE d.user.userId = :userId",
                    Long.class
            );
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting total sessions for user: " + userId, e);
            return 0L;
        }
    }

    public Double getAverageSuccessRateForUser(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Double> query = session.createQuery(
                    "SELECT COALESCE(AVG(d.averageSuccessRate), 0.0) FROM DashboardData d WHERE d.user.userId = :userId AND d.averageSuccessRate IS NOT NULL",
                    Double.class
            );
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting average success rate for user: " + userId, e);
            return 0.0;
        }
    }

    public List<Object[]> getDashboardSummaryByTimeRange(String timeRange) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT d.user.userId, d.user.username, SUM(d.totalSessions), AVG(d.averageSuccessRate), MAX(d.generatedAt) " +
                            "FROM DashboardData d WHERE d.timeRange = :timeRange " +
                            "GROUP BY d.user.userId, d.user.username ORDER BY SUM(d.totalSessions) DESC",
                    Object[].class
            );
            query.setParameter("timeRange", timeRange);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting dashboard summary for time range: " + timeRange, e);
            return List.of();
        }
    }

    // Update Operations
    public void updateDashboardData(DashboardData dashboardData) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(dashboardData);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error updating dashboard data: " + dashboardData, e);
        }
    }

    public void markCacheExpired(Long dashboardId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                    "UPDATE DashboardData d SET d.isCached = false, d.cacheExpiresAt = null WHERE d.dashboardId = :dashboardId"
            );
            query.setParameter("dashboardId", dashboardId);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error marking cache expired for dashboard ID: " + dashboardId, e);
        }
    }

    public void markAllCacheExpiredForUser(Long userId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                    "UPDATE DashboardData d SET d.isCached = false, d.cacheExpiresAt = null WHERE d.user.userId = :userId"
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
    public void deleteDashboardData(Long dashboardId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            DashboardData dashboardData = session.get(DashboardData.class, dashboardId);
            if (dashboardData != null) {
                session.delete(dashboardData);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting dashboard data with ID: " + dashboardId, e);
        }
    }

    public int deleteExpiredCache() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                    "DELETE FROM DashboardData d WHERE d.isCached = true AND d.cacheExpiresAt < :now"
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

    public int deleteOldDataForUser(Long userId, int daysToKeep) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysToKeep);
            Query query = session.createQuery(
                    "DELETE FROM DashboardData d WHERE d.user.userId = :userId AND d.generatedAt < :cutoffDate"
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
            LOGGER.log(Level.SEVERE, "Error deleting old data for user: " + userId, e);
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

    public boolean userIdExists(Long userId) {
        try {
            return !findByUserId(userId).isEmpty();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error checking if user ID exists: " + userId, e);
            return false;
        }
    }

    public long countByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(d) FROM DashboardData d WHERE d.user.userId = :userId",
                    Long.class
            );
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting dashboard data for user: " + userId, e);
            return 0L;
        }
    }

    public List<DashboardData> findRecentByUserId(Long userId, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<DashboardData> query = session.createQuery(
                    "FROM DashboardData d WHERE d.user.userId = :userId ORDER BY d.generatedAt DESC",
                    DashboardData.class
            );
            query.setParameter("userId", userId);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding recent dashboard data for user: " + userId, e);
            return List.of();
        }
    }
}
