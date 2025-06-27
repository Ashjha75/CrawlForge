package com.service;

import com.dao.UserDAO;
import com.model.User;
import com.utils.PasswordUtil;
import com.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

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
        
        System.out.println("✅ SigninService.handleAuthentication called");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> jsonResponse = new HashMap<>();
        
        try {
            // Read JSON from request body
            String jsonBody = request.getReader().lines().collect(Collectors.joining());
            System.out.println("Request JSON: " + jsonBody);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> requestData = objectMapper.readValue(jsonBody, Map.class);
            
            // Extract data
            String email = (String) requestData.get("email");
            String password = (String) requestData.get("password");
            Boolean rememberMe = (Boolean) requestData.get("rememberMe");
            
            if (rememberMe == null) rememberMe = false;
            
            System.out.println("Login attempt for email: " + email);
            System.out.println("Remember me: " + rememberMe);
            
            // Validate input
            if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Email and password are required");
            }
            
            // Authenticate user
            String token = authenticateUser(email.trim(), password);
            
            System.out.println("✅ User authenticated successfully! Token: " + token);
            
            // Set JWT token in HTTP-only cookie
            Cookie jwtCookie = new Cookie("jwt_token", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            
            if (rememberMe) {
                jwtCookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
            } else {
                jwtCookie.setMaxAge(24 * 60 * 60); // 24 hours
            }
            
            response.addCookie(jwtCookie);
            
            // Get user info for response
            JwtUtil.UserInfo userInfo = JwtUtil.getUserInfoFromToken(token);
            
            // Success response
            jsonResponse.put("success", true);
            jsonResponse.put("message", "Welcome back! Login successful.");
            jsonResponse.put("redirectUrl", "/dashboard");
            jsonResponse.put("user", Map.of(
                "email", userInfo.getEmail(),
                "username", userInfo.getUsername() != null ? userInfo.getUsername() : "User",
                "isAdmin", userInfo.isAdmin()
            ));
            
            response.setStatus(HttpServletResponse.SC_OK);
            
        } catch (Exception e) {
            System.err.println("❌ Login failed: " + e.getMessage());
            e.printStackTrace();
            
            // Error response
            jsonResponse.put("success", false);
            jsonResponse.put("message", getErrorMessage(e.getMessage()));
            
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        
        // Write JSON response
        response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
    }
    
    private String authenticateUser(String email, String password) {
        Optional<User> userOpt = userDAO.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid credentials");
        }
        
        User user = userOpt.get();
        
        // Check if account is locked or inactive
        if (!user.canLogin()) {
            throw new RuntimeException("Account is locked or inactive");
        }
        
        // Verify password
        if (!PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            user.incrementLoginAttempts();
            userDAO.updateUser(user);
            throw new RuntimeException("Invalid credentials");
        }
        
        // Reset login attempts and update last login
        user.resetLoginAttempts();
        user.setLastLoginAt(LocalDateTime.now());
        userDAO.updateUser(user);
        
        // Get user roles for token
        String roles = user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.joining(","));
        
        if (roles.isEmpty()) {
            roles = "USER"; // Default role
        }
        
        // Generate JWT token with roles
        return JwtUtil.generateTokenWithRoles(
            user.getEmail(), 
            user.getUserId(), 
            user.getUsername(), 
            roles
        );
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
