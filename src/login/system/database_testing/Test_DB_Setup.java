package login.system.database_testing;

import com.sun.org.apache.xpath.internal.SourceTree;
import login.system.dao.ArticleDAO;
import login.system.dao.CommentDAO;
import login.system.dao.MultimediaDAO;
import login.system.dao.UserDAO;
import login.system.db.MySQL;
import login.system.passwords.Passwords;

import java.sql.Date;
import java.sql.Timestamp;
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
        String profile_picture = "Multimedia/kokako.jpg"; //Standard profile picture
        String firstname = "Catherine";
        String lastname = "de Chalain";

        int iterations = Passwords.getNextNumIterations();
        byte[] salt = Passwords.getNextSalt();
        byte[] hash = Passwords.hash(passwordEntry.toCharArray(), salt, iterations);
        UserDAO.addUserToDB(DB, username, iterations, salt, hash, email, phoneEntry, occupationEntry, cityEntry, profile_description, profile_picture, firstname, lastname);

        System.out.println("Adding test user Yuri");
        username = "god";
        passwordEntry = "1";
        email = "ycow194@aucklanduni.ac.nz";
        phoneEntry = "0211234567";
        occupationEntry = "student";
        cityEntry = "Auckland";
        profile_description = "Hunting all dungeons across the land and cycling on the side";
        profile_picture = "Multimedia/dungeonHunter07.jpg"; //Non standard profile picture
        firstname = "Yuri";
        lastname =  "Cowan";

        iterations = Passwords.getNextNumIterations();
        salt = Passwords.getNextSalt();
        hash = Passwords.hash(passwordEntry.toCharArray(), salt, iterations);
        UserDAO.addUserToDB(DB, username, iterations, salt, hash, email, phoneEntry, occupationEntry, cityEntry, profile_description, profile_picture, firstname, lastname);

        System.out.println("Adding test user Julian");
        username = "julian";
        passwordEntry = "test";
        email = "jlee512@aucklanduni.ac.nz";
        phoneEntry = "0225671234";
        occupationEntry = "student";
        cityEntry = "Auckland";
        profile_description = "Writing fake social media profiles since ages ago";
        profile_picture = "Multimedia/kokako.jpg"; //Standard profile picture
        firstname = "Julian";
        lastname = "Lees";

        iterations = Passwords.getNextNumIterations();
        salt = Passwords.getNextSalt();
        hash = Passwords.hash(passwordEntry.toCharArray(), salt, iterations);
        UserDAO.addUserToDB(DB, username, iterations, salt, hash, email, phoneEntry, occupationEntry, cityEntry, profile_description, profile_picture, firstname, lastname);

        /*------------ADD/ACCESS ARTICLES----------*/
        System.out.println("Adding articles to the database: 1");
        int author_id = 1;
        String article_title = "You're a lizard Harry!";

        Timestamp timestamp = Timestamp.valueOf("2017-05-31 00:00:00");

        String article_body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque rhoncus volutpat nisl sit amet malesuada. Duis dapibus, magna non ullamcorper scelerisque, velit nisl tincidunt ante, at laoreet erat libero a mauris. Vestibulum vitae risus non massa accumsan condimentum. Phasellus lacus elit, faucibus a leo eu, mollis fringilla eros.\n" +
                "Etiam tincidunt volutpat aliquam. Vestibulum quis erat at purus fermentum pharetra. Nunc venenatis dolor nunc, non sagittis lectus suscipit sed. Nullam mollis eget leo id posuere. Sed ac sapien quam. Ut a pharetra neque. Morbi ut efficitur tortor.\n" +
                "In hac habitasse platea dictumst. Suspendisse nec odio sed dui tempus euismod. Curabitur cursus, diam sit amet porta malesuada, lacus odio ornare nunc, et lacinia sapien dolor nec nibh. In hac habitasse platea dictumst. Morbi tempor arcu nisl, in suscipit sem blandit sit amet.";

        ArticleDAO.addArticleToDB(DB, author_id, article_title, timestamp, article_body);

        System.out.println("Adding articles to the database: 2");
        author_id = 2;
        article_title = "Who is Dungeon Hunter 8?";

        timestamp = Timestamp.valueOf("2016-11-26 00:00:00");

        article_body = "Proin finibus, nisl ut tempus malesuada, eros urna tincidunt leo, vel faucibus lectus tellus a massa. Vivamus tincidunt enim erat, ut tincidunt nibh convallis vitae. Phasellus tincidunt, eros non gravida volutpat, lacus turpis mattis quam, et facilisis tellus est ut massa. Integer quis justo a orci venenatis ultricies.\n" +
                "Fusce accumsan ut ex nec aliquet. Maecenas diam dolor, ullamcorper at eros non, sagittis ornare ex. Cras placerat pharetra dui a interdum. Vestibulum vehicula convallis lacus, et dictum elit vehicula a. Sed et justo eu orci mattis mollis eget ac turpis. Quisque vel dignissim turpis. In viverra ligula at ornare lobortis.";

        ArticleDAO.addArticleToDB(DB, author_id, article_title, timestamp, article_body);

        System.out.println("Adding articles to the database: 3");
        author_id = 1;
        article_title = "Calling all Lizardsssssss - Party like it's 1998";

        timestamp = Timestamp.valueOf("1998-04-12 00:00:00");

        article_body = "Emperor angelfish sockeye salmon cuskfish rockweed gunnel knifefish orangestriped triggerfish darter Kafue pike snipefish bango Blind shark. Cow shark cutthroat trout, mudfish gudgeon platy slender snipe eel Ratfish Mexican golden trout!\n" +
                "Pricklefish thornyhead bonytongue ladyfish, roundhead sand knifefish; cookie-cutter shark cobia squeaker kokopu snakehead. Bull trout saber-toothed blenny, pikeblenny gombessa, harelip sucker arowana trout cod, pipefish, leaffish sandburrower redtooth triggerfish northern clingfish trumpeter temperate ocean-bass.\n" +
                "Basslet false moray mako shark stream catfish wahoo. Yellowbelly tail catfish lumpsucker southern sandfish blue triggerfish tripod fish. Molly Miller. Spookfish bluegill; rockling: remora New Zealand smelt lyretail, snoek dogfish Bitterling carpetshark porcupinefish, \"blue triggerfish basslet.\" Discus ocean perch skipjack tuna tilefish Black tetra roundhead, silver carp channel bass yellowmargin triggerfish dwarf loach.";

        ArticleDAO.addArticleToDB(DB, author_id, article_title, timestamp, article_body);

        System.out.println("Adding articles to the database: 4");
        author_id = 2;
        article_title = "Hunting Dungeon's for Dummies";

        timestamp = Timestamp.valueOf("2015-07-19 00:00:00");

        article_body = "Veggies es bonus vobis, proinde vos postulo essum magis kohlrabi welsh onion daikon amaranth tatsoi tomatillo melon azuki bean garlic.\n" + "Gumbo beet greens corn soko endive gumbo gourd. Parsley shallot courgette tatsoi pea sprouts fava bean collard greens dandelion okra wakame tomato. Dandelion cucumber earthnut pea peanut soko zucchini." + "\n" + "Turnip greens yarrow ricebean rutabaga endive cauliflower sea lettuce kohlrabi amaranth water spinach avocado daikon napa cabbage asparagus winter purslane kale. Celery potato scallion desert raisin horseradish spinach carrot soko. Lotus root water spinach fennel kombu maize bamboo shoot green bean swiss chard seakale pumpkin onion chickpea gram corn pea. Brussels sprout coriander water chestnut gourd swiss chard wakame kohlrabi beetroot carrot watercress. Corn amaranth salsify bunya nuts nori azuki bean chickweed potato bell pepper artichoke.";

        ArticleDAO.addArticleToDB(DB, author_id, article_title, timestamp, article_body);

        System.out.println("Adding articles to the database: 5");
        author_id = 1;
        article_title = "Computer Programming: A Lizard's Perspective";

        timestamp = Timestamp.valueOf("2002-09-01 00:00:00");

        article_body = "Airedale melted cheese stilton. Airedale gouda macaroni cheese airedale cheese strings pepper jack cow macaroni cheese. Fromage babybel feta pepper jack feta everyone loves cauliflower cheese cheese on toast.";

        ArticleDAO.addArticleToDB(DB, author_id, article_title, timestamp, article_body);

        System.out.println("Adding articles to the database: 6");
        author_id = 3;
        article_title = "How to Paint Hyper Realistic Tunnels";

        timestamp = Timestamp.valueOf("2016-02-17 00:00:00");

        article_body = "I care. So, what do you think of her, Han? The more you tighten your grip, Tarkin, the more star systems will slip through your fingers. The more you tighten your grip, Tarkin, the more star systems will slip through your fingers." + "\n" + "Look, I ain't in this for your revolution, and I'm not in it for you, Princess. I expect to be well paid. I'm in it for the money. Partially, but it also obeys your commands. Escape is not his plan. I must face him, alone." + "\n" + "Look, I can take you as far as Anchorhead. You can get a transport there to Mos Eisley or wherever you're going. Remember, a Jedi can feel the Force flowing through him. Partially, but it also obeys your commands." + "\n" + "The plans you refer to will soon be back in our hands. But with the blast shield down, I can't even see! How am I supposed to fight? I have traced the Rebel spies to her. Now she is my only link to finding their secret base." + "\n" + "Remember, a Jedi can feel the Force flowing through him. I call it luck. Escape is not his plan. I must face him, alone. Red Five standing by.";

        ArticleDAO.addArticleToDB(DB, author_id, article_title, timestamp, article_body);

        /*------------ADD/ACCESS COMMENTS----------*/



        /*------------ADD/ACCESS MULTIMEDIA--------*/
        String genericFilePath = "Multimedia/";

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
        userInputFilename = "port.mp4";
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
