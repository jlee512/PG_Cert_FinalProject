import login.system.dao.Article;
import login.system.dao.ArticleDAO;
import login.system.db.MySQL;

import java.sql.Date;
import java.sql.Timestamp;


/**
 * Created by jlee512 on 3/06/2017.
 */
public class ArticleGenerator {

    static MySQL DB = new MySQL();

    public static Article generateRandomArticle() {

        //This test will generate a random username most of the time - interrogate if failure and rule out duplicate username
        int articleRandNum = (int) (Math.random() * 10000 + 100);

        /*Generate a random author between 1 and 3*/
        int author_id = (int) ((Math.random() * 3) + 1);

        /*Prepare the article title*/
        String articleInputTestTitle = "unitTest" + articleRandNum;

        /*Prepare the article date*/
        Timestamp articleSubmissionTimestamp = new Timestamp(System.currentTimeMillis());

        /*Prepare the article body*/
        String articleInputTestBody = "unitTest" + articleRandNum + ">>>>>>>>>" + "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam efficitur, tortor et facilisis tempor, lectus magna lacinia ligula, in facilisis ex turpis quis mauris. Vestibulum non neque eget nulla vulputate imperdiet at sit amet est. Pellentesque porta dolor elit, non efficitur dui lacinia at." + ">>>>>>>>>>>>>" + "unitTest" + articleRandNum;

        return new Article(author_id, articleInputTestTitle, articleSubmissionTimestamp, articleInputTestBody);

    }

    public static Article getKnownArticle() {

        Article knownArticle = ArticleDAO.getArticle(DB, 3);

        return knownArticle;

    }

    public static void main(String[] args) {
        ArticleGenerator.getKnownArticle();
    }

}
