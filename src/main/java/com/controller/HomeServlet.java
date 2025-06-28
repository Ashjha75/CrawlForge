package com.servlets;

        import jakarta.servlet.ServletException;
        import jakarta.servlet.annotation.WebServlet;
        import jakarta.servlet.http.HttpServlet;
        import jakarta.servlet.http.HttpServletRequest;
        import jakarta.servlet.http.HttpServletResponse;

        import java.io.IOException;

        @WebServlet("")
        public class HomeServlet extends HttpServlet {

            @Override
            protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

                String requestURI = request.getRequestURI();
                String contextPath = request.getContextPath();
                String path = requestURI.substring(contextPath.length());

                if (!path.equals("/") && !path.equals("") && !path.equals("/index.jsp")) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                request.setAttribute("pageTitle", "Home");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }