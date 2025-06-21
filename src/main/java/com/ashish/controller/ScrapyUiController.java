@WebServlet("/scrapyUi")
public class ScrapyUiServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/JspUi/scrapyUi.jsp").forward(request, response);
    }
}