package login.system.database_testing;

import com.sun.org.apache.xpath.internal.SourceTree;
import login.system.dao.ArticleDAO;
import login.system.dao.CommentDAO;
import login.system.dao.MultimediaDAO;
import login.system.dao.UserDAO;
import login.system.db.MySQL;
import login.system.passwords.Passwords;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by jlee512 on 1/06/2017.
 */
public class Test_DB_Setup {

    private static MySQL DB = new MySQL();

    public static void main(String[] args) throws ParseException {


        /*--------------ADD USERS-----------------*/
        System.out.println("Adding test user Catherine");
        String username = "catherine";
        String passwordEntry = "test";
        String email = "cbla080@aucklanduni.ac.nz";
        String phoneEntry = "0270987654";
        String occupationEntry = "student and closed caption technician";
        String cityEntry = "Auckland";
        String profile_description = "My landlord is letting us have a cat!";
        String profile_picture = "/Multimedia/kokako.jpg"; //Standard profile picture

        int iterations = Passwords.getNextNumIterations();
        byte[] salt = Passwords.getNextSalt();
        byte[] hash = Passwords.hash(passwordEntry.toCharArray(), salt, iterations);
        UserDAO.addUserToDB(DB, username, iterations, salt, hash, email, phoneEntry, occupationEntry, cityEntry, profile_description, profile_picture);

        System.out.println("Adding test user Yuri");
        username = "yuri";
        passwordEntry = "test";
        email = "ycow194@aucklanduni.ac.nz";
        phoneEntry = "0211234567";
        occupationEntry = "student";
        cityEntry = "Auckland";
        profile_description = "Hunting all dungeons across the land and cycling on the side";
        profile_picture = "/Multimedia/dungeonHunter07.jpg"; //Non standard profile picture

        iterations = Passwords.getNextNumIterations();
        salt = Passwords.getNextSalt();
        hash = Passwords.hash(passwordEntry.toCharArray(), salt, iterations);
        UserDAO.addUserToDB(DB, username, iterations, salt, hash, email, phoneEntry, occupationEntry, cityEntry, profile_description, profile_picture);

        System.out.println("Adding test user Julian");
        username = "julian";
        passwordEntry = "test";
        email = "jlee512@aucklanduni.ac.nz";
        phoneEntry = "0225671234";
        occupationEntry = "student";
        cityEntry = "Auckland";
        profile_description = "Writing fake social media profiles since ages ago";
        profile_picture = "/Multimedia/kokako.jpg"; //Standard profile picture

        iterations = Passwords.getNextNumIterations();
        salt = Passwords.getNextSalt();
        hash = Passwords.hash(passwordEntry.toCharArray(), salt, iterations);
        UserDAO.addUserToDB(DB, username, iterations, salt, hash, email, phoneEntry, occupationEntry, cityEntry, profile_description, profile_picture);

        /*------------ADD/ACCESS ARTICLES----------*/
        System.out.println("Adding articles to the database: 1");
        int author_id = 1;
        String article_title = "You're a lizard Harry!";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date utilDate = dateFormat.parse("2017-05-31");
        Date date = new Date(utilDate.getTime());

        String article_body = "Curabitur vitae justo ac nunc molestie consectetur ac ut arcu. Nam efficitur fermentum congue. Pellentesque ac fringilla est, convallis condimentum neque. Pellentesque eu tortor at ligula fringilla vulputate quis ac ante.";

        System.out.println("Adding articles to the database: 2");
        author_id = 2;
        article_title = "Who is Dungeon Hunter 8?";

        utilDate = dateFormat.parse("2016-11-26");
        date = new Date(utilDate.getTime());

        article_body = "Curabitur pharetra, felis at tristique posuere, nibh velit bibendum massa, ac facilisis tortor massa et ligula. Duis scelerisque ex vel dui aliquet commodo.";

        ArticleDAO.addArticleToDB(DB, author_id, article_title, date, article_body);

        System.out.println("Adding articles to the database: 3");
        author_id = 1;
        article_title = "Calling all Lizardsssssss - Party like it's 1998";

        utilDate = dateFormat.parse("1998-04-12");
        date = new Date(utilDate.getTime());

        article_body = "Medaka ocean sunfish sturgeon pomfret stonecat brook trout ray central mudminnow righteye flounder. Zebra trout bala shark candiru louvar lionfish fathead sculpin pikeperch southern smelt prickleback Celebes rainbowfish.";

        ArticleDAO.addArticleToDB(DB, author_id, article_title, date, article_body);

        System.out.println("Adding articles to the database: 4");
        author_id = 2;
        article_title = "Hunting Dungeon's for Dummies";

        utilDate = dateFormat.parse("2015-07-19");
        date = new Date(utilDate.getTime());

        article_body = "Nori grape silver beet broccoli kombu beet greens fava bean potato quandong celery. Bunya nuts black-eyed pea prairie turnip leek lentil turnip greens parsnip. Sea lettuce lettuce water chestnut eggplant winter purslane fennel azuki bean earthnut pea sierra leone bologi leek soko chicory celtuce parsley.";

        ArticleDAO.addArticleToDB(DB, author_id, article_title, date, article_body);

        System.out.println("Adding articles to the database: 5");
        author_id = 1;
        article_title = "Computer Programming: A Lizard's Perspective";

        utilDate = dateFormat.parse("2002-09-01");
        date = new Date(utilDate.getTime());

        article_body = "Airedale melted cheese stilton. Airedale gouda macaroni cheese airedale cheese strings pepper jack cow macaroni cheese. Fromage babybel feta pepper jack feta everyone loves cauliflower cheese cheese on toast.";

        ArticleDAO.addArticleToDB(DB, author_id, article_title, date, article_body);

        System.out.println("Adding articles to the database: 6");
        author_id = 3;
        article_title = "How to Paint Hyper Realistic Tunnels";

        utilDate = dateFormat.parse("2016-02-17");
        date = new Date(utilDate.getTime());

        article_body = "Obi-Wan is here. The Force is with him. I find your lack of faith disturbing. Red Five standing by. A tremor in the Force. The last time I felt it was in the presence of my old master.";

        ArticleDAO.addArticleToDB(DB, author_id, article_title, date, article_body);

        /*------------ADD/ACCESS COMMENTS----------*/
        CommentDAO.addComment

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
