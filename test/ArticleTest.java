/**
 * Created by catherinedechalain on 1/06/17.
 */

import junit.framework.TestCase;
import login.system.dao.Article;
import login.system.dao.ArticleDAO;
import login.system.dao.UserDAO;
import login.system.db.MySQL;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArticleTest {
    private Article myArticle;
    MySQL DB = new MySQL();
    /*DAO tests have been designed to run sequentially (i.e. add a user and check their login)*/
    static Article testArticle = ArticleGenerator.generateRandomArticle();
    static Article testArticle2 = ArticleGenerator.generateRandomArticle();
    Article knownExistingArticle;


    @Before
    public void setup() {
        myArticle = new Article(2, "Title", new Date(0), "This is an article");
        knownExistingArticle = ArticleGenerator.getKnownArticle();
    }

    @Test
    public void testConstructor() {
        assertEquals(2, myArticle.getAuthor_id());
        assertEquals("Title", myArticle.getArticle_title());
        Date now = new Date(0);
        assertEquals(now, myArticle.getArticle_date());
        assertEquals("This is an article", myArticle.getArticle_body());
    }

    @Test
    public void testSetArticleID() {
        myArticle.setArticle_id(238);
        assertEquals(238, myArticle.getArticle_id());
    }

    @Test
    public void testSetAuthorID() {
        myArticle.setAuthor_id(397);
        assertEquals(397, myArticle.getAuthor_id());
    }

    @Test
    public void testSetArticleTitle() {
        myArticle.setArticle_title("A New Title");
        assertEquals("A New Title", myArticle.getArticle_title());
    }

    @Test
    public void testSetArticleDate() {
        Date date = new Date(30000);
        myArticle.setArticle_date(date);
        assertEquals(date, myArticle.getArticle_date());
    }

    @Test
    public void testSetArticleBody() {
        myArticle.setArticle_body("This is a new article body");
        assertEquals("This is a new article body", myArticle.getArticle_body());
    }

    /*---------------------------------------------------------------------*/
    /*Test ArticleDAO*/

    @Test
    public void test1ArticleAdditionToDB() {

        /*Check for successful database addition code of "1"*/
        assertEquals(1, ArticleDAO.addArticleToDB(DB, testArticle.getAuthor_id(), testArticle.getArticle_title(), testArticle.getArticle_date(), testArticle.getArticle_body()));

    }

    @Test
    public void test2ArticleRejectionFromDBDuplicate() {
        
        TestCase.assertEquals(2, ArticleDAO.addArticleToDB(DB, knownExistingArticle.getAuthor_id(), knownExistingArticle.getArticle_title(), knownExistingArticle.getArticle_date(), knownExistingArticle.getArticle_body()));
                
    }
    
    @Test
    public void test3IndividualArticleAccessFromDB() {
        
        /*Pull the article from the database by the article ID*/
        testArticle = ArticleDAO.getArticle(DB, 3);
        
        /*Lookup Article by Article ID and access the parameters returned by the MySQL */
        assertEquals(knownExistingArticle.getArticle_id(), testArticle.getArticle_id());
        assertEquals(knownExistingArticle.getAuthor_id(), testArticle.getAuthor_id());
        assertEquals(knownExistingArticle.getArticle_date(), testArticle.getArticle_date());
        assertEquals(knownExistingArticle.getArticle_title(), testArticle.getArticle_title());
        assertEquals(knownExistingArticle.getArticle_body(), testArticle.getArticle_body());
        
    }

    @Test
    public void test3AccessNArticleByDate() throws ParseException {

        /*Pull the newest 'N' (N = 3 for this test) articles from the database*/
        List<Article> articles = ArticleDAO.getfirstNArticlesByDate(DB, 3);

        for (Article article : articles) {
            System.out.println(article.getArticle_title());
        }

        int[] knownAuthorIds = {1, 1, 2};

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = dateFormat.parse("1998-01-12");
        Date date1 = new Date(utilDate.getTime());
        utilDate = dateFormat.parse("2002-01-01");
        Date date2 = new Date(utilDate.getTime());
        utilDate = dateFormat.parse("2015-01-19");
        Date date3 = new Date(utilDate.getTime());
        Date[] knownArticleDates = {date1, date2, date3};

        String[] knownArticleTitles = {"Calling all Lizardsssssss - Party like it's 1998","Computer Programming: A Lizard's Perspective","Hunting Dungeon's for Dummies"};

        String[] knownArticleBody = {"Medaka ocean sunfish sturgeon pomfret stonecat brook trout ray central mudminnow righteye flounder. Zebra trout bala shark candiru louvar lionfish fathead sculpin pikeperch southern smelt prickleback Celebes rainbowfish.", "Airedale melted cheese stilton. Airedale gouda macaroni cheese airedale cheese strings pepper jack cow macaroni cheese. Fromage babybel feta pepper jack feta everyone loves cauliflower cheese cheese on toast.", "Nori grape silver beet broccoli kombu beet greens fava bean potato quandong celery. Bunya nuts black-eyed pea prairie turnip leek lentil turnip greens parsnip. Sea lettuce lettuce water chestnut eggplant winter purslane fennel azuki bean earthnut pea sierra leone bologi leek soko chicory celtuce parsley."};


        for (int i = 0; i < 3; i++) {

            assertTrue(articles.get(i).getArticle_title().equals(knownArticleTitles[i]));
            System.out.println(articles.get(i).getArticle_date());
            System.out.println(knownArticleDates[i]);
            System.out.println(articles.get(i).getArticle_date().compareTo(knownArticleDates[i]));

            assertTrue(articles.get(i).getArticle_date().equals(knownArticleDates[i]));
            assertTrue(articles.get(i).getArticle_title().equals(knownArticleTitles[i]));
            assertTrue(articles.get(i).getArticle_body().equals(knownArticleBody[i]));

        }



    }

}
