package login.system.database_testing;

import com.sun.org.apache.xpath.internal.SourceTree;
import login.system.dao.MultimediaDAO;
import login.system.dao.UserDAO;
import login.system.db.MySQL;
import login.system.passwords.Passwords;

/**
 * Created by jlee512 on 1/06/2017.
 */
public class Test_DB_Setup {

    private static MySQL DB = new MySQL();

    public static void main(String[] args) {


        /*--------------ADD USERS-----------------*/
        System.out.println("Adding test user Catherine");
        String username = "catherine";
        String nickname = "lizard_party666";
        String passwordEntry = "test";
        String email = "cbla080@aucklanduni.ac.nz";

        int iterations = Passwords.getNextNumIterations();
        byte[] salt = Passwords.getNextSalt();
        byte[] hash = Passwords.hash(passwordEntry.toCharArray(), salt, iterations);
        UserDAO.addUserToDB(DB, username, nickname, iterations, salt, hash, email);

        System.out.println("Adding test user Yuri");
        username = "yuri";
        nickname = "xXDunge0n_Hunter7Xx";
        passwordEntry = "test";
        email = "ycow194@aucklanduni.ac.nz";

        iterations = Passwords.getNextNumIterations();
        salt = Passwords.getNextSalt();
        hash = Passwords.hash(passwordEntry.toCharArray(), salt, iterations);
        UserDAO.addUserToDB(DB, username, nickname, iterations, salt, hash, email);

        System.out.println("Adding test user Julian");
        username = "julian";
        nickname = "545d0lphin_rid3r545";
        passwordEntry = "test";
        email = "jlee512@aucklanduni.ac.nz";

        iterations = Passwords.getNextNumIterations();
        salt = Passwords.getNextSalt();
        hash = Passwords.hash(passwordEntry.toCharArray(), salt, iterations);
        UserDAO.addUserToDB(DB, username, nickname, iterations, salt, hash, email);

        /*------------ADD/ACCESS ARTICLES----------*/


        /*------------ADD/ACCESS COMMENTS----------*/


        /*------------ADD/ACCESS MULTIMEDIA--------*/
        String genericFilePath = "/Multimedia/";

        System.out.println("Adding multimedia to database: Fushimi Inari");
        int article_id = 1;
        String file_type = ".jpeg";
        String userInputFilename = "fushimi_inari.jpeg";
        String filepath = genericFilePath + userInputFilename;
        String multimedia_title = "Fushimi Inari";

        MultimediaDAO.addMultimediaToDB(DB, article_id, file_type, filepath,multimedia_title);

        System.out.println("Adding multimedia to database: Anothers Arms (sound)");
        article_id = 2;
        file_type = ".mp3";
        userInputFilename = "anothers_arms.mp3";
        filepath = genericFilePath + userInputFilename;
        multimedia_title = "Anothers Arms - Coldplay";

        MultimediaDAO.addMultimediaToDB(DB, article_id, file_type, filepath,multimedia_title);

        System.out.println("Adding multimedia to database: Port (video - mp4)");
        article_id = 4;
        file_type = ".mp4";
        userInputFilename = "anothers_arms.mp4";
        filepath = genericFilePath + userInputFilename;
        multimedia_title = "Port";

        MultimediaDAO.addMultimediaToDB(DB, article_id, file_type, filepath,multimedia_title);

        System.out.println("Adding multimedia to database: South Island Robin");
        article_id = 4;
        file_type = ".png";
        userInputFilename = "si_robin.png";
        filepath = genericFilePath + userInputFilename;
        multimedia_title = "South Island Robin";

        MultimediaDAO.addMultimediaToDB(DB, article_id, file_type, filepath,multimedia_title);
    }

}
