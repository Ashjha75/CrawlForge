//package com.servlets;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet("/dashboard")
//public class DashboardServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        // Set page attributes for Dashboard
//        request.setAttribute("pageTitle", "Dashboard - Crawling Analytics");
//        request.setAttribute("contentPage", "/jsp/pages/dashboard-content.jsp");
//        request.setAttribute("pageCss", "dashboard.css");
//        request.setAttribute("pageJs", "dashboard.js");
//
//        // Forward to master layout (index.jsp)
//        request.getRequestDispatcher("/index.jsp").forward(request, response);
//    }
//}
