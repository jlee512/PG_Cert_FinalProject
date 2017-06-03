package login.system.servlets;

import login.system.dao.*;
import login.system.db.MySQL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        MySQL DB = new MySQL();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String content = request.getParameter("comment_body");
        String username = request.getParameter("username");
        int articleID = Integer.parseInt(request.getParameter("article_id"));
        User currentUser = UserDAO.getUser(DB, username);
        int userID = currentUser.getUser_id();
        if (request.getParameter("parentComment_id").length() > 0){
            int parentCommentID = Integer.parseInt(request.getParameter("parentComment_id"));
            Comment parent = CommentDAO.getCommentByID(DB, parentCommentID);
            String status = addReplyComment(parent, DB, userID, currentTime, content);
            System.out.println(status);
        }
        else {
            Article article = ArticleDAO.getArticle(DB, articleID);
            String status = addTopLevelComment(article, DB, userID, currentTime, content);
            System.out.println(status);
        }

    }

    protected String addTopLevelComment(Article article, MySQL DB, int userID, Timestamp timestamp, String content){
        int articleID = article.getArticle_id();
        String status = CommentDAO.addComment(DB, userID, articleID, -1, timestamp, content);
        return status;
    }

    protected String addReplyComment(Comment comment, MySQL DB, int userID, Timestamp timestamp, String content){
        int parentCommentID = comment.getCommentID();
        int articleID = comment.getArticleID();
        String status = CommentDAO.addComment(DB, userID, articleID, parentCommentID, timestamp, content);
        return status;
    }
}
