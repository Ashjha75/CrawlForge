//package com.controller;
//
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//@WebServlet("/dashboard")
//public class ScrapyUIServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        request.setAttribute("pageTitle", "Scrapy UI - Visual Web Scraping Interface");
//        request.setAttribute("contentPage", "/jsp/scrappers/scrapperHome.jsp");
//
//        // Multiple CSS files as comma-separated string
//        request.setAttribute("pageCss", "linkTaker.css,history.css,dashboard.css");
//        request.setAttribute("pageJs", "linkTaker.js,dashboard.js");
//
//        request.getRequestDispatcher("/index.jsp").forward(request, response);
//    }
//}
//
