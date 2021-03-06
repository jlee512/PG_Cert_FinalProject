package backend.servlets;

import backend.dao.Article;
import backend.dao.ArticleDAO;
import backend.db.MySQL;
import backend.dao.User;
import org.json.simple.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by jlee512 on 5/06/2017.
 */

/**
 * This servlet accesses all articles to prepare the MainContent page.
 * Searching/sorting selections are processed here and a JSON array of articles is output for access bia AJAX calls
 */

public class MainContentAccess extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Create link to the database*/
        MySQL DB = new MySQL();
        HttpSession session = request.getSession();
          /*If user is logged out, redirect to login page*/
        if (session.getAttribute("loginStatus") != "active") {
            response.sendRedirect("Login");

        } else {

              /*Check if session has timed out*/
            if (!LoginAttempt.sessionExpirationRedirection(request, response)) {

                if (request.getParameter("from") == null || request.getParameter("count") == null || request.getParameter("sort_by") == null) {
                    response.sendRedirect("Content?username=" + ((User) (session.getAttribute("userDetails"))).getUsername() + "&notFound=true");
                } else {

                    /*Get the number of articles requested*/
                    int firstArticle = Integer.parseInt(request.getParameter("from"));
                    int articleCount = Integer.parseInt(request.getParameter("count"));
                    String sort_by = request.getParameter("sort_by");
                    String search_term = request.getParameter("search_term");
                    String ordering = request.getParameter("ordering");

                    /*Check the sort-by parameter and call the appropriate ArticleDAO method*/
                    List<Article> articles = null;

                    /*Case (A) If a search term is provided, output articles in accordance with the search term*/
                    if (search_term.length() > 0 || sort_by.equals("searchterm")) {
                        articles = ArticleDAO.getArticlePreviewsBySearchTerm(DB, search_term, firstArticle, articleCount);
                    }

                    /*Case (B) Else if the user has sorted the MainContent by title (ASC or DSC) output the articles in sorted order by title*/
                    else if (sort_by.equals("title")) {

                        switch (ordering) {

                            case "ASC":
                                articles = ArticleDAO.getfirstNArticlePreviewsByTitle(DB, firstArticle, articleCount);
                                break;

                            case "DESC":
                                articles = ArticleDAO.getfirstNArticlePreviewsByTitleDESC(DB, firstArticle, articleCount);
                                break;
                        }

                    /*Case (C) Else if the user has sorted the MainContent by author (ASC or DSC) output the articles in sorted order by author-name*/
                    } else if (sort_by.equals("author")) {

                        switch (ordering) {

                            case "ASC":
                                articles = ArticleDAO.getfirstNArticlePreviewsSortedByAuthor(DB, firstArticle, articleCount);
                                break;

                            case "DESC":
                                articles = ArticleDAO.getfirstNArticlePreviewsSortedByAuthorDESC(DB, firstArticle, articleCount);
                                break;
                        }

                    /*Case (D) Else if the user has sorted the MainContent by date (ASC or DSC) output the articles in sorted order by date*/
                    } else {

                        switch (ordering) {

                            case "ASC":
                                articles = ArticleDAO.getfirstNArticlePreviewsByDateASC(DB, firstArticle, articleCount);
                                break;

                            case "DESC":
                                articles = ArticleDAO.getfirstNArticlePreviewsByDate(DB, firstArticle, articleCount);
                                break;
                        }

                    }

                    /*Return a JSON object with the article information included*/
                    response.setContentType("application/json");
                    JSONArray articleDetails = new JSONArray();

                    IndividualAuthorArticles.constructArticlePreviewJSON(articles, articleDetails);

                    articleDetails.toJSONString();

                    response.getWriter().write(articleDetails.toString());

                }
            }
        }
    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
