/**
 * Created by catherinedechalain on 1/06/17.
 */

import junit.framework.TestCase;
import login.system.dao.Article;
import login.system.dao.ArticleDAO;
import login.system.db.MySQL;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/*Fix test order*/
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArticleTest {
    private Article myArticle;
    MySQL DB = new MySQL();
    /*DAO tests have been designed to run sequentially (i.e. add a user and check their login)*/
    static Article testArticle = ArticleGenerator.generateRandomArticle();
    Article knownExistingArticle;


    @Before
    public void setup() {
        knownExistingArticle = ArticleGenerator.getKnownArticle();
    }

    /*---------------------------------------------------------------------*/
    /*Test ArticleDAO*/

    @Test
    public void test1ArticleAdditionToDB() {

        /*Check for successful database addition code of "1"*/
        assertEquals(1, ArticleDAO.addArticleToDB(DB, testArticle.getAuthor_id(), testArticle.getArticle_title(), testArticle.getArtcle_timestamp(), testArticle.getArticle_body()));

    }

    @Test
    public void test2ArticleRejectionFromDBDuplicate() {
        
        TestCase.assertEquals(2, ArticleDAO.addArticleToDB(DB, knownExistingArticle.getAuthor_id(), knownExistingArticle.getArticle_title(), knownExistingArticle.getArtcle_timestamp(), knownExistingArticle.getArticle_body()));
                
    }
    
    @Test
    public void test3IndividualArticleAccessFromDB() {
        
        /*Pull the article from the database by the article ID*/
        testArticle = ArticleDAO.getArticle(DB, 3);
        
        /*Lookup Article by Article ID and access the parameters returned by the MySQL */
        assertEquals(knownExistingArticle.getArticle_id(), testArticle.getArticle_id());
        assertEquals(knownExistingArticle.getAuthor_username(), testArticle.getAuthor_username());
        assertEquals(knownExistingArticle.getAuthor_firstname(), testArticle.getAuthor_firstname());
        assertEquals(knownExistingArticle.getAuthor_lastname(), testArticle.getAuthor_lastname());
        assertEquals(knownExistingArticle.getArtcle_timestamp(), testArticle.getArtcle_timestamp());
        assertEquals(knownExistingArticle.getArticle_title(), testArticle.getArticle_title());
        assertEquals(knownExistingArticle.getArticle_body(), testArticle.getArticle_body());
        
    }

    @Test
    public void test3AccessNArticleByDate() throws ParseException {

        /*Pull the newest 'N' (N = 3 for this test) articles from the database*/
        List<Article> articles = ArticleDAO.getfirstNArticlePreviewsByDate(DB, 0, 3);

        int[] knownAuthorIds = {1, 1, 2};

        String[] knownAuthorUsernames = {"catherine", "catherine", "yuri"};
        String[] knownAuthorFirstnames = {"Catherine", "Catherine", "Yuri"};
        String[] knownAuthorLastnames = {"de Chalain", "de Chalain", "Cowan"};

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
            assertTrue(articles.get(i).getAuthor_username().equals(knownAuthorUsernames[i]));
            assertTrue(articles.get(i).getAuthor_firstname().equals(knownAuthorFirstnames[i]));
            assertTrue(articles.get(i).getAuthor_firstname().equals(knownAuthorFirstnames[i]));
            assertTrue(articles.get(i).getArtcle_timestamp().equals(knownArticleDates[i]));
            assertTrue(articles.get(i).getArticle_title().equals(knownArticleTitles[i]));
            assertTrue(articles.get(i).getArticle_body().equals(knownArticleBody[i]));

        }



    }

    @Test
    public void test4DeleteAnArticleTest() {



    }

}
