package login.system.dao;

import login.system.db.MySQL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by ycow194 on 1/06/2017.
 */
public class ArticleDAO {

    /*Add a new article to the Database*/
    public static int addArticleToDB(MySQL DB, int author_id, String article_title, Date date, String article_body) {

        /*Return method status
        * (1) Success
        * (2) SQL error - missing fields
        * (3) Database connection error*/
    /*------------------------------------------------------------*/

        Article tempArticle = new Article(author_id, article_title, date, article_body);

        try (Connection c = DB.connection()) {
        /*Connect to the database and add user*/
            try (PreparedStatement stmt = c.prepareStatement("INSERT INTO uploaded_articles (author_id, DATE ?, article_title, article_body) VALUES (?, ?, ?, ?)")) {

                stmt.setInt(1, tempArticle.getAuthor_id());
                stmt.setDate(2, tempArticle.getArticle_date());
                stmt.setString(3, tempArticle.getArticle_title());
                stmt.setString(4, tempArticle.getArticle_body());

                /*Execute prepared statement*/
                stmt.executeUpdate();
                System.out.println("Article added to the database");
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

    public static

}
