package login.system.servlets;

import login.system.dao.ArticleDAO;
import login.system.dao.Comment;
import login.system.dao.CommentDAO;
import login.system.dao.User;
import login.system.db.MySQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * Created by jlee512 on 12/06/2017.
 */
public class DeleteAnArticle extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Establish a connection to the database*/
        MySQL DB = new MySQL();

        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("userDetails");

        int article_id = Integer.parseInt(request.getParameter("article_id"));

        int deletionStatus = ArticleDAO.deleteAnArticle(DB, article_id, user.getUser_id());


        if (deletionStatus == 1) {
        /*Redirect to the users profile page*/
            response.sendRedirect("ProfilePage?user_id=" + user.getUsername() + "&articleDeleted=true");
        } else {
            response.sendRedirect("ProfilePage?user_id=" + user.getUsername() + "&articleDeleted=false");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*To be implemented*/


    }
}
