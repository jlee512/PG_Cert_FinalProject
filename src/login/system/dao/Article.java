package login.system.dao;

import java.sql.Date;

/**
 * Created by ycow194 on 1/06/2017.
 */
public class Article {

    private int article_id;
    private int author_id;
    private String article_title;
    private Date article_date;
    private String article_body;


    public Article(int article_id, int author_id, String article_title, Date article_date, String article_body) {
        this.article_id = article_id;
        this.author_id = author_id;
        this.article_title = article_title;
        this.article_date = article_date;
        this.article_body = article_body;
    }

    public void setArticleParameters(int article_id, int author_id, String article_title, Date article_date, String article_body) {
        this.article_id = article_id;
        this.author_id = author_id;
        this.article_title = article_title;
        this.article_date = article_date;
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

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public Date getArticle_date() {
        return article_date;
    }

    public void setArticle_date(Date article_date) {
        this.article_date = article_date;
    }

    public String getArticle_body() {
        return article_body;
    }

    public void setArticle_body(String article_body) {
        this.article_body = article_body;
    }

    /* End of Getters and Setters */
}
