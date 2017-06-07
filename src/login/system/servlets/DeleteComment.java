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
public class DeleteComment extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MySQL DB = new MySQL();
        int commentID = Integer.parseInt(request.getParameter("commentID"));
        int articleID = Integer.parseInt(request.getParameter("articleID"));

        /*Thread - (1) (DB Query) comment deletion (and children) (2) (DB Query) check number of children with given parent_comment_id (and comment_id != comment_id), (DB Query) if length of returned table is zero, (DB Query) set isParent to 0 where parent_comment_id = comment_id */
        CommentDAO.deleteComment(DB, commentID);
        System.out.println(commentID);
        response.sendRedirect("/ViewArticle?article_id=" + articleID);
    }
}
