package login.system.servlets;

import login.system.dao.Comment;
import login.system.dao.CommentDAO;
import login.system.dao.User;
import login.system.dao.UserDAO;
import login.system.db.MySQL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by jlee512 on 6/06/2017.
 */
public class GetComments_IndividualArticle extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*Create link to the database*/
        MySQL DB = new MySQL();

        HttpSession session = request.getSession(true);
        if (session.getAttribute("loginStatus") != "active") {
            response.sendRedirect("Login");

        } else {

             /*Check if session has timed out*/
            if (!LoginAttempt.sessionExpirationRedirection(request, response)) {

                /*If the user is trying to access the servlet directly with one or more missing parameters, redirect to the homepage with error message*/
                if (request.getParameter("article_id") == null || request.getParameter("from") == null || request.getParameter("count") == null) {
                    User user = ((User) session.getAttribute("userDetails"));
                    response.sendRedirect("Content?username=" + user.getUsername() + "&notFound=true");
                }

                else {
                    /*Get parameters from request*/
                    int articleID = Integer.parseInt(request.getParameter("article_id"));
                    int firstComment = Integer.parseInt(request.getParameter("from"));
                    int commentCount = Integer.parseInt(request.getParameter("count"));

                    /*Get the comments from database*/
                    List<Comment> commentList = CommentDAO.getTopLevelCommentsByArticle(DB, articleID, firstComment, commentCount);

                    /*Write JSON information for each comment*/
                    response.setContentType("application/json");
                    JSONArray top_level_comments = new JSONArray();

                    for (int i = 0; i < commentList.size(); i++) {
                        JSONObject singleComment = new JSONObject();
                        singleComment.put("comment_id", commentList.get(i).getCommentID());
                        singleComment.put("author_id", commentList.get(i).getAuthorID());
                        singleComment.put("timestamp", commentList.get(i).getTimestamp().getTime());
                        singleComment.put("content", commentList.get(i).getContent());
                        singleComment.put("username", commentList.get(i).getAuthor_username());
                        singleComment.put("firstname", commentList.get(i).getAuthor_firstname());
                        singleComment.put("lastname", commentList.get(i).getAuthor_lastname());
                        singleComment.put("isParent", commentList.get(i).getIsParent());
                        top_level_comments.add(i, singleComment);
                    }

                    response.getWriter().write(top_level_comments.toJSONString());

                }
            }
        }
    }

}
