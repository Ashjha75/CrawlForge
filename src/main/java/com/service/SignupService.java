package com.service;

import com.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.HibernateUtil;
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

    // Java
    public void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> jsonResponse = new HashMap<>();

        try {
            // Parse JSON body
            Map<String, Object> data = objectMapper.readValue(request.getReader(), Map.class);

            String firstName = (String) data.get("firstName");
            String lastName = (String) data.get("lastName");
            String username = (String) data.get("username");
            String email = (String) data.get("email");
            String password = (String) data.get("password");
            String confirmPassword = (String) data.get("confirmPassword");
            boolean agreeTerms = Boolean.TRUE.equals(data.get("agreeTerms"));
            boolean newsletter = Boolean.TRUE.equals(data.get("newsletter"));

            if (firstName == null || firstName.isEmpty() ||
                lastName == null || lastName.isEmpty() ||
                username == null || username.isEmpty() ||
                email == null || email.isEmpty() ||
                password == null || password.isEmpty() ||
                confirmPassword == null || confirmPassword.isEmpty() ||
                !agreeTerms) {
                throw new IllegalArgumentException("All fields are required");
            }

            if (!password.equals(confirmPassword)) {
                throw new IllegalArgumentException("Passwords do not match");
            }

            String token = registerUser(firstName, lastName, username, email, password, newsletter);

            Cookie jwtCookie = new Cookie("jwt_token", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge(24 * 60 * 60);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);

            jsonResponse.put("success", true);
            jsonResponse.put("message", "Registration successful! Welcome to CrawlForge.");
            jsonResponse.put("redirectUrl", "/signin");
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