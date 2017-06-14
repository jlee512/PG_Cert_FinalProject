package login.system.servlets;

import login.system.dao.*;
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

        /*If user is not logged in, redirect them to login page.*/
        if (session.getAttribute("loginStatus") != "active") {
            response.sendRedirect("Login");

        } else
            {
                int article_id = Integer.parseInt(request.getParameter("article_id"));
                /*If the user is not authorized to delete the article, redirect them to their homepage.*/
                if (!verifyUserAuthorization(DB, user.getUser_id(), article_id)){
                    response.sendRedirect("Content?username=" + user.getUsername());
                }
                else {
                    int deletionStatus = ArticleDAO.deleteAnArticle(DB, article_id, user.getUser_id());
                    if (deletionStatus == 1) {
                        /*Redirect to the users profile page*/
                        response.sendRedirect("ProfilePage?user_id=" + user.getUsername() + "&articleDeleted=true");
                    } else {
                        response.sendRedirect("ProfilePage?user_id=" + user.getUsername() + "&articleDeleted=false");
                    }
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*To be implemented*/
    }

    private boolean verifyUserAuthorization(MySQL DB, int user_id, int article_id){
        boolean userAuthorized = false;
        /*If the user wrote the article they can delete it.*/
        Article article = ArticleDAO.getArticle(DB, article_id);
        if (article.getAuthor_id() == user_id){
            userAuthorized = true;
        }
        return userAuthorized;
    }
}
