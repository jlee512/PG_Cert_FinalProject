package backend.servlets;

import backend.dao.Article;
import backend.dao.ArticleDAO;
import backend.dao.User;
import backend.db.MySQL;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by cbla080 on 3/06/2017.
 */

/**
 * This servlet is used to display an individual article
 */

public class ViewArticle extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*Setup the database*/
        MySQL DB = new MySQL();

        //Don't let users see articles if they are not logged in.
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("userDetails");
        if (session.getAttribute("loginStatus") != "active") {
            response.sendRedirect("Login");

        } else {

              /*Check if session has timed out*/
            if (!LoginAttempt.sessionExpirationRedirection(request, response)) {

                if (request.getParameter("article_id") == null) {
                    response.sendRedirect("Content?username=" + user.getUsername() + "&notFound=true");
                } else {

                    //Pass the articleID as a parameter when clicking the link to the article.
                    int articleID = Integer.parseInt(request.getParameter("article_id"));

                    /*Store the article ID in a cookie for use on the server side*/
                    Cookie article_id_cookie = new Cookie("article_id", Integer.toString(articleID));
                    article_id_cookie.setMaxAge(2 * 60 * 60); //Max age 2 hours
                    article_id_cookie.setPath("/");
                    response.addCookie(article_id_cookie);

                    //Get Article object by ID from ArticleDAO.
                    Article article = ArticleDAO.getArticle(DB, articleID);
                    System.out.println(article.getAuthor_id());

                    if (article.getAuthor_id() == -1 || article.getArtcle_timestamp() == null) {

                        response.sendRedirect("Content?username=" + user.getUsername());

                    } else {

                        Timestamp timestamp = article.getArtcle_timestamp();


                        String date_for_output = new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(timestamp);



                        //Set it as an attribute to pass to the JSP.
                        request.setAttribute("article", article);
                        request.setAttribute("date", date_for_output);

                        System.out.println("test");

                        getServletContext().getRequestDispatcher("/ViewArticlePage").forward(request, response);
                    }
                }
            }
        }
    }

}
