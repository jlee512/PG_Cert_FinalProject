/**
 * Created by catherinedechalain on 1/06/17.
 */

import login.system.dao.Article;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

public class ArticleTest {
    private Article myArticle;

    @Before
    public void setup() {
        myArticle = new Article(2, "Title", new Date(0), "This is an article");
    }

    @Test
    public void testConstructor() {
        assertEquals(1, myArticle.getArticle_id());
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

//    @Test
//    public void test

}
