package com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("✅ LogoutServlet called - User logging out");
        
        // Clear JWT token cookie
        Cookie jwtCookie = new Cookie("jwt_token", "");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(0); // Delete cookie
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        
        // Invalidate session
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        }
        
        // Redirect to signin with success message
        response.sendRedirect(request.getContextPath() + "/signin?success=logout");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
