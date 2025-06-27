package com.middleware;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter(urlPatterns = {"/*"})
public class GlobalExceptionFilter implements Filter {
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            handleException(httpRequest, httpResponse, e);
        }
    }
    
    private void handleException(HttpServletRequest request, HttpServletResponse response, Exception e)
            throws IOException {
        
        System.err.println("🚨 Global Exception Handler caught: " + e.getMessage());
        e.printStackTrace();
        
        // Check if it's an AJAX request (JSON expected)
        String contentType = request.getContentType();
        String acceptHeader = request.getHeader("Accept");
        boolean isAjaxRequest = (contentType != null && contentType.contains("application/json")) ||
                               (acceptHeader != null && acceptHeader.contains("application/json"));
        
        if (isAjaxRequest) {
            handleAjaxException(response, e);
        } else {
            handlePageException(request, response, e);
        }
    }
    
    private void handleAjaxException(HttpServletResponse response, Exception e) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("error", true);
        errorResponse.put("message", getErrorMessage(e));
        errorResponse.put("type", "SYSTEM_ERROR");
        
        // Set appropriate HTTP status
        if (e instanceof IllegalArgumentException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (e instanceof SecurityException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
    
    private void handlePageException(HttpServletRequest request, HttpServletResponse response, Exception e)
            throws IOException {
        
        // Store error in session for display
        request.getSession().setAttribute("globalError", getErrorMessage(e));
        request.getSession().setAttribute("errorType", getErrorType(e));
        
        // Redirect to error page or back to current page
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.contains("/error")) {
            response.sendRedirect(referer + "?error=system");
        } else {
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
    
    private String getErrorMessage(Exception e) {
        if (e instanceof IllegalArgumentException) {
            return e.getMessage();
        } else if (e instanceof SecurityException) {
            return "Access denied. Please check your permissions.";
        } else if (e instanceof RuntimeException && e.getMessage() != null) {
            return e.getMessage();
        } else {
            return "An unexpected error occurred. Please try again later.";
        }
    }
    
    private String getErrorType(Exception e) {
        if (e instanceof IllegalArgumentException) {
            return "VALIDATION_ERROR";
        } else if (e instanceof SecurityException) {
            return "SECURITY_ERROR";
        } else {
            return "SYSTEM_ERROR";
        }
    }
}
