package com.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignupService {

    private ObjectMapper objectMapper = new ObjectMapper();

    public void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> jsonResponse = new HashMap<>();

        try {
            // Extract and validate form data
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

            // Registration logic (stub)
            String token = registerUser(firstName, lastName, username, email, password, newsletter);

            // Set JWT token in HTTP-only cookie
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
        // Implement registration logic here
        return "dummy-jwt-token";
    }
}