package Servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Date;

@WebServlet("/first")
public class firstservices extends HttpServlet {
    private ServletConfig config;
    private int hitCount;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        hitCount = 0;
        System.out.println("Servlet is being initialized with config");
        // Call super.init() to properly initialize servlet
        super.init(config);
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("doGet method called");

        // Create a cookie with encoded value
        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        Cookie visitCookie = new Cookie("lastVisit", timestamp);
        visitCookie.setMaxAge(24*60*60); // 24 hours
        response.addCookie(visitCookie);

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>First Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Hello from First Servlet</h1>");
        out.println("<p>HTTP Method: " + request.getMethod() + "</p>");
        out.println("<p>Request URI: " + request.getRequestURI() + "</p>");

        // Display existing cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            out.println("<h2>Cookies:</h2>");
            for (Cookie cookie : cookies) {
                out.println("<p>" + cookie.getName() + ": " + cookie.getValue() + "</p>");
            }
        }

        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get form parameters
        String username = request.getParameter("username");
        String message = request.getParameter("message");

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><body>");
        out.println("<h2>Received POST Data:</h2>");
        out.println("<p>Username: " + username + "</p>");
        out.println("<p>Message: " + message + "</p>");
        out.println("<a href='first'>Back to Form</a>");
        out.println("</body></html>");
    }

    @Override
    public String getServletInfo() {
        return "FirstServices Servlet";
    }

    @Override
    public void destroy() {
        System.out.println("Servlet is being destroyed");
    }
}