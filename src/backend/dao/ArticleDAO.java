package backend.dao;

import backend.db.MySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The ArticleDAO class links the backend representation of an article object with the database.
 *
 * The contained methods are called from the relevant servlets.
 *
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

    /*Create an article instance to be used when passing values to the database*/
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
                /*If no exception is thrown, the addition has been carried out successfully*/
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



    /*---------------------------------------------------------------*/
    /*Get an individual article by the MySQL article_id
    * Used in the DeleteArticle and ViewArticle Servlets*/

    public static Article getArticle(MySQL DB, int article_id) {

        /*Dummy article to be returned if article not found*/
        Article article = new Article(-1, null, null, null);

        try (Connection c = DB.connection()) {
            try (PreparedStatement stmt = c.prepareStatement("SELECT uploaded_articles.article_id, uploaded_articles.author_id, username, firstname, lastname, uploaded_articles.timestamp, article_title, article_body, COUNT(posted_comments.article_id) AS comment_count FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id INNER JOIN posted_comments ON posted_comments.article_id = uploaded_articles.article_id WHERE uploaded_articles.article_id = ?")) {

                stmt.setInt(1, article_id);

                try (ResultSet r = stmt.executeQuery()) {

                    if (r.next()) {
                        /*If there is a next result, the article exists in the database*/
                        /*Get article_id, author_id, date, article_title and article_body*/
                        int article_idLookup = r.getInt("article_id");
                        int author_idLookup = r.getInt("author_id");
                        String author_username = r.getString("username");
                        String author_firstname = r.getString("firstname");
                        String author_lastname = r.getString("lastname");
                        Timestamp timestampLookup = r.getTimestamp("timestamp");
                        String article_titleLookup = r.getString("article_title");
                        String article_bodyLookup = r.getString("article_body");
                        int comment_countLookup = r.getInt("comment_count");

                        article.setArticleParameters(article_idLookup, author_username, author_firstname, author_lastname, article_titleLookup, timestampLookup, article_bodyLookup, comment_countLookup);
                        article.setAuthor_id(author_idLookup);

                        /*If no exceptions are thrown, the method has been compeleted successfully*/
                    } else {
                        /*If the article can't be found in the database and the null article will be returned*/
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*Either a specific article or a null article are returned. This outcomes are recognised in the specific servlets*/
        return article;
    }


    /*---------------------------------------------------------------*/
    /*This method is used to access the first 'N' articles in date order.
    * The method takes the initial article number and a number of articles.
    * It is called from the MainContentAccess Servlet (which is accessed via AJAX)*/

    public static List<Article> getfirstNArticlePreviewsByDate(MySQL DB, int fromArticle, int numArticles) {

        /*Dummy list of articles to be returned if article not found*/
        List<Article> articles = new ArrayList<Article>();

        try (Connection c = DB.connection()) {

            try (PreparedStatement stmt = c.prepareStatement("SELECT uploaded_articles.article_id, username, firstname, lastname, uploaded_articles.timestamp, article_title, article_body AS article_preview, COUNT(posted_comments.article_id) AS comment_count FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id LEFT JOIN posted_comments ON uploaded_articles.article_id = posted_comments.article_id GROUP BY uploaded_articles.article_id ORDER BY TIMESTAMP DESC LIMIT ? OFFSET ?;")) {

                stmt.setInt(1, numArticles);
                stmt.setInt(2, fromArticle);

                /*This method cycles through a table of articles produced by an SQL query.
                The parameters returned from the database are put into a list of Article instances*/
                getListofArticles(articles, stmt);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*Returns a list of 'N' articles or a list of size = 0 if no articles are returned by a given query*/
        return articles;
    }


    /*---------------------------------------------------------------*/
    /*This method is used to access the first 'N' articles in date order (ascending).
    * The method takes the initial article number and a number of articles.
    * It is called from the MainContentAccess Servlet (which is accessed via AJAX)*/

    public static List<Article> getfirstNArticlePreviewsByDateASC(MySQL DB, int fromArticle, int numArticles) {

        /*Dummy list of articles to be returned if article not found*/
        List<Article> articles = new ArrayList<Article>();

        try (Connection c = DB.connection()) {

            try (PreparedStatement stmt = c.prepareStatement("SELECT uploaded_articles.article_id, username, firstname, lastname, uploaded_articles.timestamp, article_title, article_body AS article_preview, COUNT(posted_comments.article_id) AS comment_count FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id LEFT JOIN posted_comments ON uploaded_articles.article_id = posted_comments.article_id GROUP BY uploaded_articles.article_id ORDER BY TIMESTAMP ASC LIMIT ? OFFSET ?;")) {

                stmt.setInt(1, numArticles);
                stmt.setInt(2, fromArticle);

                /*This method cycles through a table of articles produced by and SQL query.
                The parameters returned from the database are put into a list of Article instances*/
                getListofArticles(articles, stmt);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*Returns a list of 'N' articles or a list of size = 0 if no articles are returned by a given query*/
        return articles;
    }


    /*---------------------------------------------------------------*/
    /*This method is used to access the first 'N' articles in alphabetical order by title (ascending).
    * The method takes the initial article number and a number of articles.
    * It is called from the MainContentAccess Servlet (which is accessed via AJAX)*/

    public static List<Article> getfirstNArticlePreviewsByTitle(MySQL DB, int fromArticle, int numArticles) {

        /*Dummy list of articles to be returned if article not found*/
        List<Article> articles = new ArrayList<Article>();

        try (Connection c = DB.connection()) {

            try (PreparedStatement stmt = c.prepareStatement("SELECT uploaded_articles.article_id, username, firstname, lastname, uploaded_articles.timestamp, article_title, article_body AS article_preview, COUNT(posted_comments.article_id) AS comment_count FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id LEFT JOIN posted_comments ON posted_comments.article_id = uploaded_articles.article_id GROUP BY uploaded_articles.article_id ORDER BY article_title ASC LIMIT ? OFFSET ?;")) {

                stmt.setInt(1, numArticles);
                stmt.setInt(2, fromArticle);

                /*This method cycles through a table of articles produced by and SQL query.
                The parameters returned from the database are put into a list of Article instances*/
                getListofArticles(articles, stmt);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*Returns a list of 'N' articles or a list of size = 0 if no articles are returned by a given query*/
        return articles;
    }


    /*---------------------------------------------------------------*/
    /*This method is used to access the first 'N' articles in alphabetical order by title (descending).
    * The method takes the initial article number and a number of articles.
    * It is called from the MainContentAccess Servlet (which is accessed via AJAX)*/

    public static List<Article> getfirstNArticlePreviewsByTitleDESC(MySQL DB, int fromArticle, int numArticles) {

        /*Dummy list of articles to be returned if article not found*/
        List<Article> articles = new ArrayList<Article>();

        try (Connection c = DB.connection()) {

            try (PreparedStatement stmt = c.prepareStatement("SELECT uploaded_articles.article_id, username, firstname, lastname, uploaded_articles.timestamp, article_title, article_body AS article_preview, COUNT(posted_comments.article_id) AS comment_count FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id LEFT JOIN posted_comments ON posted_comments.article_id = uploaded_articles.article_id GROUP BY uploaded_articles.article_id ORDER BY article_title DESC LIMIT ? OFFSET ?;")) {

                stmt.setInt(1, numArticles);
                stmt.setInt(2, fromArticle);

                /*This method cycles through a table of articles produced by and SQL query.
                The parameters returned from the database are put into a list of Article instances*/
                getListofArticles(articles, stmt);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*Returns a list of 'N' articles or a list of size = 0 if no articles are returned by a given query*/
        return articles;
    }


     /*---------------------------------------------------------------*/
    /*This method is used to access the first 'N' articles in alphabetical order by author username (ascending).
    * The method takes the initial article number and a number of articles.
    * It is called from the MainContentAccess Servlet (which is accessed via AJAX)*/

    public static List<Article> getfirstNArticlePreviewsSortedByAuthor(MySQL DB, int fromArticle, int numArticles) {

       /*Dummy list of articles to be returned if article not found*/
        List<Article> articles = new ArrayList<Article>();

        try (Connection c = DB.connection()) {

            try (PreparedStatement stmt = c.prepareStatement("SELECT uploaded_articles.article_id, username, firstname, lastname, uploaded_articles.timestamp, article_title, article_body AS article_preview, COUNT(posted_comments.article_id) AS comment_count FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id LEFT JOIN posted_comments ON posted_comments.article_id = uploaded_articles.article_id GROUP BY uploaded_articles.article_id ORDER BY username ASC LIMIT ? OFFSET ?;")) {

                stmt.setInt(1, numArticles);
                stmt.setInt(2, fromArticle);

                /*This method cycles through a table of articles produced by and SQL query.
                The parameters returned from the database are put into a list of Article instances*/
                getListofArticles(articles, stmt);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*Returns a list of 'N' articles or a list of size = 0 if no articles are returned by a given query*/
        return articles;
    }


    /*---------------------------------------------------------------*/
    /*This method is used to access the first 'N' articles in alphabetical order by author username (descending).
    * The method takes the initial article number and a number of articles.
    * It is called from the MainContentAccess Servlet (which is accessed via AJAX)*/

    public static List<Article> getfirstNArticlePreviewsSortedByAuthorDESC(MySQL DB, int fromArticle, int numArticles) {

        /*Dummy list of articles to be returned if article not found*/
        List<Article> articles = new ArrayList<Article>();

        try (Connection c = DB.connection()) {

            try (PreparedStatement stmt = c.prepareStatement("SELECT uploaded_articles.article_id, username, firstname, lastname, uploaded_articles.timestamp, article_title, article_body AS article_preview, COUNT(posted_comments.article_id) AS comment_count FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id LEFT JOIN posted_comments ON posted_comments.article_id = uploaded_articles.article_id GROUP BY uploaded_articles.article_id ORDER BY username DESC LIMIT ? OFFSET ?;")) {

                stmt.setInt(1, numArticles);
                stmt.setInt(2, fromArticle);

                /*This method cycles through a table of articles produced by and SQL query.
                The parameters returned from the database are put into a list of Article instances*/
                getListofArticles(articles, stmt);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*Returns a list of 'N' articles or a list of size = 0 if no articles are returned by a given query*/
        return articles;
    }


    /*---------------------------------------------------------------*/
    /*This method is used to access the first 'N' articles for a given author (by date descending)).
    * This method is used on the personal and public profile pages to present a users individual article list
    * The method takes the author_id, an initial article number and a number of articles.
    * The servlets which use this method are accessed via AJAX*/

    public static List<Article> getfirstNArticlePreviewsByAuthor(MySQL DB, int fromArticle, int numArticles, int author_id) {

        /*Dummy list of articles to be returned if article not found*/
        List<Article> articles = new ArrayList<Article>();

        try (Connection c = DB.connection()) {

            try (PreparedStatement stmt = c.prepareStatement("SELECT uploaded_articles.article_id, username, firstname, lastname, uploaded_articles.timestamp, article_title, article_body AS article_preview, COUNT(posted_comments.article_id) AS comment_count FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id LEFT JOIN posted_comments ON posted_comments.article_id = uploaded_articles.article_id WHERE user_id = ? GROUP BY uploaded_articles.article_id ORDER BY TIMESTAMP DESC LIMIT ? OFFSET ?;")) {

                stmt.setInt(1, author_id);
                stmt.setInt(2, numArticles);
                stmt.setInt(3, fromArticle);

                /*This method cycles through a table of articles produced by and SQL query.
                The parameters returned from the database are put into a list of Article instances*/
                getListofArticles(articles, stmt);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*Returns a list of 'N' articles or a list of size = 0 if no articles are returned by a given query*/
        return articles;
    }


    /*---------------------------------------------------------------*/
    /*Method to delete an article based on an article_id and an author_id (for additional security)*/

    public static int deleteAnArticle(MySQL DB, int article_id, int author_id) {

        /*Return method status
        * (1) Success
        * (2) Integrity constraint violation or no articles to delete
        * (3) SQL error
        * (4) Database connection error*/

        try (Connection c = DB.connection()) {

            /*Step (1) Delete all comments associated with the article_id*/
            try (PreparedStatement stmt = c.prepareStatement("DELETE FROM posted_comments WHERE article_id = ?")) {

                stmt.setInt(1, article_id);
                /*Execute the comment deletion*/
                int deleted = stmt.executeUpdate();
                if (deleted == 0) {
//                    No article comments to delete
                } else {
//                    Article comments deleted successfully
                }
            }

            /*Step (2) Provided the article's comments are deleted successfully, delete the article itself*/
            try (PreparedStatement stmt = c.prepareStatement("DELETE FROM uploaded_articles WHERE article_id = ? AND author_id = ?")) {

                stmt.setInt(1, article_id);
                stmt.setInt(2, author_id);

                /*Execute the article deletion*/
                int deletedArticle = stmt.executeUpdate();
                if (deletedArticle == 0) {
//                    No articles to delete - return a corresponding status
                    return 2;

                } else {
//                    Articles deleted successfully - return success status = 1
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


    /*---------------------------------------------------------------*/
    /*Extracted method for processing a table of articles returned from an SQL query into a Java List of articles*/

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
//                Articles retrieved from the database
            } else {
//                If the article can't be found in the database, return null article (in this method, the original input List<Article>
            }
        }
    }


    /*---------------------------------------------------------------*/
    /*Update and article method for use with the edit article servlet doPost method*/

    public static int updateArticle(MySQL DB, int article_id, int author_id, String article_title_update, String article_body_update, Timestamp timestamp_update) {

        /*Return an integer representative of the status
        * (1) Successful update
        * (2) Integrity constraint
        * (3) Other invalid update request
        * (4) Database connectivity issues
        */

        try (Connection c = DB.connection()) {

            /*Carry out article update based on provided article_id, article_title and article_body*/
            try (PreparedStatement stmt = c.prepareStatement("UPDATE uploaded_articles SET timestamp = ?, article_title = ?, article_body = ?  WHERE article_id = ? AND author_id = ?;")) {

                /*Set the query parameters*/
                stmt.setTimestamp(1, timestamp_update);
                stmt.setString(2, article_title_update);
                stmt.setString(3, article_body_update);
                stmt.setInt(4, article_id);
                stmt.setInt(5, author_id);

                stmt.executeUpdate();
//              If no exceptions are thrown, the article has been updated in the database and return a corresponding status code
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

   /*---------------------------------------------------------------*/
    /*Method which searches the database for article titles/author usernames based on a provided search term
    * This method takes a search term as well as an initial article number (for infinite scrolling) and a number of
    * articles.
    */

    public static List<Article> getArticlePreviewsBySearchTerm(MySQL DB, String searchTerm, int fromArticle, int numArticles) {

        /*Dummy article list to be returned if articles not found or if any exceptions are thrown*/
        List<Article> articles = new ArrayList<Article>();

        try (Connection c = DB.connection()) {

            try (PreparedStatement stmt = c.prepareStatement("SELECT uploaded_articles.article_id, username, firstname, lastname, uploaded_articles.timestamp, article_title, article_body AS article_preview, COUNT(posted_comments.article_id) AS comment_count FROM uploaded_articles LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id LEFT JOIN posted_comments ON uploaded_articles.article_id = posted_comments.article_id WHERE uploaded_articles.article_title LIKE ? OR registered_users.username LIKE ? GROUP BY uploaded_articles.article_id ORDER BY TIMESTAMP DESC LIMIT ? OFFSET ?;")) {

                stmt.setString(1, "%" + searchTerm + "%");
                stmt.setString(2, "%" + searchTerm + "%");
                stmt.setInt(3, numArticles);
                stmt.setInt(4, fromArticle);

                /*This method cycles through a table of articles produced by and SQL query.
                The parameters returned from the database are put into a list of Article instances*/
                getListofArticles(articles, stmt);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*Returns a list of 'N' articles or a list of size = 0 if no articles are returned by a given query*/
        return articles;
    }


    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/
}
