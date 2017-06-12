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
        int commentID = Integer.parseInt(request.getParameter("comment_id"));
        int articleID = Integer.parseInt(request.getParameter("article_id"));

        /*Setup delete comment thread and run*/
        Thread deleteComment = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("comment deletion started");
                CommentDAO.deleteComment(DB, commentID);
            }
        });
        deleteComment.run();

        /*Setup parent comment adjustment and run*/
        String parent_comment_id = request.getParameter("parent_comment_id");
        if (parent_comment_id != null && parent_comment_id.length() > 0){
            int parentID = Integer.parseInt(request.getParameter("parent_comment_id"));
            Thread commentAdjust = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("parent comment adjust started");
                    adjustParentCommentStatus(DB, commentID, parentID);
                }
            });
            commentAdjust.run();


            try {
                commentAdjust.join();
                System.out.println("adjust parent comment status finished");
                deleteComment.join();
                System.out.println("delete comment finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                deleteComment.join();
                System.out.println("delete comment finished");
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
