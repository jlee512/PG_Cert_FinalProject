package backend.servlets;

import backend.dao.ArticleDAO;
import backend.dao.User;
import backend.db.MySQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

        } else {

            /*Check if session has timed out*/
            if (!LoginAttempt.sessionExpirationRedirection(request, response)) {

                if (request.getParameter("article_id") == null) {
                    response.sendRedirect("Content?username=" + user.getUsername() + "&notFound=true");
                } else {

                    int article_id = Integer.parseInt(request.getParameter("article_id"));
                /*If the user is not authorized to delete the article, redirect them to their homepage.*/
//                    if (!verifyUserAuthorization(DB, user.getUser_id(), article_id)) {
//                        System.out.println("test redirect to login with verification");
//                        response.sendRedirect("Content?username=" + user.getUsername());
//                    } else {
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
        }
    }


//      Removed this method as it is not necessary. This authentication is carried out automatically in the DAO
//    private boolean verifyUserAuthorization(MySQL DB, int user_id, int article_id){
//        boolean userAuthorized = false;
//        System.out.println(user_id);
//        System.out.println(article_id);
//        /*If the user wrote the article they can delete it.*/
//        Article article = ArticleDAO.getArticle(DB, article_id);
//        System.out.println(article.getAuthor_id());
//        if (article.getAuthor_id() == user_id){
//            userAuthorized = true;
//        }
//        System.out.println(userAuthorized);
//        return userAuthorized;
//    }
//}