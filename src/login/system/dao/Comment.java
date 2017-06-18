package login.system.dao;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by catherinedechalain on 1/06/17.
 */
public class Comment implements Serializable {
    private int commentID;
    private int articleID;
    private int authorID;
    private int parentCommentID;
    private Timestamp timestamp;
    private String content;
    private boolean isParent;
    private String author_username;
    private String author_firstname;
    private String author_lastname;

    /*No argument constructor*/
    public Comment(){

    }

    public Comment(int articleID, int authorID, int parentCommentID, Timestamp timestamp, String content){
        this.articleID = articleID;
        this.authorID = authorID;
        this.parentCommentID = parentCommentID;
        this.timestamp = timestamp;
        this.content = content;
        this.isParent = false;
    }

    /*Getters and Setters*/

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

    public Timestamp getTimestamp(){return timestamp;}

    public void setTimestamp(Timestamp timestamp){
        this.timestamp = timestamp;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public boolean getIsParent(){
        return isParent;
    }

    public void setIsParent(boolean isParent){
        this.isParent = isParent;
    }

    public boolean isParent() {
        return isParent;
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

    public void setParent(boolean parent) {
        isParent = parent;
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
}