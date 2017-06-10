import login.system.dao.Article;
import login.system.dao.ArticleDAO;
import login.system.dao.Comment;
import login.system.dao.CommentDAO;
import login.system.db.MySQL;

import java.sql.*;


/**
 * Created by jlee512 on 3/06/2017.
 */
public class CommentGenerator {

    static MySQL DB = new MySQL();

    public static void generateRandomCommentAndReply() {

        /*Generate random article on which we will post comments and add to the database*/
        Article commentArticle = ArticleGenerator.generateRandomArticle();
        ArticleDAO.addArticleToDB(DB, commentArticle.getAuthor_id(), commentArticle.getArticle_title(), commentArticle.getArtcle_timestamp(), commentArticle.getArticle_body());

        /*Get article details based on timestamp and title*/
        try (Connection c = DB.connection()) {
            try (PreparedStatement stmt = c.prepareStatement("SELECT article_id FROM uploaded_articles WHERE article_title = ?")) {

                stmt.setString(1, commentArticle.getArticle_title());

                try (ResultSet r = stmt.executeQuery()) {

                    if (r.next()) {

                        int article_id = r.getInt("article_id");
                        commentArticle.setArticle_id(article_id);

                    } else {
                        System.out.println("Article not found");
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(commentArticle.getArticle_id());

        /*Prepare the article body*/
        String topLevelCommentBody = "Top level comment unitTest" + commentArticle.getArticle_title() + ">>>>>>>>>" + "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam efficitur, tortor et facilisis tempor, lectus magna lacinia ligula, in facilisis ex turpis quis mauris." + ">>>>>>>>>>>>>" + " Top level comment unitTest" + commentArticle.getArticle_title();
        Timestamp topLevelCommentTimestamp = new Timestamp(System.currentTimeMillis());

        String secondLevelCommentBody = "Second level comment unitTest" + commentArticle.getArticle_title() + ">>>>>>>>>" + "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam efficitur, tortor et facilisis tempor, lectus magna lacinia ligula, in facilisis ex turpis quis mauris." + ">>>>>>>>>>>>>" + " Second level comment unitTest" + commentArticle.getArticle_title();
        /*Set reply time to be 5 minutes ahead of current time*/
        Timestamp secondLevelCommentTimestamp = new Timestamp(System.currentTimeMillis() + 300000);

        /*Construct top level comment*/
        Comment topLevelComment = new Comment(commentArticle.getArticle_id(), ((int) (Math.random() * 3 + 1)), -1, topLevelCommentTimestamp, topLevelCommentBody);
        topLevelComment.setIsParent(false);
        CommentDAO.addComment(DB, topLevelComment.getAuthorID(), topLevelComment.getArticleID(), -1, topLevelComment.getTimestamp(), topLevelComment.getContent());

        /*Lookup top level comment_id to set as the parent comment_id*/
        try (Connection c = DB.connection()) {
            try (PreparedStatement stmt = c.prepareStatement("SELECT comment_id FROM posted_comments WHERE comment_body = ?")) {

                stmt.setString(1, topLevelComment.getContent());

                try (ResultSet r = stmt.executeQuery()) {

                    if (r.next()) {

                        int comment_id = r.getInt("comment_id");
                        topLevelComment.setCommentID(comment_id);

                    } else {
                        System.out.println("Article not found");
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Comment secondLevelComment = new Comment(commentArticle.getArticle_id(), ((int) (Math.random() * 3 + 1)), topLevelComment.getCommentID(), secondLevelCommentTimestamp, secondLevelCommentBody);
        CommentDAO.addComment(DB, secondLevelComment.getAuthorID(), secondLevelComment.getArticleID(), topLevelComment.getCommentID(), secondLevelComment.getTimestamp(), secondLevelComment.getContent());
        /*This update appears to alter the timestamp*/
        CommentDAO.setCommentAsParent(DB, topLevelComment.getCommentID());


    }

    public static void main(String[] args) {
        CommentGenerator.generateRandomCommentAndReply();
    }

}