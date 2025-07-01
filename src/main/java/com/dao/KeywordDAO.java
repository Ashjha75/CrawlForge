package com.dao;

import com.entity.Keyword;
import com.entity.Page;
import com.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeywordDAO {
    
    private static final Logger LOGGER = Logger.getLogger(KeywordDAO.class.getName());
    
    // Create Operations
    public void saveKeyword(Keyword keyword) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(keyword);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving keyword: " + keyword, e);
        }
    }
    
    public void saveAllKeywords(List<Keyword> keywords) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (Keyword keyword : keywords) {
                session.save(keyword);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving keyword list", e);
        }
    }
    
    // Read Operations
    public Optional<Keyword> findById(Long keywordId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Keyword keyword = session.get(Keyword.class, keywordId);
            return Optional.ofNullable(keyword);
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding keyword by ID: " + keywordId, he);
            return Optional.empty();
        }
    }
    
    public List<Keyword> getAllKeywords() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Keyword ORDER BY frequency DESC", Keyword.class).list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all keywords", e);
            return List.of();
        }
    }
    
    public List<Keyword> findByPageId(Long pageId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Keyword> query = session.createQuery(
                "FROM Keyword k WHERE k.page.pageId = :pageId ORDER BY k.frequency DESC", 
                Keyword.class
            );
            query.setParameter("pageId", pageId);
            return query.list();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding keywords for page: " + pageId, he);
            return List.of();
        }
    }
    
    public List<Keyword> findByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Keyword> query = session.createQuery(
                "FROM Keyword k WHERE k.page.crawlSession.user.userId = :userId ORDER BY k.frequency DESC", 
                Keyword.class
            );
            query.setParameter("userId", userId);
            return query.list();
        } catch (HibernateException he) {
            LOGGER.log(Level.SEVERE, "Error finding keywords for user: " + userId, he);
            return List.of();
        }
    }
    
    public List<Keyword> findByKeyword(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Keyword> query = session.createQuery(
                "FROM Keyword k WHERE k.keyword = :keyword ORDER BY k.frequency DESC", 
                Keyword.class
            );
            query.setParameter("keyword", keyword);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding keywords by keyword: " + keyword, e);
            return List.of();
        }
    }
    
    public List<Keyword> findByKeywordType(Keyword.KeywordType keywordType) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Keyword> query = session.createQuery(
                "FROM Keyword k WHERE k.keywordType = :keywordType ORDER BY k.frequency DESC", 
                Keyword.class
            );
            query.setParameter("keywordType", keywordType);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding keywords by type: " + keywordType, e);
            return List.of();
        }
    }
    
    public List<Keyword> findBySessionId(Long sessionId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Keyword> query = session.createQuery(
                "FROM Keyword k WHERE k.page.crawlSession.sessionId = :sessionId ORDER BY k.frequency DESC", 
                Keyword.class
            );
            query.setParameter("sessionId", sessionId);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding keywords for session: " + sessionId, e);
            return List.of();
        }
    }
    
    public List<Keyword> findTopKeywords(int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Keyword> query = session.createQuery(
                "FROM Keyword k ORDER BY k.frequency DESC", 
                Keyword.class
            );
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding top keywords", e);
            return List.of();
        }
    }
    
    public List<Keyword> findTopKeywordsByUserId(Long userId, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Keyword> query = session.createQuery(
                "FROM Keyword k WHERE k.page.crawlSession.user.userId = :userId ORDER BY k.frequency DESC", 
                Keyword.class
            );
            query.setParameter("userId", userId);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding top keywords for user: " + userId, e);
            return List.of();
        }
    }
    
    // Update Operations
    public void updateKeyword(Keyword keyword) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(keyword);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error updating keyword: " + keyword, e);
        }
    }
    
    // Delete Operations
    public void deleteKeyword(Long keywordId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Keyword keyword = session.get(Keyword.class, keywordId);
            if (keyword != null) {
                session.delete(keyword);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting keyword with ID: " + keywordId, e);
        }
    }
    
    public int deleteByPageId(Long pageId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                "DELETE FROM Keyword k WHERE k.page.pageId = :pageId"
            );
            query.setParameter("pageId", pageId);
            int deleted = query.executeUpdate();
            transaction.commit();
            return deleted;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting keywords for page: " + pageId, e);
            return 0;
        }
    }
    
    public int deleteBySessionId(Long sessionId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                "DELETE FROM Keyword k WHERE k.page.crawlSession.sessionId = :sessionId"
            );
            query.setParameter("sessionId", sessionId);
            int deleted = query.executeUpdate();
            transaction.commit();
            return deleted;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting keywords for session: " + sessionId, e);
            return 0;
        }
    }
    
    // Analytics and Statistics Methods
    public long countByPageId(Long pageId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(k) FROM Keyword k WHERE k.page.pageId = :pageId", 
                Long.class
            );
            query.setParameter("pageId", pageId);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting keywords for page: " + pageId, e);
            return 0L;
        }
    }
    
    public long countByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(k) FROM Keyword k WHERE k.page.crawlSession.user.userId = :userId", 
                Long.class
            );
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting keywords for user: " + userId, e);
            return 0L;
        }
    }
    
    public List<Object[]> getKeywordFrequencyDistribution() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                "SELECT k.keyword, SUM(k.frequency), COUNT(k) FROM Keyword k GROUP BY k.keyword ORDER BY SUM(k.frequency) DESC", 
                Object[].class
            );
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting keyword frequency distribution", e);
            return List.of();
        }
    }
    
    public List<Object[]> getKeywordTypeDistribution() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                "SELECT k.keywordType, COUNT(k), AVG(k.frequency) FROM Keyword k GROUP BY k.keywordType ORDER BY COUNT(k) DESC", 
                Object[].class
            );
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting keyword type distribution", e);
            return List.of();
        }
    }
    
    public Double getAverageKeywordDensityByUserId(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Double> query = session.createQuery(
                "SELECT AVG(k.density) FROM Keyword k WHERE k.page.crawlSession.user.userId = :userId AND k.density IS NOT NULL", 
                Double.class
            );
            query.setParameter("userId", userId);
            Double result = query.uniqueResult();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting average keyword density for user: " + userId, e);
            return 0.0;
        }
    }
    
    public List<Object[]> getTopKeywordsByDensity(int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                "SELECT k.keyword, k.density, k.frequency FROM Keyword k WHERE k.density IS NOT NULL ORDER BY k.density DESC", 
                Object[].class
            );
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting top keywords by density", e);
            return List.of();
        }
    }
    
    // Utility Methods
    public boolean keywordExistsForPage(Long pageId, String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(k) FROM Keyword k WHERE k.page.pageId = :pageId AND k.keyword = :keyword", 
                Long.class
            );
            query.setParameter("pageId", pageId);
            query.setParameter("keyword", keyword);
            return query.uniqueResult() > 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error checking if keyword exists for page", e);
            return false;
        }
    }
    
    public List<Keyword> findSimilarKeywords(String keyword, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Keyword> query = session.createQuery(
                "FROM Keyword k WHERE k.keyword LIKE :keyword ORDER BY k.frequency DESC", 
                Keyword.class
            );
            query.setParameter("keyword", "%" + keyword + "%");
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding similar keywords for: " + keyword, e);
            return List.of();
        }
    }
    
    // Pagination Support for Dashboard
    public List<Keyword> findWithPagination(int offset, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Keyword> query = session.createQuery(
                "FROM Keyword ORDER BY frequency DESC", 
                Keyword.class
            );
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding keywords with pagination", e);
            return List.of();
        }
    }
    
    public List<Keyword> findByUserIdWithPagination(Long userId, int offset, int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Keyword> query = session.createQuery(
                "FROM Keyword k WHERE k.page.crawlSession.user.userId = :userId ORDER BY k.frequency DESC", 
                Keyword.class
            );
            query.setParameter("userId", userId);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding keywords for user with pagination: " + userId, e);
            return List.of();
        }
    }
    
    public long getTotalCount() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(k) FROM Keyword k", 
                Long.class
            );
            return query.uniqueResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting total keyword count", e);
            return 0L;
        }
    }
}
