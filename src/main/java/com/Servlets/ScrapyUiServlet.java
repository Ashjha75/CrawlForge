package main.java.com.Servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/scrapyUi")  // This maps to /scrapyUi
public class ScrapyUiServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/JspUi/scrapyUi.jsp");
        dispatcher.forward(request, response);
    }
}
