package com.servlets;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("")  // Use empty string instead of "/"
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean isLoggedIn = false;
        String userEmail = null;
        String userName = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt_token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    DecodedJWT jwt = JwtUtil.verifyToken(token); // returns DecodedJWT or null
                    if (jwt != null) {
                        isLoggedIn = true;
                        userEmail = jwt.getClaim("email").asString();
                        userName = jwt.getClaim("name").asString();
                    }
                    break;
                }
            }
        }

        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.substring(contextPath.length());

        if (!path.equals("/") && !path.equals("") && !path.equals("/index.jsp")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.setAttribute("isLoggedIn", isLoggedIn);
        request.setAttribute("userName", userName);
        request.setAttribute("userEmail", userEmail);
        request.setAttribute("pageTitle", "Home");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}