package login.system.servlets;

import login.system.dao.Article;
import login.system.dao.ArticleDAO;
import login.system.db.MySQL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by jlee512 on 5/06/2017.
 */
public class MainContentAccess extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Create link to the database*/
        MySQL DB = new MySQL();
        HttpSession session = request.getSession();
          /*If user is logged out, redirect to login page*/
        if (session.getAttribute("loginStatus") != "active") {
            response.sendRedirect("Login");

        } else {

        /*Get the number of articles requested*/
            int firstArticle = Integer.parseInt(request.getParameter("from"));
            int articleCount = Integer.parseInt(request.getParameter("count"));

            List<Article> articles = ArticleDAO.getfirstNArticlePreviewsByDate(DB, firstArticle, articleCount);


        /*Return a JSON object with the article information included*/
            response.setContentType("application/json");
            JSONArray articleDetails = new JSONArray();

            IndividualAuthorArticles.constructArticlePreviewJSON(articles, articleDetails);

            articleDetails.toJSONString();

            response.getWriter().write(articleDetails.toString());

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //DO POST

    }
}
