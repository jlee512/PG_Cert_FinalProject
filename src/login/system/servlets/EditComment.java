package login.system.servlets;

import login.system.dao.CommentDAO;
import login.system.db.MySQL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cbla080 on 7/06/2017.
 */
public class EditComment extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MySQL DB = new MySQL();
        int commentID = Integer.parseInt(request.getParameter("comment_id"));
        int articleID = Integer.parseInt(request.getParameter("article_id"));
        String content = request.getParameter("comment_body");
        CommentDAO.editComment(DB, commentID, content);
        response.sendRedirect("/ViewArticle?article_id=" + articleID);
    }
}
