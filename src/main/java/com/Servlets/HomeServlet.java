package main.java.com.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/")
public class HomeServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Set page attributes for home page
        request.setAttribute("pageTitle", "Home - Advanced Web Scraping Platform");
        request.setAttribute("pageCss", null); // No additional CSS needed for home
        request.setAttribute("pageJs", "home.js"); // Optional home-specific JS
        request.setAttribute("contentPage", null); // Use default content
        
        // Forward to master layout (index.jsp)
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
