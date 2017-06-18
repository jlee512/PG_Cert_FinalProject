package backend.servlets;

import backend.dao.Article;
import backend.dao.ArticleDAO;
import backend.db.MySQL;
import backend.dao.User;
import backend.dao.UserDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static backend.servlets.IndividualAuthorArticles.constructArticlePreviewJSON;

/**
 * Created by catherinedechalain on 13/06/17.
 */

/**
 * This servlet is used to construct a JSON array representing a number of articles written by a particular user in date order
 * (for the public profile)
 */

public class IndividualPublicAuthorArticles extends HttpServlet {

    /*This Servlet is mapped to ViewPublicArticles*/
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

                /*If any parameter is null redirect to homepage*/
                if (request.getParameter("from") == null || request.getParameter("count") == null || request.getParameter("username") == null) {
                    response.sendRedirect("Content?username=" + ((User) (session.getAttribute("userDetails"))).getUsername() + "&notFound=true");
                } else {

                    /*Get the number of articles requested and the author_id*/
                    int firstArticle = Integer.parseInt(request.getParameter("from"));
                    int articleCount = Integer.parseInt(request.getParameter("count"));
                    String username = request.getParameter("username");
                    User user = UserDAO.getUser(DB, username);
                    int author_id = user.getUser_id();

                    List<Article> articles = ArticleDAO.getfirstNArticlePreviewsByAuthor(DB, firstArticle, articleCount, author_id);

                    /*Return a JSON object with the article information included*/
                    response.setContentType("application/json");
                    JSONArray articleDetails = new JSONArray();

                    /*Construct the JSON array using the method developed in "IndividualAuthorArticles*/
                    constructArticlePreviewJSON(articles, articleDetails);

                    articleDetails.toJSONString();

                    response.getWriter().write(articleDetails.toString());

                }
            }
        }
    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
