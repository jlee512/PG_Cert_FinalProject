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

/**
 * This servlet is used to delete a user account
 */

public class DeleteAccount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Create link to the database*/
        MySQL DB = new MySQL();
        HttpSession session = request.getSession(true);

        /*Check the user's login status and get the user's details*/
        if (session.getAttribute("loginStatus") != null) {

            User user = ((User) session.getAttribute("userDetails"));

            /*Loop through all user comments that will be deleted and check if the parent status of any comments need to be updated*/
            List<Comment> delete_user_comments = CommentDAO.getCommentsByAuthor(DB, user.getUser_id());
            int user_id = user.getUser_id();

            /*Update any parent comments as necessary*/
            CommentDAO.updateParentCommentStatus(DB, delete_user_comments);

            /*Delete the user account, remove the user's login status and re-direct to the login page*/
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

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
