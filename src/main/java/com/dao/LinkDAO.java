package com.dao;

import com.entity.Link;
import com.entity.Page;
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

public class LinkDAO {
    
    private static final Logger LOGGER = Logger.getLogger(LinkDAO.class.getName());
    
    // Create Operations
    public void saveLink(Link link) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(link);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving link: " + link, e);
        }
    }
    
    public void saveAllLinks(List<Link> links) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (Link link : links) {
                session.save(link);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving link list", e);
        }
    }
    
    // Read Operations
    public Optional<Link> findById(Long linkId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Link link = session.get(Link.class, linkId);
            return Optional.ofNullable(link);
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding link by ID: " + linkId, he);
            return Optional.empty();
        }
    }
    
    public List<Link> getAllLinks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Link ORDER BY discoveredAt DESC", Link.class).list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all links", e);
            return List.of();
        }
    }
    
    public List<Link> findBySourcePageId(Long sourcePageId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Link> query = session.createQuery(
                "FROM Link l WHERE l.sourcePage.pageId = :sourcePageId ORDER BY l.discoveredAt DESC", 
                Link.class
            );
            query.setParameter("sourcePageId", sourcePageId);
            return query.list();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding links for source page: " + sourcePageId, he);
            return List.of();
        }
    }
    
    public List<Link> findByTargetPageId(Long targetPageId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Link> query = session.createQuery(
                "FROM Link l WHERE l.targetPage.pageId = :targetPageId ORDER BY l.discoveredAt DESC", 
                Link.class
            );
            query.setParameter("targetPageId", targetPageId);
            return query.list();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding links for target page: " + targetPageId, he);
            return List.of();
        }
    }
    
    public List<Link> findByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Link> query = session.createQuery(
                "FROM Link l WHERE l.sourcePage.crawlSession.user.userId = :userId ORDER BY l.discoveredAt DESC", 
                Link.class
            );
            query.setParameter("userId", userId);
            return query.list();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding links for user: " + userId, he);
            return List.of();
        }
    }
    
    public List<Link> findByTargetUrl(String targetUrl) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Link> query = session.createQuery(
                "FROM Link l WHERE l.targetUrl = :targetUrl ORDER BY l.discoveredAt DESC", 
                Link.class
            );
            query.setParameter("targetUrl", targetUrl);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding links by target URL: " + targetUrl, e);
            return List.of();
        }
    }
    
    public List<Link> findByLinkType(Link.LinkType linkType) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Link> query = session.createQuery(
                "FROM Link l WHERE l.linkType = :linkType ORDER BY l.discoveredAt DESC", 
                Link.class
            );
            query.setParameter("linkType", linkType);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding links by type: " + linkType, e);
            return List.of();
        }
    }
    
    public List<Link> findBySessionId(Long sessionId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Link> query = session.createQuery(
                "FROM Link l WHERE l.sourcePage.crawlSession.sessionId = :sessionId ORDER BY l.discoveredAt DESC", 
                Link.class
            );
            query.setParameter("sessionId", sessionId);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding links for session: " + sessionId, e);
            return List.of();
        }
    }
    
    public List<Link> findInternalLinks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Link> query = session.createQuery(
                "FROM Link l WHERE l.linkType = :linkType ORDER BY l.discoveredAt DESC", 
                Link.class
            );
            query.setParameter("linkType", Link.LinkType.INTERNAL);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding internal links", e);
            return List.of();
        }
    }
    
    public List<Link> findExternalLinks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Link> query = session.createQuery(
                "FROM Link l WHERE l.linkType = :linkType ORDER BY l.discoveredAt DESC", 
                Link.class
            );
            query.setParameter("linkType", Link.LinkType.EXTERNAL);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding external links", e);
            return List.of();
        }
    }
    
    public List<Link> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Link> query = session.createQuery(
                "FROM Link l WHERE l.discoveredAt BETWEEN :startDate AND :endDate ORDER BY l.discoveredAt DESC", 
                Link.class
            );
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding links by date range", e);
            return List.of();
        }
    }
    
    // Update Operations
    public void updateLink(Link link) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(link);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error updating link: " + link, e);
        }
    }
    
    // Delete Operations
    public void deleteLink(Long linkId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Link link = session.get(Link.class, linkId);
            if (link != null) {
                session.delete(link);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting link with ID: " + linkId, e);
        }
    }
    
    public int deleteBySourcePageId(Long sourcePageId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                "DELETE FROM Link l WHERE l.sourcePage.pageId = :sourcePageId"
            );
            query.setParameter("sourcePageId", sourcePageId);
            int deleted = query.executeUpdate();
            transaction.commit();
            return deleted;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting links for source page: " + sourcePageId, e);
            return 0;
        }
    }
    
    public int deleteBySessionId(Long sessionId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                "DELETE FROM Link l WHERE l.sourcePage.crawlSession.sessionId = :sessionId"
            );
            query.setParameter("sessionId", sessionId);
            int deleted = query.executeUpdate();
            transaction.commit();
            return deleted;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting links for session: " + sessionId, e);
            return 0;
        }
    }
    
    // Analytics and Statistics Methods
    public long countBySourcePageId(Long sourcePageId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(l) FROM Link l WHERE l.sourcePage.pageId = :sourcePageId", 
                Long.class
            );
            query.setParameter("sourcePageId", sourcePageId);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting links for source page: " + sourcePageId, e);
            return 0L;
        }
    }
    
    public long countByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(l) FROM Link l WHERE l.sourcePage.crawlSession.user.userId = :userId", 
                Long.class
            );
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting links for user: " + userId, e);
            return 0L;
        }
    }
    
    public long countInternalLinksByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(l) FROM Link l WHERE l.sourcePage.crawlSession.user.userId = :userId AND l.linkType = :linkType", 
                Long.class
            );
            query.setParameter("userId", userId);
            query.setParameter("linkType", Link.LinkType.INTERNAL);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting internal links for user: " + userId, e);
            return 0L;
        }
    }
    
    public long countExternalLinksByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(l) FROM Link l WHERE l.sourcePage.crawlSession.user.userId = :userId AND l.linkType = :linkType", 
                Long.class
            );
            query.setParameter("userId", userId);
            query.setParameter("linkType", Link.LinkType.EXTERNAL);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting external links for user: " + userId, e);
            return 0L;
        }
    }
    
    public List<Object[]> getLinkTypeDistribution() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                "SELECT l.linkType, COUNT(l) FROM Link l GROUP BY l.linkType ORDER BY COUNT(l) DESC", 
                Object[].class
            );
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting link type distribution", e);
            return List.of();
        }
    }
    
    public List<Object[]> getTopTargetDomains(int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                "SELECT SUBSTRING(l.targetUrl, LOCATE('://', l.targetUrl) + 3, LOCATE('/', l.targetUrl, LOCATE('://', l.targetUrl) + 3) - LOCATE('://', l.targetUrl) - 3) as domain, " +
                "COUNT(l) FROM Link l WHERE l.linkType = :linkType GROUP BY domain ORDER BY COUNT(l) DESC", 
                Object[].class
            );
            query.setParameter("linkType", Link.LinkType.EXTERNAL);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting top target domains", e);
            return List.of();
        }
    }
    
    public List<Object[]> getLinkStatsByPage() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                "SELECT l.sourcePage.pageId, l.sourcePage.url, COUNT(l), " +
                "SUM(CASE WHEN l.linkType = :internal THEN 1 ELSE 0 END), " +
                "SUM(CASE WHEN l.linkType = :external THEN 1 ELSE 0 END) " +
                "FROM Link l GROUP BY l.sourcePage.pageId, l.sourcePage.url ORDER BY COUNT(l) DESC", 
                Object[].class
            );
            query.setParameter("internal", Link.LinkType.INTERNAL);
            query.setParameter("external", Link.LinkType.EXTERNAL);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting link stats by page", e);
            return List.of();
        }
    }
    
    // Utility Methods
    public boolean linkExists(Long sourcePageId, String targetUrl) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(l) FROM Link l WHERE l.sourcePage.pageId = :sourcePageId AND l.targetUrl = :targetUrl", 
                Long.class
            );
            query.setParameter("sourcePageId", sourcePageId);
            query.setParameter("targetUrl", targetUrl);
            return query.uniqueResult() > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error checking if link exists", e);
            return false;
        }
    }
    
    public List<Link> findBrokenLinks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Link> query = session.createQuery(
                "FROM Link l WHERE l.targetPage IS NULL AND l.linkType = :linkType ORDER BY l.discoveredAt DESC", 
                Link.class
            );
            query.setParameter("linkType", Link.LinkType.INTERNAL);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding broken links", e);
            return List.of();
        }
    }
    
    public List<Link> findRecentLinks(int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Link> query = session.createQuery(
                "FROM Link l ORDER BY l.discoveredAt DESC", 
                Link.class
            );
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding recent links", e);
            return List.of();
        }
    }
    
    public List<Link> findRecentLinksByUserId(Long userId, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Link> query = session.createQuery(
                "FROM Link l WHERE l.sourcePage.crawlSession.user.userId = :userId ORDER BY l.discoveredAt DESC", 
                Link.class
            );
            query.setParameter("userId", userId);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding recent links for user: " + userId, e);
            return List.of();
        }
    }
    
    // Pagination Support for Dashboard
    public List<Link> findWithPagination(int offset, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Link> query = session.createQuery(
                "FROM Link ORDER BY discoveredAt DESC", 
                Link.class
            );
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding links with pagination", e);
            return List.of();
        }
    }
    
    public List<Link> findByUserIdWithPagination(Long userId, int offset, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Link> query = session.createQuery(
                "FROM Link l WHERE l.sourcePage.crawlSession.user.userId = :userId ORDER BY l.discoveredAt DESC", 
                Link.class
            );
            query.setParameter("userId", userId);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding links for user with pagination: " + userId, e);
            return List.of();
        }
    }
    
    public long getTotalCount() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(l) FROM Link l", 
                Long.class
            );
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting total link count", e);
            return 0L;
        }
    }
}
