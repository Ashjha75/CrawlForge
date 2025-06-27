package com.service;

import com.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.PasswordUtil;
import com.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignupService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> jsonResponse = new HashMap<>();

        try {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            boolean newsletter = "on".equals(request.getParameter("newsletter"));

            if (firstName == null || lastName == null || username == null ||
                    email == null || password == null) {
                throw new IllegalArgumentException("All fields are required");
            }

            // Registration logic
            String token = registerUser(firstName, lastName, username, email, password, newsletter);

            Cookie jwtCookie = new Cookie("jwt_token", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge(24 * 60 * 60);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);

            jsonResponse.put("success", true);
            jsonResponse.put("message", "Registration successful! Welcome to CrawlForge.");
            jsonResponse.put("redirectUrl", "/dashboard");
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
    }

    public String registerUser(String firstName, String lastName, String username, String email, String password, boolean newsletter) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            // Check for duplicate username/email
            Query<User> userQuery = session.createQuery(
                "FROM User WHERE username = :username OR email = :email", User.class);
            userQuery.setParameter("username", username);
            userQuery.setParameter("email", email);
            if (!userQuery.list().isEmpty()) {
                throw new IllegalArgumentException("Username or email already exists");
            }

            // Hash password
            String hashedPassword = PasswordUtil.hashPassword(password);

            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(username);
            user.setEmail(email);
            user.setPasswordHash(hashedPassword);
            user.setNewsletterSubscribed(newsletter);

            session.save(user);
            tx.commit();

            // Generate JWT with user info and default role USER
            return JwtUtil.generateTokenWithRoles(
                user.getEmail(),
                user.getUserId(),
                user.getUsername(),
                "USER"
            );
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Registration failed: " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }
}