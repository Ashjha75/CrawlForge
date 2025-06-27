package com.dao;

import com.entity.Role;
import com.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class RoleDAO {

    public void saveRole(Role role) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Optional<Role> findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Role> query = session.createQuery("FROM Role WHERE name = :name", Role.class);
            query.setParameter("name", name);
            return query.uniqueResultOptional();
        }
    }

    public List<Role> getAllRoles() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Role", Role.class).list();
        }
    }

    public Role getOrCreateRole(String roleName, String description) {
        Optional<Role> existingRole = findByName(roleName);
        if (existingRole.isPresent()) {
            return existingRole.get();
        }

        Role newRole = Role.builder()
                .name(roleName)
                .description(description)
                .build();
        saveRole(newRole);
        return newRole;
    }
}