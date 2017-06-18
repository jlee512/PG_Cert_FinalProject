package backend.servlets;

import backend.dao.CommentDAO;
import backend.dao.User;
import backend.db.MySQL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import static backend.servlets.AddAnArticleAttempt.inputContainsHTML;

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
                String username = request.getParameter("username");

                int articleID = Integer.parseInt(request.getParameter("article_id"));

                /*Access user details from the session*/
                User currentUser = (User) session.getAttribute("userDetails");
                int userID = currentUser.getUser_id();

                /*Check if input contains HTML, if so, redirect the user to the MainContentPage and do not upload comment to the database (this uses the method developed in the AddAnArticleAttempt Class*/
                if (inputContainsHTML(content)) {
                    response.sendRedirect("Content?username=" + currentUser.getUsername());
                    return;
                }


                /*Case (A) If there is a parent comment ID, the comment is a reply*/
                if (request.getParameter("parent_comment_id").length() > 0) {
                    int parentCommentID = Integer.parseInt(request.getParameter("parent_comment_id"));
                    status = addReplyComment(parentCommentID, articleID, DB, userID, currentTime, content);

                /*Case (B) Otherwise it is a top level comment*/
                } else {
                    status = addTopLevelComment(articleID, DB, userID, currentTime, content);

                }

                /*Check output status from comment addition method and send a corresponding redirect*/
                if (status.equals("Comment added successfully.")) {
                    response.sendRedirect("ViewArticle?article_id=" + articleID);
                } else {
                    out.println("<p>" + status + " Click <a href=\"ViewArticle?article_id=" + articleID + "\">here</a> to return to the article.");
                }
            }
        }
    }


    /*The addTopLevelComment method adds a top level comment which is designated with a parentCommentID = - 1*/
    private String addTopLevelComment(int articleID, MySQL DB, int userID, Timestamp timestamp, String content){
        return CommentDAO.addComment(DB, userID, articleID, -1, timestamp, content);
    }

    /*The addReplyComment method adds a reply comment in accordance with the CommentDAO method*/
    private String addReplyComment(int parentCommentID, int articleID, MySQL DB, int userID, Timestamp timestamp, String content){
        return CommentDAO.addReplyComment(DB, userID, articleID, parentCommentID, timestamp, content);
    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
