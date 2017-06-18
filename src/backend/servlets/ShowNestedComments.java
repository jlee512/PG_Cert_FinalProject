package backend.servlets;

import backend.dao.Comment;
import backend.dao.CommentDAO;
import backend.dao.User;
import backend.db.MySQL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by cbla080 on 5/06/2017.
 */
public class ShowNestedComments extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MySQL DB = new MySQL();

        //Don't let users see comments if they are not logged in.
        HttpSession session = request.getSession(true);
        if (session.getAttribute("loginStatus") != "active") {
            response.sendRedirect("Login");

        } else {

              /*Check if session has timed out*/
            if (!LoginAttempt.sessionExpirationRedirection(request, response)) {

                if (request.getParameter("parentCommentID") == null) {
                    response.sendRedirect("Content?username=" + ((User) (session.getAttribute("userDetails"))).getUsername() + "&notFound=true");
                } else {
                    int parentCommentID = Integer.parseInt(request.getParameter("parentCommentID"));
                    List<Comment> commentList = CommentDAO.getNestedComments(DB, parentCommentID);

                    response.setContentType("application/json");
                    PrintWriter out = response.getWriter();
                    JSONArray commentDetails = new JSONArray();

                    for (Comment comment : commentList) {
                        JSONObject jsonComment = new JSONObject();
                        jsonComment.put("comment_id", comment.getCommentID());
                        jsonComment.put("author_id", comment.getAuthorID());
                        jsonComment.put("article_id", comment.getArticleID());
                        jsonComment.put("parent_comment_id", comment.getParentCommentID());
                        jsonComment.put("timestamp", comment.getTimestamp().getTime());
                        jsonComment.put("content", comment.getContent());
                        jsonComment.put("is_parent", comment.getIsParent());
                        jsonComment.put("author_username", comment.getAuthor_username());
                        jsonComment.put("author_firstname", comment.getAuthor_firstname());
                        jsonComment.put("author_lastname", comment.getAuthor_lastname());
                        commentDetails.add(jsonComment);
                    }

                    commentDetails.toJSONString();
                    out.println(commentDetails);
                }
            }
        }
    }
}
