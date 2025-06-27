package com.service;

import com.dao.UserDAO;
import com.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.JwtUtil;
import com.utils.PasswordUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SigninService {

    private UserDAO userDAO = new UserDAO();
    private ObjectMapper objectMapper = new ObjectMapper();

    public void handleAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> jsonResponse = new HashMap<>();

        try {
            String jsonBody = request.getReader().lines().collect(Collectors.joining());
            Map<String, Object> requestData = objectMapper.readValue(jsonBody, Map.class);

            String email = (String) requestData.get("email");
            String password = (String) requestData.get("password");
            Boolean rememberMe = (Boolean) requestData.get("rememberMe");
            if (rememberMe == null) rememberMe = false;
            System.out.println("Email: " + email + ", Password: " + password + ", Remember Me: " + rememberMe);

            // CSRF token validation (optional, if implemented)
            // String csrfToken = (String) requestData.get("csrfToken");
            // String sessionToken = (String) request.getSession().getAttribute("csrfToken");
            // if (csrfToken == null || !csrfToken.equals(sessionToken)) {
            //     throw new RuntimeException("Invalid CSRF token");
            // }

            if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Email and password are required");
            }
System.out.println("here position is 2");
            String token = authenticateUser(email.trim(), password);
            System.out.println("here position is 3");
            Cookie jwtCookie = new Cookie("jwt_token", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(rememberMe ? 30 * 24 * 60 * 60 : 24 * 60 * 60);
            response.addCookie(jwtCookie);

            System.out.println("JWT Token: " + token);
            JwtUtil.UserInfo userInfo = JwtUtil.getUserInfoFromToken(token);
            System.out.println("User info: " + userInfo);
            jsonResponse.put("success", true);
            jsonResponse.put("message", "Welcome back! Login successful.");
            jsonResponse.put("redirectUrl", "/dashboard");
            jsonResponse.put("user", Map.of(
                    "email", userInfo.getEmail(),
                    "username", userInfo.getUsername() != null ? userInfo.getUsername() : "User",
                    "isAdmin", userInfo.isAdmin()
            ));
            System.out.println("User info2: " + userInfo);
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", getErrorMessage(e.getMessage()));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
    }

    private String authenticateUser(String email, String password) {
        System.out.println("authenticateUser called with email: " + email);
        Optional<User> userOpt;
        try {
            userOpt = userDAO.findByEmail(email);
        } catch (Exception ex) {
            System.out.println("Exception in userDAO.findByEmail: " + ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException("Database error");
        }
        System.out.println("here position is 4");
        // ... rest of your code ...
    }

    private String getErrorMessage(String originalMessage) {
        if (originalMessage.contains("Invalid credentials")) {
            return "Invalid email or password. Please try again.";
        } else if (originalMessage.contains("Account is locked")) {
            return "Your account has been temporarily locked due to multiple failed login attempts.";
        } else if (originalMessage.contains("inactive")) {
            return "Your account is inactive. Please contact support.";
        } else {
            return "Login failed. Please try again.";
        }
    }
}