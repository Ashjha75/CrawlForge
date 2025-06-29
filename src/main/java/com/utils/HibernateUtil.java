package com.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            // Set properties using environment variables from Render
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", System.getenv("DB_URL"));
            configuration.setProperty("hibernate.connection.username", System.getenv("DB_USER"));
            configuration.setProperty("hibernate.connection.password", System.getenv("DB_PASSWORD"));

            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");

            // HikariCP settings (optional, tune as needed)
            configuration.setProperty("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
            configuration.setProperty("hibernate.hikari.minimumIdle", "5");
            configuration.setProperty("hibernate.hikari.maximumPoolSize", "20");
            configuration.setProperty("hibernate.hikari.idleTimeout", "300000");
            configuration.setProperty("hibernate.hikari.maxLifetime", "1200000");
            configuration.setProperty("hibernate.hikari.connectionTimeout", "20000");

            // Add annotated entity classes
            configuration.addAnnotatedClass(com.entity.User.class);
            configuration.addAnnotatedClass(com.entity.Role.class);
            configuration.addAnnotatedClass(com.entity.CrawlSession.class);
            configuration.addAnnotatedClass(com.entity.Page.class);
            configuration.addAnnotatedClass(com.entity.Link.class);
            configuration.addAnnotatedClass(com.entity.Keyword.class);

            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
