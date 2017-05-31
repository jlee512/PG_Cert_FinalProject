package login.system.database_testing;

import login.system.dao.User;
import login.system.dao.UserDAO;
import login.system.db.MySQL;
import login.system.passwords.Passwords;

/**
 * Created by jlee512 on 29/05/2017.
 */
public class DB_testing {

    private static MySQL DB = new MySQL();

    public static void main(String[] args) {

        /*Test user registration and login prior to developing UserDAO*/
        System.out.println(testUserRegistrationAndLogin());

        /*Access user for further testing*/
        User user = UserDAO.getUser(DB, "ycow194");

        /*Test user password update before developing change password page*/
        UserDAO.updateUserPassword(DB, user, "test1234");

        /*Test user login with updated password*/
        user = UserDAO.getUser(DB, "ycow194");

        if (Passwords.isExpectedPassword("test1234".toCharArray(), user.getSalt(), user.getIterations(), user.getHash())){
            System.out.println("User details verified, successful login!");
        } else {
            System.out.println("Login unsuccessful");
        }

    }

    private static boolean testUserRegistrationAndLogin() {
        /*Setup MySQL database*/
        MySQL DB = new MySQL();
        /*Test RegistrationAttempt of Username and Password to setup SQL data entry*/
        String userName = "ycow194";
        String nickName = "test nickname";
        String email = "ycow194@aucklanduni.ac.nz";
        String passwordInput = "test123";

        /*Convert password to char array and hash*/
        char[] passwordArray = passwordInput.toCharArray();

        int iterations = Passwords.getNextNumIterations();
        byte[] salt = Passwords.getNextSalt();
        byte[] hash = Passwords.hash(passwordArray, salt, iterations);
        UserDAO.addUserToDB(DB, userName, nickName, iterations, salt, hash, email);

        /*Test User Login*/
        String testUsernameLogin = "ycow194";
        String testPWLogin = "test123";
        User user = UserDAO.getUser(DB, testUsernameLogin);

        if (Passwords.isExpectedPassword(testPWLogin.toCharArray(), user.getSalt(), user.getIterations(), user.getHash())){
            System.out.println("User details verified, successful login!");
        }

        return (Passwords.isExpectedPassword(testPWLogin.toCharArray(), user.getSalt(), user.getIterations(), user.getHash()));
    }

}
