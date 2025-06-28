package com.controller;

        import com.auth0.jwt.interfaces.DecodedJWT;
        import com.utils.JwtUtil;
        import jakarta.servlet.ServletException;
        import jakarta.servlet.annotation.WebServlet;
        import jakarta.servlet.http.Cookie;
        import jakarta.servlet.http.HttpServlet;
        import jakarta.servlet.http.HttpServletRequest;
        import jakarta.servlet.http.HttpServletResponse;

        import java.io.IOException;

        @WebServlet("/user-check")
        public class UserCheckServlet extends HttpServlet {

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
                            DecodedJWT jwt = JwtUtil.verifyToken(token);
                            if (jwt != null) {
                                isLoggedIn = true;
                                userEmail = jwt.getClaim("email").asString();
                                userName = jwt.getClaim("name").asString();
                            }
                            break;
                        }
                    }
                }

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                if (!isLoggedIn) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{\"isLoggedIn\":false}");
                } else {
                    response.getWriter().write(String.format(
                        "{\"isLoggedIn\":true,\"userName\":\"%s\",\"userEmail\":\"%s\"}",
                        userName, userEmail
                    ));
                }
            }
        }