package login.system.servlets;

import login.system.dao.Comment;
import login.system.dao.CommentDAO;
import login.system.db.MySQL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by cbla080 on 7/06/2017.
 */
public class DeleteComment extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MySQL DB = new MySQL();
        int commentID = Integer.parseInt(request.getParameter("commentID"));
        int articleID = Integer.parseInt(request.getParameter("articleID"));
        CommentDAO.deleteComment(DB, commentID);
        String parent_comment_id = request.getParameter("parentCommentID");
        if (parent_comment_id != null && parent_comment_id.length() > 0){
            int parentID = Integer.parseInt(request.getParameter("parentCommentID"));
            Thread commentAdjust = new Thread(new Runnable() {
                @Override
                public void run() {
                    adjustParentCommentStatus(DB, commentID, parentID);
                }
            });
            commentAdjust.run();
            try {
                commentAdjust.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("/ViewArticle?article_id=" + articleID);
    }

    private void adjustParentCommentStatus(MySQL DB, int commentID, int parentCommentID){
        List<Comment> commentList = CommentDAO.getNestedComments(DB, parentCommentID);
        if (commentList.size() == 0){
            CommentDAO.setCommentNotParent(DB, parentCommentID);
        }
        else if (commentList.size() == 1){
            Comment childComment = commentList.get(0);
            if (childComment.getCommentID() == commentID){
                CommentDAO.setCommentNotParent(DB, parentCommentID);
            }
        }
    }

}
