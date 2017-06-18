package backend.servlets;

import backend.dao.ArticleDAO;
import backend.dao.User;
import backend.db.MySQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by jlee512 on 12/06/2017.
 */

/**
 * This servlet is used to delete a user account
 */


public class DeleteAnArticle extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Establish a connection to the database*/
        MySQL DB = new MySQL();
        HttpSession session = request.getSession(true);

        /*Access the user's details stored in the current session*/
        User user = (User) session.getAttribute("userDetails");

        /*If user is not logged in, redirect them to login page.*/
        if (session.getAttribute("loginStatus") != "active") {
            response.sendRedirect("Login");

        } else {

            /*If the session has not timed out */
            if (!LoginAttempt.sessionExpirationRedirection(request, response)) {

                /*If the article_id does not exist, redirect to the MainContent page*/
                if (request.getParameter("article_id") == null) {
                    response.sendRedirect("Content?username=" + user.getUsername() + "&notFound=true");
                } else {

                    int article_id = Integer.parseInt(request.getParameter("article_id"));

                    /*Delete the article and send a redirect to the user's profile page with a corresponding deletion status code (refer to the ArticleDAO for details*/
                    int deletionStatus = ArticleDAO.deleteAnArticle(DB, article_id, user.getUser_id());
                    if (deletionStatus == 1) {
                        /*Redirect to the users profile page*/
                        response.sendRedirect("ProfilePage?user_id=" + user.getUsername() + "&articleDeleted=true");
                    } else {
                        response.sendRedirect("ProfilePage?user_id=" + user.getUsername() + "&articleDeleted=false");
                    }
                }
            }
        }
    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
