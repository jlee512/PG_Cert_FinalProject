package login.system.dao;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by catherinedechalain on 1/06/17.
 */
public class Comment implements Serializable {
    private int commentID;
    private int articleID;
    private int authorID;
    private int parentCommentID;
    private Date date;
    private String content;
    private boolean isParent;

    public Comment(){
        new Comment();
    }

    public Comment(int articleID, int authorID, int parentCommentID, Date date, String content){
        this.articleID = articleID;
        this.authorID = authorID;
        this.parentCommentID = parentCommentID;
        this.date = date;
        this.content = content;
        this.isParent = false;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID){
        this.commentID = commentID;
    }

    public int getArticleID(){
        return articleID;
    }

    public void setArticleID(int articleID){
        this.articleID = articleID;
    }

    public int getAuthorID(){
        return authorID;
    }

    public void setAuthorID(int authorID){
        this.authorID = authorID;
    }

    public int getParentCommentID(){
        return parentCommentID;
    }

    public void setParentCommentID(int parentCommentID){
        this.parentCommentID = parentCommentID;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public boolean isParent(){
        return isParent;
    }
}