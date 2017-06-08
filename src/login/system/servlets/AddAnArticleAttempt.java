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
import java.sql.Date;

/**
 * Created by jlee512 on 8/06/2017.
 */
public class AddAnArticleAttempt extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*If there is an attempt to access a servlet directly, check login status and redirect to login page or content page as is appropriate (method defined below)*/
        LoginAttempt.loginStatusRedirection(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*Create a link to the database*/
        MySQL DB = new MySQL();

        /*Pull the relevant user details from the current session for addition to the database*/
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("userDetails");
        int author_id = user.getUser_id();
        String username = user.getUsername();
        Date currentDate = new Date(System.currentTimeMillis());

        String article_title_input = request.getParameter("article_title_input");
        String article_body_input = request.getParameter("article_body_input");

        int articleAdditionStatus = ArticleDAO.addArticleToDB(DB, author_id, article_title_input, currentDate, article_body_input);

        switch (articleAdditionStatus) {
            case 1:
                System.out.println("Article added successfully");
                /*If successful article addition, reload the user's profile page*/
                response.sendRedirect("ProfilePage?username=" + username);
                break;
            case 2:
                System.out.println("User already exists within the database");
                /*If the article is non-unique (this is not expected as it is based on a combination of article title and article date), return the user to the profile page and display a descriptive message*/
                response.sendRedirect("ProfilePage?username=" + username + "&articleAdditionStatus=exists");
                break;
            case 3:
                /*If an invalid article, return the user to the registration page and display a descriptive message*/
                response.sendRedirect("ProfilePage?username=" + username + "&articleAdditionStatus=invalid");
                break;
            case 4:
                System.out.println("No connection to the database");
                response.sendRedirect("ProfilePage?username=" + username + "&articleAdditionStatus=dbConn");
                break;
        }

    }
}
