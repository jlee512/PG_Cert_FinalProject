package backend.dao;

import backend.db.MySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MultimediaDAO {

    /**
     * The MultimediaDAO class links the backend representation of a multimedia object with the database.
     *
     * The contained methods are called from the relevant servlets.
     *
     */

     /*Add a new multimedia file to the Database*/

    public static int addMultimediaToDB(MySQL DB, int article_id, String file_type, String file_path, String multimedia_title) {

    /*Return method status
            * (1) Success
            * (2) Integrity constraint violation (duplicate filepath)
            * (3) SQL error
            * (4) Database connection error

    ------------------------------------------------------------*/

        /*Create a multimedia instance to be used when passing values to the database*/
        Multimedia tempMultimedia = new Multimedia(file_type, file_path, multimedia_title);

        /*Connect to the database and add user*/
        try (Connection c = DB.connection()) {


            try (PreparedStatement stmt = c.prepareStatement("INSERT INTO posted_multimedia (article_id, file_type, file_path, multimedia_title) VALUES (?, ?, ?, ?)")) {
                /*Populate prepared statement*/
                stmt.setInt(1, article_id);
                stmt.setString(2, file_type);
                stmt.setString(3, file_path);
                stmt.setString(4, multimedia_title);

                /*Execute the prepared statement*/
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
    /*This method is used to access the first 'N' multimedia files for all authors in date order.
    * The method takes the initial multimedia number and a number of multimedia to get.
    * It is called from the MultimediaContent Servlet (which is accessed via AJAX) and is used to build the full gallery feature*/


    public static List<Multimedia> getFirstNMultimediaByArticleDate(MySQL DB, int fromMultimedia, int numMultimedia) {

           /*Dummy multimedia to be returned if none found*/
        List<Multimedia> multimedia = new ArrayList<Multimedia>();

        try (Connection c = DB.connection()) {

            try (PreparedStatement stmt = c.prepareStatement("SELECT registered_users.username, posted_multimedia.multimedia_id, posted_multimedia.article_id, posted_multimedia.file_type, posted_multimedia.file_path, posted_multimedia.multimedia_title FROM posted_multimedia " +
                    "LEFT JOIN uploaded_articles ON posted_multimedia.article_id = uploaded_articles.article_id " +
                    "LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id ORDER BY uploaded_articles.timestamp DESC LIMIT ? OFFSET ?;")) {

                stmt.setInt(1, numMultimedia);
                stmt.setInt(2, fromMultimedia);

                /*This method cycles through a table of multimedia produced by an SQL query.
                The parameters returned from the database are put into a list of multimedia instances*/
                constructListFromMultimediaQuery(multimedia, stmt);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*Returns a list of 'N' multimedia or a list of size = 0 if no multimedia are returned by a given query*/
        return multimedia;
    }


     /*---------------------------------------------------------------*/
    /*This method is used to access the first 'N' articles for a given 'poster'/user in date order.
    * The method takes the 'poster'/user id, an initial multimedia number and a number of multimedia.
    * It is called from the UserMultimedia Servlet (which is accessed via AJAX) and used to build the user gallery feature*/

    public static List<Multimedia> getFirstNMultimediaForPosterByDate(MySQL DB, int fromMultimedia, int numMultimedia, int poster_id) {

           /*Dummy multimedia to be returned if none found*/
        List<Multimedia> multimedia = new ArrayList<Multimedia>();

        try (Connection c = DB.connection()) {

            try (PreparedStatement stmt = c.prepareStatement("SELECT registered_users.username, posted_multimedia.multimedia_id, posted_multimedia.article_id, posted_multimedia.file_type, posted_multimedia.file_path, posted_multimedia.multimedia_title FROM posted_multimedia\n" +
                    "  LEFT JOIN uploaded_articles ON posted_multimedia.article_id = uploaded_articles.article_id\n" +
                    "  LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id WHERE author_id = ? ORDER BY uploaded_articles.timestamp DESC LIMIT ? OFFSET ?")) {

                stmt.setInt(1, poster_id);
                stmt.setInt(2, numMultimedia);
                stmt.setInt(3, fromMultimedia);

                /*This method cycles through a table of multimedia produced by an SQL query.
                The parameters returned from the database are put into a list of multimedia instances*/
                constructListFromMultimediaQuery(multimedia, stmt);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*Returns a list of 'N' multimedia or a list of size = 0 if no multimedia are returned by a given query*/
        return multimedia;
    }


    /*---------------------------------------------------------------*/
    /*This method is used to access all of the multimedia for a given article.
    * The method takes the article id
    * It is called from the ArticleMultimedia Servlet (which is accessed via AJAX)*/

    public static List<Multimedia> getAllMultimediaForArticle(MySQL DB, int article_id) {

           /*Dummy multimedia to be returned if none found*/
        List<Multimedia> multimedia = new ArrayList<Multimedia>();

        try (Connection c = DB.connection()) {

            try (PreparedStatement stmt = c.prepareStatement("SELECT registered_users.username, posted_multimedia.multimedia_id, posted_multimedia.article_id, posted_multimedia.file_type, posted_multimedia.file_path, posted_multimedia.multimedia_title FROM posted_multimedia\n" +
                    "  LEFT JOIN uploaded_articles ON posted_multimedia.article_id = uploaded_articles.article_id\n" +
                    "  LEFT JOIN registered_users ON uploaded_articles.author_id = registered_users.user_id WHERE posted_multimedia.article_id = ? ORDER BY uploaded_articles.timestamp DESC")) {

                stmt.setInt(1, article_id);

                /*This method cycles through a table of multimedia produced by an SQL query.
                The parameters returned from the database are put into a list of multimedia instances*/
                constructListFromMultimediaQuery(multimedia, stmt);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*Returns a list of 'N' multimedia or a list of size = 0 if no multimedia are returned by a given query*/
        return multimedia;
    }

        /*---------------------------------------------------------------*/
    /*This is the method used in a number of the other DAO methods to process a MySQL results table of Multimedia and assign these to Multimedia object instances*/
    private static void constructListFromMultimediaQuery(List<Multimedia> multimedia, PreparedStatement stmt) throws SQLException {
        try (ResultSet r = stmt.executeQuery()) {

            while (r.next()) {
                /*If there is a next result, there are multimedia in the database to be accessed still, get the available parameters an store in the multimedia object*/

                String usernameLookup = r.getString("username");
                int multimedia_idLookup = r.getInt("multimedia_id");
                int article_idLookup = r.getInt("article_id");
                String file_typeLookup = r.getString("file_type");
                String file_pathLookup = r.getString("file_path");
                String multimedia_titleLookup = r.getString("multimedia_title");

                Multimedia multimedia_temp = new Multimedia(multimedia_idLookup, article_idLookup, file_typeLookup, file_pathLookup, multimedia_titleLookup);

                multimedia_temp.setUsername(usernameLookup);
                multimedia.add(multimedia_temp);

            }

            /*Summary print-out to the console*/
            if (multimedia.size() > 0) {
//                Multimedia retrieved from the database
            } else {
//                Multimedia could not be found in the database
            }

        }
    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}