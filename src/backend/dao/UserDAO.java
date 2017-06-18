package backend.dao;

import backend.db.MySQL;
import backend.passwords.Passwords;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;

/**
 * Created by jlee512 on 29/05/2017.
 */

/**
 * The UserDAO class links the backend representation of an user object with the database.
 *
 * The contained methods are called from the relevant servlets.
 *
 */

public class UserDAO {

    /*Add a new user to the Database*/

    public static int addUserToDB(MySQL DB, String userName, int iterations, byte[] salt, byte[] hash, String email, String phone, String occupation, String city, String profile_description, String profile_picture, String firstname, String lastname) {

        /*Return method status
        * (user_id) Success
        * (-2) Integrity constraint violation (duplicate user)
        * (-3) SQL error
        * (-4) Database connection error*/
    /*------------------------------------------------------------*/
    /*convert username to lowercase to avoid duplication*/
        String username = userName.toLowerCase();

        /*Addition to database*/
        User tempUser = new User(username, hash, salt, iterations, email, phone, occupation, city, profile_description, profile_picture, firstname, lastname);

        try (Connection c = DB.connection()) {
            /*Connect to the database and add user*/
            try (PreparedStatement stmt = c.prepareStatement("INSERT INTO registered_users (username, hash, salt, iterations, email, phone, occupation, city, profile_description, profile_picture, firstname, lastname) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                stmt.setString(1, tempUser.getUsername());
                stmt.setBlob(2, new SerialBlob(hash));
                stmt.setBlob(3, new SerialBlob(salt));
                stmt.setInt(4, tempUser.getIterations());
                stmt.setString(5, tempUser.getEmail());
                stmt.setString(6, tempUser.getPhone());
                stmt.setString(7, tempUser.getOccupation());
                stmt.setString(8, tempUser.getCity());
                stmt.setString(9, tempUser.getProfile_description());
                stmt.setString(10, tempUser.getProfile_picture());
                stmt.setString(11, tempUser.getFirstname());
                stmt.setString(12, tempUser.getLastname());

                /*Execute the prepared statement*/
                stmt.executeUpdate();
//                User added to the database

                /*Get the user ID for use within the session object*/
                try(PreparedStatement stmt1 = c.prepareStatement("SELECT * FROM registered_users WHERE username = ?")) {

                    stmt1.setString(1, tempUser.getUsername());

                    try (ResultSet r = stmt1.executeQuery()) {
                        if (r.next()) {
                            int user_id = r.getInt("user_id");
                            /*If the user has been found, return the successful status which is the user_id (a positive integer)*/
                            return user_id;
                        } else {
                            throw new SQLException();
                        }
                    }
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            return -2;
        } catch (SQLException e) {
            e.printStackTrace();
            return -3;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return -4;
        }
    }

    /*---------------------------------------------------------------*/
    /*Update a user password in the database (core of the change password functionality)*/

    public static int updateUserPassword(MySQL DB, User user, String passwordChange) {
        /*Convert username to lookup to lowercase*/
        String usernameToLookup = user.getUsername().toLowerCase();

        /*Get new salt and iterations number for changed password and convert to Blobs for input into the database*/
        byte[] salt = Passwords.getNextSalt();
        int iterations = Passwords.getNextNumIterations();

        /*Hash the changed password String and convert */
        byte[] hash = Passwords.hash(passwordChange.toCharArray(), salt, iterations);

        try (Connection conn = DB.connection()) {
            try (PreparedStatement stmt = conn.prepareStatement("UPDATE registered_users SET hash = ?, salt = ?, iterations = ? WHERE username = ?")) {
                /**/
                stmt.setBlob(1, new SerialBlob(hash));
                stmt.setBlob(2, new SerialBlob(salt));
                stmt.setInt(3, iterations);
                stmt.setString(4, usernameToLookup);

                stmt.executeUpdate();
//              If no exceptions are thrown, the user password has been updated and return the successful status = 1
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

    /*---------------------------------------------------------------*/
    /*Get a user's details based on their username (hashed password, salt and number of iterations) for use with login validation*/
    public static User getUser(MySQL DB, String usernameToLookup) {
    /*----------------------------------------------------*/
        /*Convert username to lookup to lowercase*/
        usernameToLookup = usernameToLookup.toLowerCase();

        /*Dummy user to be returned if user not found*/
        User user = new User(null, null, null, -1, null, null, null, null, null, null, null, null);

        try (Connection c = DB.connection()) {
            try (PreparedStatement stmt = c.prepareStatement("SELECT * FROM registered_users WHERE username = ?")) {

                stmt.setString(1, usernameToLookup);

                executeUserSQLSelectStatement(user, stmt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*If no exceptions are thrown, the selected user will be returned (otherwise the dummy user object will be returned which is to be recognised by the calling methods)*/
        return user;
    }

/*---------------------------------------------------------------*/
    /*Get user by email for Google sign-in*/

    public static User getUserByEmail(MySQL DB, String email) {

        /*Method developed to verify user password*/
        User user = new User(null, null, null, -1, null, null, null, null, null, null, null, null);

        try (Connection c = DB.connection()) {
            try (PreparedStatement stmt = c.prepareStatement("SELECT * FROM registered_users WHERE email = ?")) {
                /**/
                stmt.setString(1, email);

                executeUserSQLSelectStatement(user, stmt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*If no exceptions are thrown, the selected user will be returned (otherwise the dummy user object will be returned which is to be recognised by the calling methods)*/
        return user;
    }


    /*---------------------------------------------------------------*/
    /*Extracted method for processing a user MySQL query and update Java User object*/

    private static void executeUserSQLSelectStatement(User user, PreparedStatement stmt) throws SQLException {
        try (ResultSet r = stmt.executeQuery()) {
            if (r.next()) {
             /*If there is a next result, the user exists in the database*/

             /*Get user id, username and nickname*/
                int userIdLookup = r.getInt("user_id");
                String usernameLookup = r.getString("username");

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

                /*Get additional user details*/
                String phone = r.getString("phone");
                String occupation = r.getString("occupation");
                String city = r.getString("city");
                String profile_description = r.getString("profile_description");
                String profile_picture = r.getString("profile_picture");
                String firstnameLookup = r.getString("firstname");
                String lastnameLookup = r.getString("lastname");

                user.setUserParameters(userIdLookup, usernameLookup, hashLookup, saltLookup, iterationsLookup, email, phone, occupation, city, profile_description, profile_picture, firstnameLookup, lastnameLookup);

//                User retrieved from database
            } else {
            /*If the user can't be found in the database, return null user*/
//                User could not be found in the database
            }
        }
    }

    /*---------------------------------------------------------------*/
    /*A method to update a user's details*/
    public static int updateUserDetails(MySQL DB, String userName, String email, String phone, String occupation, String city, String profile_description, String firstname, String lastname, String original_username) {

        /*Return method status
        * (1) Success
        * (2) Integrity constraint violation (duplicate user)
        * (3) SQL error
        * (4) Database connection error*/
    /*------------------------------------------------------------*/
    /*convert username to lowercase to avoid duplication*/
        String username = userName.toLowerCase();

        /*Addition to database*/
        User tempUser = new User(username, email, phone, occupation, city, profile_description, firstname, lastname);

        try (Connection c = DB.connection()) {
            /**/
            try(PreparedStatement stmt = c.prepareStatement("SELECT user_id FROM registered_users WHERE username = ?")){

                 stmt.setString(1, original_username);

                try (ResultSet resultSet = stmt.executeQuery()) {

                    /*Get the user_id*/
                    if(resultSet.next()) {
                        int user_id = resultSet.getInt(1);
                        tempUser.setUser_id(user_id);
                    } else {
//                        User could not be located
                    }
                }
            }


            /*Connect to the database and add user*/
            try (PreparedStatement stmt1 = c.prepareStatement("UPDATE registered_users SET username = ?, email = ?, phone = ?, occupation = ?, city = ?, profile_description = ?, firstname = ?, lastname = ? WHERE user_id = ?")) {

                stmt1.setString(1, tempUser.getUsername());
                stmt1.setString(2, tempUser.getEmail());
                stmt1.setString(3, tempUser.getPhone());
                stmt1.setString(4, tempUser.getOccupation());
                stmt1.setString(5, tempUser.getCity());
                stmt1.setString(6, tempUser.getProfile_description());
                stmt1.setString(7, tempUser.getFirstname());
                stmt1.setString(8, tempUser.getLastname());
                stmt1.setInt(9, tempUser.getUser_id());

                /*Execute the prepared statement*/
                stmt1.executeUpdate();
                /*If no exceptions are thrown, return the successful status = 1*/
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
    /*Method to delete a user account*/

    public static int deleteUserAccount(MySQL DB, String username) {

        try (Connection c = DB.connection()) {
            /*Connect to the database and add user*/
            try (PreparedStatement stmt = c.prepareStatement("DELETE FROM registered_users WHERE username = ?")) {

                stmt.setString(1, username);

                /*Execute the prepared statement (cascading update/delete functionality included to also remove any comments/articles written by the user being deleted*/
                stmt.executeUpdate();
                /*If no exceptions are thrown, return the successfuly status = 1*/
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
    /*Method to update a user's profile picture*/

    public static int updateProfilePicture(MySQL DB, String profile_picture, String username) {

        try (Connection c = DB.connection()) {
            /*Connect to the database and add user*/
            try (PreparedStatement stmt = c.prepareStatement("UPDATE registered_users SET profile_picture = ? WHERE  username = ?")) {

                stmt.setString(1, profile_picture);
                stmt.setString(2, username);

                /*Execute the prepared statement*/
                stmt.executeUpdate();
                //User picture successfully updated
                /*If no exceptions are thrown, return the successful status = 1*/
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

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
