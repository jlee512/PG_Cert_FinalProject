package backend.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * The Article class stores the backend representation of an article object.
 *
 * Articles can be added/edited/deleted/commented-on by users.
 *
 */

public class Article implements Serializable {

    /*Article instance variables*/
    private int article_id;
    private int author_id;
    private String article_title;
    private Timestamp artcle_timestamp;
    private String article_body;
    private int comment_count;
    private List<Comment> top_level_comments = null;

    /*Addition instance variables used when article lookups are joined with users table*/
    private String author_username;
    private String author_firstname;
    private String author_lastname;

/*---------------------------------------------
            Article Constructors
------------------------------------------------*/

    public Article () {
    }

    /*Used when adding-an-article. Also used to populate an empty temp articles in DAO methods*/

    public Article(int author_id, String article_title, Timestamp article_timestamp, String article_body) {
        this.author_id = author_id;
        this.article_title = article_title;
        this.artcle_timestamp = article_timestamp;
        this.article_body = article_body;
    }


    /*Constructor used when joining the articles database table with the username table*/
    public Article(int article_id, String author_username, String author_firstname, String author_lastname, String article_title, Timestamp article_timestamp, String article_body, int comment_count) {
        this.article_id = article_id;
        this.author_username = author_username;
        this.author_firstname = author_firstname;
        this.author_lastname = author_lastname;
        this.article_title = article_title;
        this.artcle_timestamp = article_timestamp;
        this.article_body = article_body;
        this.comment_count = comment_count;
    }

    /*----------------------------------------------------------------*/

    /*Used in the ArticleDAO to set all the parameters for an Article instance which has been sucessfully looked up in the database */

    public void setArticleParameters(int article_id, String author_username, String author_firstname, String author_lastname, String article_title, Timestamp article_timestamp, String article_body, int comment_count) {
        this.article_id = article_id;
        this.author_username = author_username;
        this.author_firstname = author_firstname;
        this.author_lastname = author_lastname;
        this.article_title = article_title;
        this.artcle_timestamp = article_timestamp;
        this.article_body = article_body;
        this.comment_count = comment_count;
    }

    /**
     * @param article_id represents the autoincrement database id assigned to a specific article
     */
    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    /**
     * @param author_id represents the foreign key between the uploaded_articles table and the registered_users table.
     */

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getAuthor_id() {
        return author_id;
    }


    /**
     * @param author_username represents the username string for a specific author_id when the uploaded_articles table is joined to the registered_users table
     */

    public void setAuthor_username(String author_username) {
        this.author_username = author_username;
    }

    public String getAuthor_username() {
        return author_username;
    }

    /**
     *@param author_firstname represents the author firstname string for a specific author_id when the uploaded_articles table is joined to the registered_users table
     */

    public void setAuthor_firstname(String author_firstname) {
        this.author_firstname = author_firstname;
    }

    public String getAuthor_firstname() {
        return author_firstname;
    }

    /**
     *@param author_lastname represents the author lastname string for a specific author_id when the uploaded_articles table is joined to the registered_users table
     */

    public void setAuthor_lastname(String author_lastname) {
        this.author_lastname = author_lastname;
    }

    public String getAuthor_lastname() {
        return author_lastname;
    }

    /**
     *@param article_title represents the article title text
     */

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_title() {
        return article_title;
    }

    /**
     * @param artcle_timestamp represents the article timestamp
     */

    public void setArtcle_timestamp(Timestamp artcle_timestamp) {
        this.artcle_timestamp = artcle_timestamp;
    }

    public Timestamp getArtcle_timestamp() {
        return artcle_timestamp;
    }

    /**
     * @param article_body represents the article body text
     */

    public void setArticle_body(String article_body) {
        this.article_body = article_body;
    }

    public String getArticle_body() {
        return article_body;
    }

    /**@param top_level_comments represents the top level comments for a given article.
     *
     * This variable is used for article viewing when joining the uploaded_articles and the posted_comments tables.
     */

    public void setTop_level_comments(List<Comment> top_level_comments) {
        this.top_level_comments = top_level_comments;
    }

    public List<Comment> getTop_level_comments() {
        return top_level_comments;
    }


    /**
     * @param comment_count represents the article's comment count (for display purposes)
     */

    public void setComment_count(int comment_count){
        this.comment_count = comment_count;
    }


    public int getComment_count(){
        return comment_count;
    }


    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
