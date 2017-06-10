package login.system.dao;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ycow194 on 1/06/2017.
 */
public class Article implements Serializable {

    private int article_id;
    private int author_id;
    private String author_username;
    private String author_firstname;
    private String author_lastname;
    private String article_title;
    private Timestamp artcle_timestamp;
    private String article_body;
    private List<Comment> top_level_comments = null;


    public Article(int author_id, String article_title, Timestamp article_timestamp, String article_body) {
        this.author_id = author_id;
        this.article_title = article_title;
        this.artcle_timestamp = article_timestamp;
        this.article_body = article_body;
    }

    public Article(int article_id, String author_username, String author_firstname, String author_lastname, String article_title, Timestamp article_timestamp, String article_body) {
        this.article_id = article_id;
        this.author_username = author_username;
        this.author_firstname = author_firstname;
        this.author_lastname = author_lastname;
        this.article_title = article_title;
        this.artcle_timestamp = article_timestamp;
        this.article_body = article_body;
    }

    public void setArticleParameters(int article_id, String author_username, String author_firstname, String author_lastname, String article_title, Timestamp article_timestamp, String article_body) {
        this.article_id = article_id;
        this.author_username = author_username;
        this.author_firstname = author_firstname;
        this.author_lastname = author_lastname;
        this.article_title = article_title;
        this.artcle_timestamp = article_timestamp;
        this.article_body = article_body;
    }

    /* Getters and Setters*/

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public String getAuthor_username() {
        return author_username;
    }

    public String getAuthor_firstname() {
        return author_firstname;
    }

    public String getAuthor_lastname() {
        return author_lastname;
    }

    public List<Comment> getTop_level_comments() {
        return top_level_comments;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public Timestamp getArtcle_timestamp() {
        return artcle_timestamp;
    }

    public void setArtcle_timestamp(Timestamp artcle_timestamp) {
        this.artcle_timestamp = artcle_timestamp;
    }

    public String getArticle_body() {
        return article_body;
    }

    public void setArticle_body(String article_body) {
        this.article_body = article_body;
    }

    public void setAuthor_username(String author_username) {
        this.author_username = author_username;
    }

    public void setAuthor_firstname(String author_firstname) {
        this.author_firstname = author_firstname;
    }

    public void setAuthor_lastname(String author_lastname) {
        this.author_lastname = author_lastname;
    }

    public void setTop_level_comments(List<Comment> top_level_comments) {
        this.top_level_comments = top_level_comments;
    }

    /* End of Getters and Setters */
}
