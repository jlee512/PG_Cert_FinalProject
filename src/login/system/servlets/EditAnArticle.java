package login.system.servlets;

import login.system.dao.ArticleDAO;
import login.system.dao.User;
import login.system.db.MySQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by Julian on 12/06/2017.
 */
public class EditAnArticle extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*If there is an attempt to access a servlet directly, check login status and redirect to login page or content page as is appropriate (method defined below)*/
        LoginAttempt.loginStatusRedirection(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Establish connection to the database*/
        MySQL DB = new MySQL();

        /*Get user details from the current session (redirect if user is not logged in)*/
        HttpSession session = request.getSession(true);
        if (session.getAttribute("loginStatus") != "active") {
            response.sendRedirect("Login");
        }
        else {
            User user = (User) session.getAttribute("userDetails");
            int author_id = user.getUser_id();

        /*Access POST parameters*/
            int article_id = Integer.parseInt(request.getParameter("editArticle"));
            String title_update = request.getParameter("article_title_input");
            String body_update = request.getParameter("article_body_input");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());


            ArticleDAO.updateArticle(DB, article_id, author_id, title_update, body_update, currentTimestamp);

            response.sendRedirect("ProfilePage?username=" + user.getUsername());

        }
    }
}
