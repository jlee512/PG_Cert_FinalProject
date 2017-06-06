package login.system.servlets;

import login.system.dao.Article;
import login.system.dao.ArticleDAO;
import login.system.db.MySQL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by jlee512 on 5/06/2017.
 */
public class MainContentAccess extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Create link to the database*/
        MySQL DB = new MySQL();

        /*Get the number of articles requested*/
        int firstArticle = Integer.parseInt(request.getParameter("from"));
        int articleCount = Integer.parseInt(request.getParameter("count"));

        List<Article> articles = ArticleDAO.getfirstNArticlePreviewsByDate(DB, firstArticle, articleCount);


        /*Return a JSON object with the article information included*/
        response.setContentType("application/json");
        JSONArray articleDetails = new JSONArray();

        for (int i = 0; i < articles.size(); i++) {
            JSONObject singleArticle = new JSONObject();
            singleArticle.put("article_id", articles.get(i).getArticle_id());
            singleArticle.put("article_title", articles.get(i).getArticle_title());
            singleArticle.put("article_date", articles.get(i).getArticle_date().getTime());
            singleArticle.put("author_username", articles.get(i).getAuthor_username());

            singleArticle.put("author_firstname", articles.get(i).getAuthor_firstname());
            singleArticle.put("author_lastname", articles.get(i).getAuthor_lastname());
            singleArticle.put("article_body", articles.get(i).getArticle_body());
            articleDetails.add(i, singleArticle);
        }

        articleDetails.toJSONString();

        response.getWriter().write(articleDetails.toString());

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //DO POST

    }
}
