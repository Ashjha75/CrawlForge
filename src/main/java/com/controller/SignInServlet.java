package com.controller;

import com.service.SigninService;
import com.utils.CsrfUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {

    private final SigninService signinService = new SigninService();

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
        String csrfToken = CsrfUtil.generateCSRFToken();
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("csrfToken", csrfToken);

        // Set page attributes for master layout
        request.setAttribute("pageTitle", "Sign In - CrawlForge");
        request.setAttribute("contentPage", "/jsp/auth/signin.jsp");

        // Add CSS files specific to signin page
        String[] cssFiles = {"signin.css"};
        request.setAttribute("pageCssFiles", cssFiles);

        // Add JavaScript files specific to signin page
        String[] jsFiles = {"signin.js"};
        request.setAttribute("pageJsFiles", jsFiles);

        // Add CSRF token to request for JSP
        request.setAttribute("csrfToken", csrfToken);

        // Check for any error or success messages from previous requests
        String error = request.getParameter("error");
        String success = request.getParameter("success");

        if (error != null) {
            switch (error) {
                case "invalid":
                    request.setAttribute("error", "Invalid username or password. Please try again.");
                    break;
                case "inactive":
                    request.setAttribute("error", "Your account has been deactivated. Please contact support.");
                    break;
                case "expired":
                    request.setAttribute("error", "Your session has expired. Please sign in again.");
                    break;
                case "required":
                    request.setAttribute("error", "Please sign in to access this page.");
                    break;
                default:
                    request.setAttribute("error", "An error occurred. Please try again.");
                    break;
            }
        }

        if (success != null) {
            switch (success) {
                case "logout":
                    request.setAttribute("success", "You have been successfully logged out.");
                    break;
                case "registered":
                    request.setAttribute("success", "Registration successful! Please sign in with your credentials.");
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

        signinService.handleAuthentication(request, response);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        // Log servlet initialization
        System.out.println("SignInServlet initialized - CrawlForge Authentication System");
    }

    @Override
    public void destroy() {
        super.destroy();
        // Cleanup if needed
        System.out.println("SignInServlet destroyed");
    }
}
