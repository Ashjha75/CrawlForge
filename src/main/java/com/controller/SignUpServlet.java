package com.controller;

import com.service.SignupService;
import com.utils.CsrfUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

    private final SignupService signupService = new SignupService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // CSRF token generation
        String csrfToken = CsrfUtil.generateCSRFToken();
        request.getSession(true).setAttribute("csrfToken", csrfToken);

        // Set attributes for rendering
        request.setAttribute("csrfToken", csrfToken);
        request.setAttribute("pageTitle", "Sign Up - CrawlForge");
        request.setAttribute("contentPage", "/jsp/auth/signup.jsp");

        // Forward to master layout
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        signupService.handleRegistration(request, response);
    }
}