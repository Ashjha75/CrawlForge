package com.dao;

import com.entity.User;
import com.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    public void saveUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving user: " + user, e);
        }
    }

    public Optional<User> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(
                    "from User u where u.email = :email", User.class
            );
            query.setParameter("email", email);
            return query.uniqueResultOptional();
        } catch (HibernateException he) {
            log.error("Error finding User by email='{}'", email, he);
            return Optional.empty();
        }
    }

    public Optional<User> findByUsername(String username) {
        // Try-with-resources will auto-close the Session
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(
                    "from User u where u.username = :username", User.class
            );
            query.setParameter("username", username);
            return query.uniqueResultOptional();
        } catch (HibernateException he) {
            log.error("Error finding User by username='{}'", username, he);
            return Optional.empty();
        }
    }

    public boolean emailExists(String email) {
        try {
            return findByEmail(email).isPresent();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error checking if email exists: " + email, e);
            return false;
        }
    }

    public boolean usernameExists(String username) {
        try {
            return findByUsername(username).isPresent();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error checking if username exists: " + username, e);
            return false;
        }
    }

    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving all users", e);
            return List.of();
        }
    }

    public void updateUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error updating user: " + user, e);
        }
    }

    public void deleteUser(Long userId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting user with ID: " + userId, e);
        }
    }
}