package backend.servlets;

import backend.dao.Comment;
import backend.dao.CommentDAO;
import backend.dao.User;
import backend.dao.UserDAO;
import backend.db.MySQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ycow194 on 7/06/2017.
 */
public class DeleteAccount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Create link to the database*/
        MySQL DB = new MySQL();


        /*Access username stored within the session*/
        HttpSession session = request.getSession(true);

        if (session.getAttribute("loginStatus") != null) {

            User user = ((User) session.getAttribute("userDetails"));

            /*Cycle through all user comments that will be delete and check if the parent status of any comments need to be updated*/
            List<Comment> delete_user_comments = CommentDAO.getCommentsByAuthor(DB, user.getUser_id());
            int user_id = user.getUser_id();

            updateParentCommentStatus(DB, delete_user_comments);

            UserDAO.deleteUserAccount(DB, user.getUsername());
            session.removeAttribute("loginStatus");
            session.removeAttribute("userDetails");
        /*Invalidate session and redirect to login page*/
            session.invalidate();
            response.sendRedirect("Login");
        } else {
        /*Else redirect to login page and print message saying that the user is no longer logged in*/
            session.invalidate();
            response.sendRedirect("Login?loginStatus=loggedOut");
        }
    }

    private void updateParentCommentStatus(MySQL DB, List<Comment> delete_user_comments) {
        try (Connection conn = DB.connection()) {

            for (int i = 0; i < delete_user_comments.size(); i++) {

                try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(comment_id) FROM posted_comments WHERE parent_comment_id = ? AND author_id != ?;")) {

                    stmt.setInt(1, delete_user_comments.get(i).getParentCommentID());
                    stmt.setInt(2, delete_user_comments.get(i).getAuthorID());

                    try (ResultSet resultSet = stmt.executeQuery()) {

                        /*Get the count and if zero, update the is_parent status to be false*/
                        resultSet.next();
                        int number_of_children = resultSet.getInt(1);

                        if (number_of_children == 0) {

                            try (PreparedStatement stmt1 = conn.prepareStatement("UPDATE posted_comments SET is_parent = false WHERE comment_id = ?")) {
                                stmt1.setInt(1, delete_user_comments.get(i).getParentCommentID());
                                stmt1.executeUpdate();

                            }

                        }


                    }

                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
