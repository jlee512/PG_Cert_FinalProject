package login.system.servlets;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import login.system.dao.*;
import login.system.db.MySQL;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by cbla080 on 3/06/2017.
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

                /*If user attempts to access the servlet without the article_id parameter, redirect to the homepage with an error message*/
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

                    if (article.getAuthor_id() == -1) {

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
