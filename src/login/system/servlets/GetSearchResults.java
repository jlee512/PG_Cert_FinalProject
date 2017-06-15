package login.system.servlets;

import login.system.dao.Article;
import login.system.dao.ArticleDAO;
import login.system.db.MySQL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by catherinedechalain on 15/06/17.
 */
public class GetSearchResults extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
         /*If there is an attempt to access a servlet directly, check login status and redirect to login page or content page as is appropriate (method defined below)*/
        LoginAttempt.loginStatusRedirection(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
         /*Access the database*/
        MySQL DB = new MySQL();

        HttpSession session = request.getSession(true);
        if (session.getAttribute("loginStatus") != "active") {
            response.sendRedirect("Login");

        } else {

             /*Check if session has timed out*/
            if (!LoginAttempt.sessionExpirationRedirection(request, response)) {
                String searchTerm = request.getParameter("search_term");
                List<Article> articles = ArticleDAO.getArticlePreviewsBySearchTerm(DB, searchTerm);
            }
        }
    }
}
