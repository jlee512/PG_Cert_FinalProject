package login.system.servlets;

import login.system.dao.*;
import login.system.db.MySQL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.sql.Date;

/**
 * Created by catherinedechalain on 1/06/17.
 */
public class AddCommentAttempt extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        //redirect
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        MySQL DB = new MySQL();
        Date currentDate = new Date(System.currentTimeMillis());
        String content = request.getParameter("comment_body");
        String username = request.getParameter("username");
        User currentUser = UserDAO.getUser(DB, username);
        int userID = currentUser.getUser_id();

        //Depending on whether this is a top level comment or a reply to a previous comment, either a Comment object or an Article object will be passed.

        String status = CommentDAO.addComment(DB, userID, 0, 0, currentDate, content);
        System.out.println(status);
    }

    protected String addTopLevelComment(Article article, MySQL DB, int userID, Date date, String content){
        int articleID = article.getArticle_id();
        String status = CommentDAO.addComment(DB, userID, articleID, 0, date, content);
        return status;
    }

    protected String addReplyComment(Comment comment, MySQL DB, int userID, Date date, String content){
        int parentCommentID = comment.getCommentID();
        int articleID = comment.getArticleID();
        String status = CommentDAO.addComment(DB, userID, articleID, parentCommentID, date, content);
        return status;
    }
}
