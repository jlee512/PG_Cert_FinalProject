package login.system.servlets;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import login.system.dao.*;
import login.system.db.MySQL;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by cbla080 on 3/06/2017.
 */
public class ViewArticle extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*Setup the database*/
        MySQL DB = new MySQL();


        //Pass the articleID as a parameter when clicking the link to the article.
        int articleID = Integer.parseInt(request.getParameter("article_id"));

        /*Store the article ID in a cookie for use on the server side*/
        Cookie article_id_cookie = new Cookie("article_id", Integer.toString(articleID));
        article_id_cookie.setMaxAge(2 * 60 * 60); //Max age 2 hours
        article_id_cookie.setPath("/");
        response.addCookie(article_id_cookie);

        //Get Article object by ID from ArticleDAO.
        Article article = ArticleDAO.getArticle(DB, articleID);
        Timestamp timestamp = article.getArtcle_timestamp();
        String date_for_output = new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(timestamp);

        //Set it as an attribute to pass to the JSP.
        request.setAttribute("article", article);
        request.setAttribute("date", date_for_output);


        getServletContext().getRequestDispatcher("/ViewArticlePage").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        //something
    }
}
