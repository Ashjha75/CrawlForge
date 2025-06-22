package com.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/scrapyui")
public class ScrapyUIServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set page attributes for Scrapy UI
        request.setAttribute("pageTitle", "Scrapy UI - Visual Web Scraping Interface");
        request.setAttribute("contentPage", "/jsp/pages/scrapyui-content.jsp");
        request.setAttribute("pageCss", "scrapyui.css");
        request.setAttribute("pageJs", "scrapyui.js");
        
        // Forward to master layout (index.jsp)
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
