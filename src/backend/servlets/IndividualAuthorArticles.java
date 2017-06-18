package backend.servlets;

import backend.dao.Article;
import backend.dao.ArticleDAO;
import backend.db.MySQL;
import backend.dao.User;
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
 * Created by jlee512 on 9/06/2017.
 */
public class IndividualAuthorArticles extends HttpServlet {

    /*This Servlet is mapped to ViewIndividualArticles*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Create a link to the database*/
        MySQL DB = new MySQL();
        HttpSession session = request.getSession(true);

        /*If user is logged out, redirect to login page*/
        if (session.getAttribute("loginStatus") != "active") {
            response.sendRedirect("Login");
        } else {
              /*Check if session has timed out*/
            if (!LoginAttempt.sessionExpirationRedirection(request, response)) {

        /*Get the number of articles requested and the author_id*/

                if (request.getParameter("from") == null || request.getParameter("count") == null) {
                    response.sendRedirect("Content?username=" + ((User) (session.getAttribute("userDetails"))).getUsername() + "&notFound=true");
                } else {
                    int firstArticle = Integer.parseInt(request.getParameter("from"));
                    int articleCount = Integer.parseInt(request.getParameter("count"));
                    int author_id = ((User) (session.getAttribute("userDetails"))).getUser_id();

                    List<Article> articles = ArticleDAO.getfirstNArticlePreviewsByAuthor(DB, firstArticle, articleCount, author_id);

        /*Return a JSON object with the article information included*/
                    response.setContentType("application/json");
                    JSONArray articleDetails = new JSONArray();

                    constructArticlePreviewJSON(articles, articleDetails);

                    articleDetails.toJSONString();

                    response.getWriter().write(articleDetails.toString());

                }
            }
        }
    }

    public static void constructArticlePreviewJSON(List<Article> articles, JSONArray articleDetails) {
        for (int i = 0; i < articles.size(); i++) {

            JSONObject singleArticle = new JSONObject();
            singleArticle.put("article_id", articles.get(i).getArticle_id());
            singleArticle.put("article_title", articles.get(i).getArticle_title());
            singleArticle.put("article_timestamp", articles.get(i).getArtcle_timestamp().getTime());
            singleArticle.put("author_username", articles.get(i).getAuthor_username());

            singleArticle.put("author_firstname", articles.get(i).getAuthor_firstname());
            singleArticle.put("author_lastname", articles.get(i).getAuthor_lastname());
            singleArticle.put("article_body", articles.get(i).getArticle_body().substring(0, Math.min(articles.get(i).getArticle_body().length(), 100)) + "...");
            singleArticle.put("article_body_full", articles.get(i).getArticle_body());
            singleArticle.put("comment_count", articles.get(i).getComment_count());
            articleDetails.add(i, singleArticle);
        }
    }
}
