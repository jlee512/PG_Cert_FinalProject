package login.system.servlets;

import login.system.dao.CommentDAO;
import login.system.dao.User;
import login.system.dao.UserDAO;
import login.system.db.MySQL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.sql.Date;

/**
 * Created by catherinedechalain on 1/06/17.
 */
public class AddCommentAttempt extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        //redirect
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        MySQL DB = new MySQL();
        Date currentDate = new Date(System.currentTimeMillis());

        String content = request.getParameter("comment_body");
        String username = request.getParameter("username");
        //article to be passed as object from previous page.

        User currentUser = UserDAO.getUser(DB, username);
        int userID = currentUser.getUser_id();
        String status = CommentDAO.addComment(DB, userID, 0, 0, currentDate, content);
        System.out.println(status);
    }
}
