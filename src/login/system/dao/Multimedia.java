package login.system.dao;

import java.io.Serializable;

/**
 * Created by jlee512 on 1/06/2017.
 */
public class Multimedia implements Serializable {

    private int multimedia_id;
    private int article_id;
    private String file_type;
    private String file_path;
    private String multimedia_title;

    public Multimedia(String file_type, String file_path, String multimedia_title) {
        this.file_type = file_type;
        this.file_path = file_path;
        this.multimedia_title = multimedia_title;
    }

    public Multimedia(int multimedia_id, int article_id, String file_type, String file_path, String multimedia_title) {
        this.multimedia_id = multimedia_id;
        this.article_id = article_id;
        this.file_type = file_type;
        this.file_path = file_path;
        this.multimedia_title = multimedia_title;
    }

    public int getMultimedia_id() {
        return multimedia_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public String getFile_type() {
        return file_type;
    }

    public String getFile_path() {
        return file_path;
    }

    public String getMultimedia_title() {
        return multimedia_title;
    }

    public void setMultimedia_id(int multimedia_id) {
        this.multimedia_id = multimedia_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public void setMultimedia_title(String multimedia_title) {
        this.multimedia_title = multimedia_title;
    }

    public void setMultimediaParameters(int multimedia_id, int article_id, String file_type, String file_path, String multimedia_title) {
        this.multimedia_id = multimedia_id;
        this.article_id = article_id;
        this.file_type = file_type;
        this.file_path = file_path;
        this.multimedia_title = multimedia_title;
    }

}
