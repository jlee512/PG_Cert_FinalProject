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

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        //redirect
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*Access the database*/
        MySQL DB = new MySQL();

        String status = "";

        /*Get printwriter to write to JSON*/
        PrintWriter out = response.getWriter();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String content = request.getParameter("comment_body");
        String username = request.getParameter("username");
        int articleID = Integer.parseInt(request.getParameter("article_id"));

        /*Access user details from the session*/
        HttpSession session = request.getSession(true);
        User currentUser = (User) session.getAttribute("userDetails");

        int userID = currentUser.getUser_id();
        if (request.getParameter("parentComment_id").length() > 0){
            int parentCommentID = Integer.parseInt(request.getParameter("parentComment_id"));
            status = addReplyComment(parentCommentID, articleID, DB, userID, currentTime, content);
            System.out.println(status);
        }
        else {
            status = addTopLevelComment(articleID, DB, userID, currentTime, content);
            System.out.println(status);
        }
        if (status.equals("Comment added successfully.")){
            response.sendRedirect("ViewArticle?article_id=" + articleID);
        }
        else {
            out.println("<p>" + status + " Click <a href=\"ViewArticle?article_id=" + articleID + "\">here</a> to return to the article.");
        }
    }

    protected String addTopLevelComment(int articleID, MySQL DB, int userID, Timestamp timestamp, String content){
        String status = CommentDAO.addComment(DB, userID, articleID, -1, timestamp, content);
        return status;
    }

    protected String addReplyComment(int parentCommentID, int articleID, MySQL DB, int userID, Timestamp timestamp, String content){
        String status = CommentDAO.addComment(DB, userID, articleID, parentCommentID, timestamp, content);
        CommentDAO.setCommentAsParent(DB, parentCommentID);
        return status;
    }
}
