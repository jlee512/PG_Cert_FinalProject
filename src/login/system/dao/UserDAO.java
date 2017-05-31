package login.system.dao;

import login.system.db.MySQL;
import login.system.passwords.Passwords;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;

/**
 * Created by jlee512 on 29/05/2017.
 */
public class UserDAO {

    /*Add a new user to the Database*/
    public static int addUserToDB(MySQL DB, String userName, String nickName, int iterations, byte[] salt, byte[] hash, String email) {

        /*Return method status
        * (1) Success
        * (2) Integrity constraint violation (duplicate user)
        * (3) SQL error
        * (4) Database connection error*/
    /*------------------------------------------------------------*/
    /*convert username to lowercase to avoid duplication*/
    String username = userName.toLowerCase();

        /*Test addition to database*/
        User tempUser = new User(username, nickName, hash, salt, iterations, email);

        try (Connection c = DB.connection()) {
            /*Connect to the database and add user*/
            try (PreparedStatement stmt = c.prepareStatement("INSERT INTO registered_users (username, nickname, hash, salt, iterations, email) VALUES (?, ?, ?, ?, ?, ?)")) {
                /*Database input, method of transferring char[] to blob to be confirmed with Andrew*/
                stmt.setString(1, tempUser.getUsername());
                stmt.setString(2, tempUser.getNickname());
                stmt.setBlob(3, new SerialBlob(hash));
                stmt.setBlob(4, new SerialBlob(salt));
                stmt.setInt(5, tempUser.getIterations());
                stmt.setString(6, tempUser.getEmail());

                /*Execute the prepared statement*/
                stmt.executeUpdate();
                System.out.println("User added to the database");
                return 1;

            }
        } catch (SQLIntegrityConstraintViolationException e){
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

    public static int updateUserPassword(MySQL DB, User user, String passwordChange){
        /*Convert username to lookup to lowercase*/
        String usernameToLookup = user.getUsername().toLowerCase();

        /*Get new salt and iterations number for changed password and convert to Blobs for input into the database*/
        byte[] salt = Passwords.getNextSalt();
        int iterations = Passwords.getNextNumIterations();

        /*Hash the changed password String and convert */
        byte[] hash = Passwords.hash(passwordChange.toCharArray(), salt, iterations);

        try (Connection conn = DB.connection()) {
            try (PreparedStatement stmt = conn.prepareStatement("UPDATE registered_users SET hash = ?, salt = ?, iterations = ? WHERE username = ?")){
                /**/
                stmt.setBlob(1, new SerialBlob(hash));
                stmt.setBlob(2, new SerialBlob(salt));
                stmt.setInt(3, iterations);
                stmt.setString(4, usernameToLookup);

                stmt.executeUpdate();
                System.out.println("User password updated");
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 2;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return 3;
        }

    }


    /*Get a user's details (hashed password, salt and number of iterations) for use with login validation*/
    public static User getUser(MySQL DB, String usernameToLookup) {
    /*----------------------------------------------------*/
        /*Convert username to lookup to lowercase*/
        usernameToLookup = usernameToLookup.toLowerCase();

        /*Method development to verify user password*/
        User user = new User(null, null, null, null, -1, null);

        try (Connection c = DB.connection()) {
            try (PreparedStatement stmt = c.prepareStatement("SELECT * FROM registered_users WHERE username = ?")) {
                /**/
                stmt.setString(1, usernameToLookup);

                try (ResultSet r = stmt.executeQuery()) {
                    if (r.next()) {
                     /*If there is a next result, the user exists in the database*/

                     /*Get user id, username and nickname*/
                        int userIdLookup = r.getInt("user_id");
                        String usernameLookup = r.getString("username");
                        String nicknameLookup = r.getString("nickname");

                     /*Get hash blob and convert to byte array*/
                        SerialBlob hashLookupBlob = new SerialBlob(r.getBlob("hash"));

                        byte[] hashLookup = hashLookupBlob.getBytes(1, (int) hashLookupBlob.length());

                     /*Get salt blob and convert to byte array*/
                        SerialBlob saltLookupBlob = new SerialBlob(r.getBlob("salt"));
                        byte[] saltLookup = saltLookupBlob.getBytes(1, (int) saltLookupBlob.length());

                     /*Get number of iterations*/
                        int iterationsLookup = r.getInt("iterations");

                     /*Get user email*/
                        String email = r.getString("email");

                        user.setUserParameters(userIdLookup, usernameLookup, nicknameLookup, hashLookup, saltLookup, iterationsLookup, email);

                        System.out.println("User retrieved from database");
                    } else {
                    /*If the user can't be found in the database, return null user*/
                        System.out.println("User could not be found in the database");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*----------------------------------------------------*/
        return user;
    }

}
