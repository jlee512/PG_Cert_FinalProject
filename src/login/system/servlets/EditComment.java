package login.system.servlets;

import login.system.dao.CommentDAO;
import login.system.db.MySQL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by cbla080 on 7/06/2017.
 */
public class EditComment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
         /*If there is an attempt to access a servlet directly, check login status and redirect to login page or content page as is appropriate (method defined in LoginAttempt)*/
        LoginAttempt.loginStatusRedirection(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MySQL DB = new MySQL();

        /*If not logged in, redirect to login page*/
        HttpSession session = request.getSession(true);
        if (session.getAttribute("loginStatus") != "active") {
            response.sendRedirect("Login");
        }

        else {
              /*Check if session has timed out*/
            if (!LoginAttempt.sessionExpirationRedirection(request, response)) {

                /*Get parameters from the form*/
                int commentID = Integer.parseInt(request.getParameter("comment_id"));
                int articleID = Integer.parseInt(request.getParameter("article_id"));
                String content = request.getParameter("comment_body");

                /*Update comment in the database*/
                CommentDAO.editComment(DB, commentID, content);

                /*Redirect to article*/
                response.sendRedirect("ViewArticle?article_id=" + articleID);
            }
        }
    }
}
