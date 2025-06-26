package com.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is already authenticated
        HttpSession session = request.getSession(false);
        if (session != null) {
            String authToken = (String) session.getAttribute("authToken");
            if (authToken != null && !authToken.isEmpty()) {
                // User is already logged in, redirect to dashboard
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            }
        }
        
        // Generate CSRF token for security
        String csrfToken = generateCSRFToken();
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("csrfToken", csrfToken);
        
        // Set page attributes for master layout
        request.setAttribute("pageTitle", "Sign Up - CrawlForge");
        request.setAttribute("contentPage", "/jsp/auth/signup.jsp");
        
        // Add CSS files specific to signup page
        String[] cssFiles = {"signup.css"};
        request.setAttribute("pageCssFiles", cssFiles);
        
        // Add JavaScript files specific to signup page
//        String[] jsFiles = {"signup.js"};
//        request.setAttribute("pageJsFiles", jsFiles);
        
        // Add CSRF token to request for JSP
        request.setAttribute("csrfToken", csrfToken);
        
        // Check for any error or success messages from previous requests
        String error = request.getParameter("error");
        String success = request.getParameter("success");
        
        if (error != null) {
            switch (error) {
                case "username_exists":
                    request.setAttribute("error", "Username already exists. Please choose a different username.");
                    break;
                case "email_exists":
                    request.setAttribute("error", "Email already registered. Please use a different email or sign in.");
                    break;
                case "weak_password":
                    request.setAttribute("error", "Password is too weak. Please use at least 8 characters with numbers and symbols.");
                    break;
                case "validation_failed":
                    request.setAttribute("error", "Please fill all required fields correctly.");
                    break;
                case "server_error":
                    request.setAttribute("error", "Registration failed due to server error. Please try again.");
                    break;
                default:
                    request.setAttribute("error", "Registration failed. Please try again.");
                    break;
            }
        }
        
        if (success != null) {
            switch (success) {
                case "registered":
                    request.setAttribute("success", "Registration successful! Please check your email to verify your account.");
                    break;
                default:
                    request.setAttribute("success", "Operation completed successfully.");
                    break;
            }
        }
        
        // Forward to master layout (index.jsp)
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // POST requests to /signup should redirect to GET
        // This prevents form resubmission issues
        response.sendRedirect(request.getContextPath() + "/signup");
    }
    
    /**
     * Generate a secure CSRF token for form protection
     */
    private String generateCSRFToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
    

}
