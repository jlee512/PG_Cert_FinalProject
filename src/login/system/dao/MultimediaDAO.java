package login.system.dao;

import login.system.db.MySQL;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class MultimediaDAO {

/*
Add a new user to the Database
*/

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

}