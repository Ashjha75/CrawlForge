package com.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/dashboard")
public class ScrapyUIServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set page attributes for Scrapy UI
        request.setAttribute("pageTitle", "Scrapy UI - Visual Web Scraping Interface");
        request.setAttribute("contentPage", "/jsp/scrappers/scrapperHome.jsp");
        request.setAttribute("pageCss", "linkTaker.css");
        request.setAttribute("pageCss", "history.css");
        request.setAttribute("pageJs", "linkTaker.js");

        // Forward to master layout (index.jsp)
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
