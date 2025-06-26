package com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/reports")
public class ReportsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set page attributes for Reports
        request.setAttribute("pageTitle", "Analytics & Reports - CrawlForge");
        request.setAttribute("contentPage", "/jsp/reports/report.jsp");

//
        request.setAttribute("pageCssFiles", "report.css");
//
//        // JavaScript files for reports functionality
//
        request.setAttribute("pageJsFiles", "report.js");

        // Forward to the master layout (index.jsp)
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
