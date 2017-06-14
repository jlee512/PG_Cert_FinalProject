package login.system.dao;

import login.system.db.MySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycow194 on 1/06/2017.
 */
public class ArticleDAO {

    /*Add a new article to the Database*/
    public static int addArticleToDB(MySQL DB, int author_id, String article_title, Timestamp article_timestamp, String article_body) {

        /*Return method status
        * (1) Success
        * (2) Integrity constraint violation (duplicate user)
        * (3) SQL error
        * (4) Database connection error*/
    /*------------------------------------------------------------*/

        Article tempArticle = new Article(author_id, article_title, article_timestamp, article_body);

        try (Connection c = DB.connection()) {
        /*Connect to the database and add user*/
            try (PreparedStatement stmt = c.prepareStatement("INSERT INTO uploaded_articles (author_id, timestamp, article_title, article_body) VALUES (?, ?, ?, ?)")) {

                stmt.setInt(1, tempArticle.getAuthor_id());
                stmt.setTimestamp(2, tempArticle.getArtcle_timestamp());
                stmt.setString(3, tempArticle.getArticle_title());
                stmt.setString(4, tempArticle.getArticle_body());

                /*Execute prepared statement*/
                stmt.executeUpdate();
                System.out.println("Article added to the database");
                return 1;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
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
            try (PreparedStatement stmt = c.prepareStatement("SELECT uploaded_articles.article_id, username, firstname, lastname, uploaded_articles.timestamp, article_title, article_body, COUNT(posted_comments.article_id) AS comment_count FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id INNER JOIN posted_comments ON posted_comments.article_id = uploaded_articles.article_id WHERE uploaded_articles.article_id = ?")) {

                stmt.setInt(1, article_id);

                try (ResultSet r = stmt.executeQuery()) {

                    if (r.next()) {
                        /*If there is a next result, the article exists in the database*/
                        /*Get article_id, author_id, date, article_title and article_body*/
                        int article_idLookup = r.getInt("article_id");
                        String author_username = r.getString("username");
                        String author_firstname = r.getString("firstname");
                        String author_lastname = r.getString("lastname");
                        Timestamp timestampLookup = r.getTimestamp("timestamp");
                        String article_titleLookup = r.getString("article_title");
                        String article_bodyLookup = r.getString("article_body");
                        int comment_countLookup = r.getInt("comment_count");

                        article.setArticleParameters(article_idLookup, author_username, author_firstname, author_lastname, article_titleLookup, timestampLookup, article_bodyLookup, comment_countLookup);

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

            try (PreparedStatement stmt = c.prepareStatement("SELECT uploaded_articles.article_id, username, firstname, lastname, uploaded_articles.timestamp, article_title, article_body AS article_preview, COUNT(posted_comments.article_id) AS comment_count FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id LEFT JOIN posted_comments ON uploaded_articles.article_id = posted_comments.article_id GROUP BY uploaded_articles.article_id ORDER BY TIMESTAMP DESC LIMIT ? OFFSET ?;")) {

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

    public static List<Article> getfirstNArticlePreviewsByAuthor(MySQL DB, int fromArticle, int numArticles, int author_id) {

        /*Dummy article to be returned if article not found*/
        List<Article> articles = new ArrayList<Article>();

        try (Connection c = DB.connection()) {

            try (PreparedStatement stmt = c.prepareStatement("SELECT uploaded_articles.article_id, username, firstname, lastname, uploaded_articles.timestamp, article_title, article_body AS article_preview, COUNT(posted_comments.article_id) AS comment_count FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id LEFT JOIN posted_comments ON posted_comments.article_id = uploaded_articles.article_id WHERE user_id = ? GROUP BY uploaded_articles.article_id ORDER BY TIMESTAMP DESC LIMIT ? OFFSET ?;")) {

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


    /*Method to delete and article*/
    public static int deleteAnArticle(MySQL DB, int article_id, int author_id) {

        /*Establish connection to the database and delete the article (note: the corresponding servlet will need to confirm user log-in status prior to deletion*/

        try (Connection c = DB.connection()) {

            /*Delete all comments associated with the article_id (cascading does not work with the directionality of the foreign key arrangement)*/
            try (PreparedStatement stmt = c.prepareStatement("DELETE FROM posted_comments WHERE article_id = ?")) {

                stmt.setInt(1, article_id);
                /*Execute the prepared statement*/
                int deleted = stmt.executeUpdate();
                if (deleted == 0) {
                    System.out.println("No article comments to delete");
                } else {
                    System.out.println("Article comments deleted");
                }
            }

            /*Provided the article's comments are deleted successfully, the database should now be permitted to delete the article itself*/
            try (PreparedStatement stmt = c.prepareStatement("DELETE FROM uploaded_articles WHERE article_id = ? AND author_id = ?")) {

                stmt.setInt(1, article_id);
                stmt.setInt(2, author_id);

                /*Execute the prepared statement*/
                int deletedArticle = stmt.executeUpdate();
                if (deletedArticle == 0) {

                    System.out.println("No comments to delete");
                    return 2;

                } else {
                    System.out.println("Article successfully deleted");
                    return 1;
                }
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            return 2;

        } catch (SQLException e) {
            e.printStackTrace();
            return 3;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return 4;

        }
    }

//    public static int editAnArticle (MySQL DB, int article_id, )

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
                Timestamp timestampLookup = r.getTimestamp("timestamp");
                String article_titleLookup = r.getString("article_title");
                String article_bodyLookup = r.getString("article_preview");
                int comment_countLookup = r.getInt("comment_count");

                Article article = new Article(article_idLookup, author_username, author_firstname, author_lastname, article_titleLookup, timestampLookup, article_bodyLookup, comment_countLookup);
                article.setArticle_id(article_idLookup);
                articles.add(article);
            }

            if (articles.size() > 0) {
                System.out.println("Article retrieved from the database");
            } else {
                /*If the article can't be found in the database, return null article*/
                System.out.println("Article could not be found in the database");
            }
        }
    }

    /*Update and article method for use with the edit article servlet doPost method*/
    public static int updateArticle(MySQL DB, int article_id, int author_id, String article_title_update, String article_body_update, Timestamp timestamp_update) {

        /*Return an integer representative of the status
        * (1) Successful update
        * (2) Integrity constraint
        * (3) Other invalid update request
        * (4) Database connectivity issues
        * */

        try (Connection c = DB.connection()) {

            /*Carry out article update based on provided article_id, article_title and article_body)*/
            try (PreparedStatement stmt = c.prepareStatement("UPDATE uploaded_articles SET timestamp = ?, article_title = ?, article_body = ?  WHERE article_id = ? AND author_id = ?;")) {

                /*Set the query parameters*/
                stmt.setTimestamp(1, timestamp_update);
                stmt.setString(2, article_title_update);
                stmt.setString(3, article_body_update);
                stmt.setInt(4, article_id);
                stmt.setInt(5, author_id);

                /*Execute prepared statement*/
                stmt.executeUpdate();
                System.out.println("Article updated in the database");
                return 1;
            }

        } catch (SQLIntegrityConstraintViolationException e){
            e.printStackTrace();
            return 2;
    }catch (SQLException e) {
            e.printStackTrace();
            return 3;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return 4;
        }
    }


    /*-----------END OF ARTICLE DAO--------------*/
}
