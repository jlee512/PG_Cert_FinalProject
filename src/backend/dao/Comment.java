package backend.dao;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by catherinedechalain on 1/06/17.
 */

/**
 * The Comment class stores the backend representation of an comment object.
 *
 * Comments can be added/edited/deleted/replied-to by users.
 *
 */
public class Comment implements Serializable {

    /*Comment instance variables*/
    private int commentID;
    private int articleID;
    private int authorID;
    private int parentCommentID;
    private Timestamp timestamp;
    private String content;
    private boolean isParent;

    /*Addition instance variables used when article lookups are joined with users table*/
    private String author_username;
    private String author_firstname;
    private String author_lastname;

    /*---------------------------------------------
            Comment Constructors
------------------------------------------------*/

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

    /*----------------------------------------------------------------*/

    /**
     * @param commentID represents the autoincrement database id assigned to a specific comment
     */

    public void setCommentID(int commentID){
        this.commentID = commentID;
    }

    public int getCommentID() {
        return commentID;
    }


    /**
     * @param articleID represents the foreign key between the uploaded_articles table and the posted_comments table.
     */

    public void setArticleID(int articleID){
        this.articleID = articleID;
    }

    public int getArticleID(){
        return articleID;
    }


    /**
     * @param authorID represents the foreign key between the posted_comments table and the registered_users table.
     */

    public void setAuthorID(int authorID){
        this.authorID = authorID;
    }

    public int getAuthorID(){
        return authorID;
    }

    /**
     * @param parentCommentID represents the foreign key between the parent comments and reply comments (i.e. children).
     */

    public void setParentCommentID(int parentCommentID){
        this.parentCommentID = parentCommentID;
    }

    public int getParentCommentID(){
        return parentCommentID;
    }


    /**
     * @param timestamp represents the comment timestamp
     */

    public void setTimestamp(Timestamp timestamp){
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp(){return timestamp;}


    /**
     * @param content represents the comment text
     */

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }


    /**
     * @param isParent a boolean representing whether the comment is a parent comment
     */

    public void setIsParent(boolean isParent){
        this.isParent = isParent;
    }

    public boolean getIsParent(){
        return isParent;
    }


    /**
     * @param author_username represents the username string for a specific author_id when the posted_comments table is joined to the registered_users table
     */

    public void setAuthor_username(String author_username) {
        this.author_username = author_username;
    }

    public String getAuthor_username() {
        return author_username;
    }


    /**
     * @param author_firstname represents the firstname string for a specific author_id when the uploaded_articles table is joined to the registered_users table
     */

    public void setAuthor_firstname(String author_firstname) {
        this.author_firstname = author_firstname;
    }

    public String getAuthor_firstname() {
        return author_firstname;
    }


    /**
     * @param author_lastname represents the lastname string for a specific author_id when the uploaded_articles table is joined to the registered_users table
     */

    public void setAuthor_lastname(String author_lastname) {
        this.author_lastname = author_lastname;
    }

    public String getAuthor_lastname() {
        return author_lastname;
    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}