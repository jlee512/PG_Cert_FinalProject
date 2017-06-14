package login.system.dao;

import login.system.db.MySQL;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MultimediaDAO {

/*
Add a new user to the Database
*/

    public static List<Multimedia> getAllMultimedia(MySQL DB) {


        /*Addition to database*/
        List<Multimedia> multimedia = new ArrayList<Multimedia>();

        /*Connect to the database and add user*/
        try (Connection c = DB.connection()) {


            try (PreparedStatement stmt = c.prepareStatement("SELECT * FROM posted_multimedia")) {

                        getListOfMultimedia(multimedia, stmt);

            }
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*------------------------------------------------------------*/
        System.out.println("All Multimedia has been selected");

        return multimedia;
    }


    private static void getListOfMultimedia(List<Multimedia> multimedia, PreparedStatement stmt) throws SQLException {
        try (ResultSet r = stmt.executeQuery()) {
            while (r.next()) {
             /*If there is a next result, the user exists in the database*/


                /*Get additional user details*/
                int multimedia_id = r.getInt("multimedia_id");
                int article_id = r.getInt("article_id");
                String file_type = r.getString("file_type");
                String file_path = r.getString("file_path");
                String multimedia_title = r.getString("multimedia_title");

                System.out.println("singlemultimedia added");
                Multimedia singleMultimedia = new Multimedia(multimedia_id, article_id, file_type, file_path, multimedia_title);

                multimedia.add(singleMultimedia);


                System.out.println("Multimedia retrieved from database");
            }

        }
    }


    public static int addMultimediaToDB(MySQL DB, int article_id, String file_type, String file_path, String multimedia_title) {

    /*Return method status
            * (1) Success
            * (2) Integrity constraint violation (duplicate filepath)
            * (3) SQL error
            * (4) Database connection error

    ------------------------------------------------------------*/

        /*Addition to database*/
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
                System.out.println("Multimedia added to the database");
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
        /*------------------------------------------------------------*/

    }

    public static void getFirstNMultimediaByArticleDate(MySQL DB, int fromMultimedia, int numMultimedia) {

           /*Dummy multimedia to be returned if none found*/
        List<Multimedia> multimedia = new ArrayList<Multimedia>();

        try (Connection c = DB.connection()) {

            try (PreparedStatement stmt = c.prepareStatement("")) {



            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}