import backend.dao.User;
import backend.dao.UserDAO;
import backend.db.MySQL;
import backend.passwords.Passwords;

/**
 * Created by Julian on 02-Jun-17.
 */

/**
 * This class is used to generate a random user for testing purposes as well as a known user
 */

public class UserGenerator {

    static MySQL DB = new MySQL();

    public static User generateRandomUser() {

        //This test will generate a random username most of the time - interrogate if failure and rule out duplicate username
        int userRandNum = (int) (Math.random() * 10000 + 100);
        String usernameInputTest = "unitTest" + userRandNum;

        /*Generate password parameters*/
        String password = "unitTest";
        byte[] salt = Passwords.getNextSalt();
        int iterations = Passwords.getNextNumIterations();
        byte[] hash = Passwords.hash(password.toCharArray(), salt, iterations);

        /*Add additional fields*/
        String nickname = usernameInputTest;
        String email = usernameInputTest + "@gmail.com";
        String phone = "091234567";
        String occupation = "Rocket Scientist";
        String city = "Seoul";
        String profile_description = "I am a unit test that should be added to the database and be able to login!";
        String profile_picture = "/Multimedia/kokako.jpg";
        String firstname = "Albert";
        String lastname = "Einstein";

        return new User(usernameInputTest, hash, salt, iterations, email, phone, occupation, city, profile_description, profile_picture, firstname, lastname);

    }

    public static User getKnownUser() {

        User knownUser = UserDAO.getUser(DB, "julian");

        return knownUser;

    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
