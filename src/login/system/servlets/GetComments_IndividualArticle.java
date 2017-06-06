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

        int articleID = Integer.parseInt(request.getParameter("article_id"));

        List<Comment> commentList = CommentDAO.getCommentsByArticle(DB, articleID);

        response.setContentType("application/json");
        JSONArray top_level_comments = new JSONArray();

        for (int i = 0; i < commentList.size(); i++) {
            JSONObject singleComment = new JSONObject();
            singleComment.put("commentID", commentList.get(i).getCommentID());
            singleComment.put("authorID", commentList.get(i).getAuthorID());
            singleComment.put("timestamp", commentList.get(i).getTimestamp());
            singleComment.put("content", commentList.get(i).getContent());
        }

    }


}
