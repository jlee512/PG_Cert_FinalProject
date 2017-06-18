package login.system.servlets;

import login.system.dao.*;
import login.system.db.MySQL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.Date;

/**
 * Created by catherinedechalain on 1/06/17.
 */
public class AddCommentAttempt extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
         /*If there is an attempt to access a servlet directly, check login status and redirect to login page or content page as is appropriate (method defined in LoginAttempt)*/
        LoginAttempt.loginStatusRedirection(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*Access the database*/
        MySQL DB = new MySQL();

        String status = "";

        /*If user is not logged in, redirect to login page*/
        HttpSession session = request.getSession(true);
        if (session.getAttribute("loginStatus") != "active") {
            response.sendRedirect("Login");

        } else {

             /*Check if session has timed out*/
            if (!LoginAttempt.sessionExpirationRedirection(request, response)) {

                /*Get printwriter to write to page*/
                PrintWriter out = response.getWriter();

                /*Get comment details*/
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                String content = request.getParameter("comment_body");
                int articleID = Integer.parseInt(request.getParameter("article_id"));

                /*Access user details from the session*/
                User currentUser = (User) session.getAttribute("userDetails");
                int userID = currentUser.getUser_id();

                /*If there is a parent comment ID, the comment is a reply; otherwise it is a top level comment*/
                if (request.getParameter("parent_comment_id").length() > 0) {
                    int parentCommentID = Integer.parseInt(request.getParameter("parent_comment_id"));
                    status = addReplyComment(parentCommentID, articleID, DB, userID, currentTime, content);
                    System.out.println(status);
                } else {
                    status = addTopLevelComment(articleID, DB, userID, currentTime, content);
                    System.out.println(status);
                }

                /*If comment is added successfully, redirect to the article. Otherwise print the error message*/
                if (status.equals("Comment added successfully.")) {
                    response.sendRedirect("ViewArticle?article_id=" + articleID);
                } else {
                    out.println("<p>" + status + " Click <a href=\"ViewArticle?article_id=" + articleID + "\">here</a> to return to the article.");
                }
            }
        }
    }

    /*Method to add a top level comment*/
    private String addTopLevelComment(int articleID, MySQL DB, int userID, Timestamp timestamp, String content){
        return CommentDAO.addComment(DB, userID, articleID, -1, timestamp, content);
    }

    /*Method to add a reply comment*/
    private String addReplyComment(int parentCommentID, int articleID, MySQL DB, int userID, Timestamp timestamp, String content){
        return CommentDAO.addReplyComment(DB, userID, articleID, parentCommentID, timestamp, content);
    }
}
