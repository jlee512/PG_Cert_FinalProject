package backend.servlets;

import backend.dao.*;
import backend.db.MySQL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by cbla080 on 7/06/2017.
 */
public class DeleteComment extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        /*If user is not logged in redirect to Login page*/
        HttpSession session = request.getSession(true);
        if (session.getAttribute("loginStatus") != "active") {
            response.sendRedirect("Login");

        } else {

              /*Check if session has timed out*/
            if (!LoginAttempt.sessionExpirationRedirection(request, response)) {

                MySQL DB = new MySQL();

                User user = (User) session.getAttribute("userDetails");

            /*If user tries to access DeleteComment directly with no parameters, redirect to homepage with error message*/
                if (request.getParameter("comment_id") == null || request.getParameter("article_id") == null) {
                    response.sendRedirect("Content?username=" + user.getUsername() + "&notFound=true");

                } else {
                    /*Get details from request*/
                    int commentID = Integer.parseInt(request.getParameter("comment_id"));
                    int articleID = Integer.parseInt(request.getParameter("article_id"));

                /*Verify that User is authorized to delete comment.*/
                    if (!verifyUserAuthorization(DB, user.getUser_id(), commentID, articleID)) {
                        response.sendRedirect("Content?username=" + user.getUsername());
                    } else {

                        String parent_comment_id = request.getParameter("parent_comment_id");

                        System.out.println("comment deletion started");

                        if (parent_comment_id == null || parent_comment_id.length() <= 0) {

                            CommentDAO.deleteComment(DB, commentID);

                        } else {
                            int parentID = Integer.parseInt(parent_comment_id);

                            CommentDAO.deleteIndividualNestedComment(DB, commentID, parentID);

                        }
                /*Setup parent comment adjustment and run*/
                        System.out.println("delete comment finished");
                    }
                    response.sendRedirect("ViewArticle?article_id=" + articleID);
                }
            }
        }
    }

    private boolean verifyUserAuthorization(MySQL DB, int userID, int commentID, int articleID) {
        boolean userAuthorized = false;

        /*If the user wrote the comment, they are authorized to delete it.*/
        Comment comment = CommentDAO.getCommentByID(DB, commentID);
        if (comment.getAuthorID() == userID) {
            userAuthorized = true;
        }
        else {
            /*If the user wrote the article they can delete any comments on the article.*/
            Article article = ArticleDAO.getArticle(DB, articleID);
            if (article.getAuthor_id() == userID) {
                userAuthorized = true;
            }
        }
        return userAuthorized;
    }

}
