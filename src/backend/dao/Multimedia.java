package backend.dao;

import java.io.Serializable;

/**
 * Created by jlee512 on 1/06/2017.
 */

/**
 * The Multimedia class stores the backend representation of an multimedia object.
 *
 * Multimedia can be added/deleted by users.
 *
 */

public class Multimedia implements Serializable {

    /*Article instance variables*/
    private int multimedia_id;
    private int article_id;
    private String file_type;
    private String file_path;
    private String multimedia_title;

    /*Addition instance variables used when article lookups are joined with users table*/
    private String username;

    /*---------------------------------------------
            Multimedia Constructors
------------------------------------------------*/

    /*Blank constructor for staged variable setting when uploading multimedia*/
    public Multimedia(){
    }

    /*Used when extracting multimedia information form the database*/
    public Multimedia(int multimedia_id, int article_id, String file_type, String file_path, String multimedia_title) {
        this.article_id = article_id;
        this.multimedia_id = multimedia_id;
        this.file_type = file_type;
        this.file_path = file_path;
        this.multimedia_title = multimedia_title;
    }

    /*Used when adding multimedia to the database*/
    public Multimedia(String file_type, String file_path, String multimedia_title) {
        this.file_type = file_type;
        this.file_path = file_path;
        this.multimedia_title = multimedia_title;
    }

        /*----------------------------------------------------------------*/

    /**
     * @param multimedia_id represents the autoincrement database id assigned to a specific multimedia post
     */

    public void setMultimedia_id(int multimedia_id) {
        this.multimedia_id = multimedia_id;
    }

    public int getMultimedia_id() {
        return multimedia_id;
    }


    /**
     * @param article_id represents the foreign key between the posted_multimedia table and the uploaded_articles table.
     */

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getArticle_id() {
        return article_id;
    }


    /**
     *@param file_type represents the article filetype with a '.' prefix (e.g .jpeg, .png, .mp4)
     */

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_type() {
        return file_type;
    }


    /**
     *@param file_path represents the article filepath from the root directory e.g. (Multimedia/kokako.jpg)
     */

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_path() {
        return file_path;
    }


    /**
     *@param multimedia_title represents the raw filename e.g. (kokako.jpg)
     */

    public void setMultimedia_title(String multimedia_title) {
        this.multimedia_title = multimedia_title;
    }

    public String getMultimedia_title() {
        return multimedia_title;
    }


    /**
     * @param username represents the username string for a specific author_id when the posted_multimedia table is joined to the registered_users table
     */

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
