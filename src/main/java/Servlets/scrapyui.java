package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/scrapyUi")
public class ScrapyUiServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirects the client to the scrapyUi.jsp page (URL changes in browser)
        response.sendRedirect(request.getContextPath() + "/jsp/JspUi/scrapyUi.jsp");
    }
}