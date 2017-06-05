package login.system.servlets;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import login.system.dao.*;
import login.system.db.MySQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by cbla080 on 3/06/2017.
 */
public class ViewArticle extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Pass the articleID as a parameter when clicking the link to the article.
        int articleID = Integer.parseInt(request.getParameter("article_id"));
        System.out.println(articleID);
        MySQL DB = new MySQL();

        //Get Article object by ID from ArticleDAO.
        Article article = ArticleDAO.getArticle(DB, articleID);
        //Set it as an attribute to pass to the JSP.
        request.setAttribute("article", article);

        //Get the author's username to display on Article page.
        int authorID = article.getAuthor_id();
        User user = UserDAO.getUser(DB, authorID);
        String username = user.getUsername();
        request.setAttribute("username", username);

        List<Comment> commentList = CommentDAO.getCommentsByArticle(DB, articleID);
        request.setAttribute("commentList", commentList);
        getServletContext().getRequestDispatcher("/ViewArticlePage").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        //something
    }
}
