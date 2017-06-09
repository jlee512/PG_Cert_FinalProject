package login.system.dao;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import login.system.db.MySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycow194 on 1/06/2017.
 */
public class ArticleDAO {

    /*Add a new article to the Database*/
    public static int addArticleToDB(MySQL DB, int author_id, String article_title, Date date, String article_body) {

        /*Return method status
        * (1) Success
        * (2) Integrity constraint violation (duplicate user)
        * (3) SQL error
        * (4) Database connection error*/
    /*------------------------------------------------------------*/

        Article tempArticle = new Article(author_id, article_title, date, article_body);

        try (Connection c = DB.connection()) {
        /*Connect to the database and add user*/
            try (PreparedStatement stmt = c.prepareStatement("INSERT INTO uploaded_articles (author_id, date, article_title, article_body) VALUES (?, ?, ?, ?)")) {

                stmt.setInt(1, tempArticle.getAuthor_id());
                stmt.setDate(2, tempArticle.getArticle_date());
                stmt.setString(3, tempArticle.getArticle_title());
                stmt.setString(4, tempArticle.getArticle_body());

                /*Execute prepared statement*/
                stmt.executeUpdate();
                System.out.println("Article added to the database");
                return 1;
            }
        }  catch (SQLIntegrityConstraintViolationException e) {
            return 2;
        } catch (SQLException e) {
            e.printStackTrace();
            return 3;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return 4;
        }

    }

    public static Article getArticle(MySQL DB, int article_id) {
        /*----------------------------------------------------*/

        /*Dummy article to be returned if article not found*/
        Article article = new Article(-1, null, null, null);

        try (Connection c = DB.connection()) {
            try (PreparedStatement stmt = c.prepareStatement("SELECT article_id, username, firstname, lastname, date, article_title, article_body FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id WHERE article_id = ?")) {

                stmt.setInt(1, article_id);

                try (ResultSet r = stmt.executeQuery()) {

                    if (r.next()) {
                        /*If there is a next result, the article exists in the database*/
                        /*Get article_id, author_id, date, article_title and article_body*/
                        int article_idLookup = r.getInt("article_id");
                        String author_username = r.getString("username");
                        String author_firstname = r.getString("firstname");
                        String author_lastname = r.getString("lastname");
                        Date dateLookup = r.getDate("date");
                        String article_titleLookup = r.getString("article_title");
                        String article_bodyLookup = r.getString("article_body");

                        article.setArticleParameters(article_idLookup, author_username, author_firstname, author_lastname, article_titleLookup, dateLookup, article_bodyLookup);

                        System.out.println("Article retrieved from the database");
                    } else {
                        /*If the article can't be found in the database, return null article*/
                        System.out.println("Article could not be found in the database");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*----------------------------------------------------------*/
        return article;
    }

    public static List<Article> getfirstNArticlePreviewsByDate(MySQL DB, int fromArticle, int numArticles) {

        /*Dummy article to be returned if article not found*/
        List<Article> articles = new ArrayList<Article>();

        try (Connection c = DB.connection()) {

            try (PreparedStatement stmt = c.prepareStatement("SELECT article_id, username, firstname, lastname, date, article_title, SubString(article_body, 1, 100) AS article_preview FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id ORDER BY DATE DESC LIMIT ? OFFSET ?;")) {

                stmt.setInt(1, numArticles);
                stmt.setInt(2, fromArticle);

                getListofArticles(articles, stmt);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public static List<Article> getfirstNArticlePreviewsByAuthor(MySQL DB, int fromArticle, int numArticles , int author_id) {

        /*Dummy article to be returned if article not found*/
        List<Article> articles = new ArrayList<Article>();

        try (Connection c = DB.connection()) {

            try (PreparedStatement stmt = c.prepareStatement("SELECT article_id, username, firstname, lastname, date, article_title, SubString(article_body, 1, 100) AS article_preview FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id WHERE user_id = ? ORDER BY DATE DESC LIMIT ? OFFSET ?;")) {

                stmt.setInt(1, author_id);
                stmt.setInt(2, numArticles);
                stmt.setInt(3, fromArticle);

                getListofArticles(articles, stmt);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return articles;
    }


    /*Extracted method during refactoring to reduce code repitition*/
    public static void getListofArticles(List<Article> articles, PreparedStatement stmt) throws SQLException {
        try (ResultSet r = stmt.executeQuery()) {

            while (r.next()) {
                /*If there is a next result, the user has articles in the database*/
                /*Get article_id, author_id, date, article_title and article_body and add to the list of articles retrieved*/
                int article_idLookup = r.getInt("article_id");
                String author_username = r.getString("username");
                String author_firstname = r.getString("firstname");
                String author_lastname = r.getString("lastname");
                Date dateLookup = r.getDate("date");
                String article_titleLookup = r.getString("article_title");
                String article_bodyLookup = r.getString("article_preview") + "...";

                Article article = new Article(article_idLookup, author_username, author_firstname, author_lastname, article_titleLookup, dateLookup, article_bodyLookup);
                article.setArticle_id(article_idLookup);
                articles.add(article);
            }

            if (articles.size() > 0){
                System.out.println("Article retrieved from the database");
            } else {
                /*If the article can't be found in the database, return null article*/
                System.out.println("Article could not be found in the database");
            }
        }
    }

}
