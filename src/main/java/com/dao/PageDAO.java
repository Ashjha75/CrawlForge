package com.dao;

import com.entity.Page;
import com.entity.CrawlSession;
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

public class PageDAO {

    private static final Logger LOGGER = Logger.getLogger(PageDAO.class.getName());

    // Create Operations
    public void savePage(Page page) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(page);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving page: " + page, e);
        }
    }

    public void saveAllPages(List<Page> pages) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (Page page : pages) {
                session.save(page);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving page list", e);
        }
    }

    // Read Operations
    public Optional<Page> findById(Long pageId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Page page = session.get(Page.class, pageId);
            return Optional.ofNullable(page);
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding page by ID: " + pageId, he);
            return Optional.empty();
        }
    }

    public List<Page> getAllPages() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Page ORDER BY crawledAt DESC", Page.class).list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all pages", e);
            return List.of();
        }
    }

    public List<Page> findBySessionId(Long sessionId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Page> query = session.createQuery(
                    "FROM Page p WHERE p.crawlSession.sessionId = :sessionId ORDER BY p.crawledAt DESC",
                    Page.class
            );
            query.setParameter("sessionId", sessionId);
            return query.list();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding pages for session: " + sessionId, he);
            return List.of();
        }
    }

    public List<Page> findByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Page> query = session.createQuery(
                    "FROM Page p WHERE p.crawlSession.user.userId = :userId ORDER BY p.crawledAt DESC",
                    Page.class
            );
            query.setParameter("userId", userId);
            return query.list();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding pages for user: " + userId, he);
            return List.of();
        }
    }

    public Optional<Page> findByUrl(String url) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Page> query = session.createQuery(
                    "FROM Page p WHERE p.url = :url",
                    Page.class
            );
            query.setParameter("url", url);
            return query.uniqueResultOptional();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding page by URL: " + url, he);
            return Optional.empty();
        }
    }

    public List<Page> findByStatusCode(Integer statusCode) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Page> query = session.createQuery(
                    "FROM Page p WHERE p.statusCode = :statusCode ORDER BY p.crawledAt DESC",
                    Page.class
            );
            query.setParameter("statusCode", statusCode);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding pages by status code: " + statusCode, e);
            return List.of();
        }
    }

    public List<Page> findSuccessfulPages() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Page> query = session.createQuery(
                    "FROM Page p WHERE p.statusCode >= 200 AND p.statusCode < 300 ORDER BY p.crawledAt DESC",
                    Page.class
            );
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding successful pages", e);
            return List.of();
        }
    }

    public List<Page> findErrorPages() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Page> query = session.createQuery(
                    "FROM Page p WHERE p.statusCode >= 400 ORDER BY p.crawledAt DESC",
                    Page.class
            );
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding error pages", e);
            return List.of();
        }
    }

    public List<Page> findByDomain(String domain) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Page> query = session.createQuery(
                    "FROM Page p WHERE p.url LIKE :domain ORDER BY p.crawledAt DESC",
                    Page.class
            );
            query.setParameter("domain", "%" + domain + "%");
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding pages by domain: " + domain, e);
            return List.of();
        }
    }

    public List<Page> findByDepthLevel(Integer depthLevel) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Page> query = session.createQuery(
                    "FROM Page p WHERE p.depthLevel = :depthLevel ORDER BY p.crawledAt DESC",
                    Page.class
            );
            query.setParameter("depthLevel", depthLevel);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding pages by depth level: " + depthLevel, e);
            return List.of();
        }
    }

    public List<Page> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Page> query = session.createQuery(
                    "FROM Page p WHERE p.crawledAt BETWEEN :startDate AND :endDate ORDER BY p.crawledAt DESC",
                    Page.class
            );
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding pages by date range", e);
            return List.of();
        }
    }

    // Update Operations
    public void updatePage(Page page) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(page);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error updating page: " + page, e);
        }
    }

    // Delete Operations
    public void deletePage(Long pageId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Page page = session.get(Page.class, pageId);
            if (page != null) {
                session.delete(page);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting page with ID: " + pageId, e);
        }
    }

    public int deleteBySessionId(Long sessionId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                    "DELETE FROM Page p WHERE p.crawlSession.sessionId = :sessionId"
            );
            query.setParameter("sessionId", sessionId);
            int deleted = query.executeUpdate();
            transaction.commit();
            return deleted;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting pages for session: " + sessionId, e);
            return 0;
        }
    }

    // Analytics and Statistics Methods
    public long countBySessionId(Long sessionId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(p) FROM Page p WHERE p.crawlSession.sessionId = :sessionId",
                    Long.class
            );
            query.setParameter("sessionId", sessionId);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting pages for session: " + sessionId, e);
            return 0L;
        }
    }

    public long countByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(p) FROM Page p WHERE p.crawlSession.user.userId = :userId",
                    Long.class
            );
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting pages for user: " + userId, e);
            return 0L;
        }
    }

    public long countSuccessfulPagesByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(p) FROM Page p WHERE p.crawlSession.user.userId = :userId AND p.statusCode >= 200 AND p.statusCode < 300",
                    Long.class
            );
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting successful pages for user: " + userId, e);
            return 0L;
        }
    }

    public long countErrorPagesByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(p) FROM Page p WHERE p.crawlSession.user.userId = :userId AND p.statusCode >= 400",
                    Long.class
            );
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting error pages for user: " + userId, e);
            return 0L;
        }
    }

    public Double getAverageLoadTimeByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Double> query = session.createQuery(
                    "SELECT AVG(p.loadTimeMs) FROM Page p WHERE p.crawlSession.user.userId = :userId AND p.loadTimeMs IS NOT NULL",
                    Double.class
            );
            query.setParameter("userId", userId);
            Double result = query.uniqueResult();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting average load time for user: " + userId, e);
            return 0.0;
        }
    }

    public List<Object[]> getDomainStatistics() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT SUBSTRING(p.url, LOCATE('://', p.url) + 3, LOCATE('/', p.url, LOCATE('://', p.url) + 3) - LOCATE('://', p.url) - 3) as domain, " +
                            "COUNT(p), AVG(p.loadTimeMs), SUM(CASE WHEN p.statusCode >= 200 AND p.statusCode < 300 THEN 1 ELSE 0 END) " +
                            "FROM Page p GROUP BY domain ORDER BY COUNT(p) DESC",
                    Object[].class
            );
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting domain statistics", e);
            return List.of();
        }
    }

    public List<Object[]> getStatusCodeDistribution() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT p.statusCode, COUNT(p) FROM Page p GROUP BY p.statusCode ORDER BY COUNT(p) DESC",
                    Object[].class
            );
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting status code distribution", e);
            return List.of();
        }
    }

    // Utility Methods
    public boolean pageExists(String url) {
        try {
            return findByUrl(url).isPresent();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error checking if page exists: " + url, e);
            return false;
        }
    }

    public List<Page> findRecentPages(int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Page> query = session.createQuery(
                    "FROM Page p ORDER BY p.crawledAt DESC",
                    Page.class
            );
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding recent pages", e);
            return List.of();
        }
    }

    public List<Page> findRecentPagesByUserId(Long userId, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Page> query = session.createQuery(
                    "FROM Page p WHERE p.crawlSession.user.userId = :userId ORDER BY p.crawledAt DESC",
                    Page.class
            );
            query.setParameter("userId", userId);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding recent pages for user: " + userId, e);
            return List.of();
        }
    }

    // Pagination Support for Dashboard
    public List<Page> findWithPagination(int offset, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Page> query = session.createQuery(
                    "FROM Page ORDER BY crawledAt DESC",
                    Page.class
            );
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding pages with pagination", e);
            return List.of();
        }
    }

    public List<Page> findByUserIdWithPagination(Long userId, int offset, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Page> query = session.createQuery(
                    "FROM Page p WHERE p.crawlSession.user.userId = :userId ORDER BY p.crawledAt DESC",
                    Page.class
            );
            query.setParameter("userId", userId);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding pages for user with pagination: " + userId, e);
            return List.of();
        }
    }

    public long getTotalCount() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(p) FROM Page p",
                    Long.class
            );
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting total page count", e);
            return 0L;
        }
    }
}
