package login.system.dao;

/**
 * Created by catherinedechalain on 1/06/17.
 */
import login.system.db.MySQL;

import java.sql.*;
        import java.util.ArrayList;
        import java.util.List;

public class CommentDAO {

    public CommentDAO(){

    }

    //Returns a list of comments for a particular article.
    public List<Comment> getCommentsByArticle(MySQL DB, int articleID) {
        List<Comment> comments = null;
        //Will be updated to our database details.
        try (Connection conn = DB.connection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM comments WHERE article_id = ?;")){
                statement.setInt(1,articleID);
                try (ResultSet resultSet = statement.executeQuery()){
                    comments = new ArrayList<>();
                    while (resultSet.next()){
                        Comment comment = new Comment();
                        comment.setArticleID(articleID);
                        comment.setAuthorID(resultSet.getInt(resultSet.findColumn("author_id")));
                        comment.setCommentID(resultSet.getInt(resultSet.findColumn("comment_id")));
                        comment.setParentCommentID(resultSet.getInt(resultSet.findColumn("parent_comment_id")));
                        comment.setDate(resultSet.getDate(resultSet.findColumn("date")));
                        comment.setContent(resultSet.getString(resultSet.findColumn("content")));
                        comments.add(comment);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return comments;
    }

    //Returns a list of all children comments of a particular comment.
    public List<Comment> getNestedComments(MySQL DB, int parentCommentID){
        List<Comment> comments = null;
        //Will be updated to our database details.
        try (Connection conn = DB.connection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM comments WHERE parent_comment_id = ?;")){
                statement.setInt(1, parentCommentID);
                try (ResultSet resultSet = statement.executeQuery()){
                    comments = new ArrayList<>();
                    while (resultSet.next()){
                        Comment comment = new Comment();
                        comment.setParentCommentID(parentCommentID);
                        comment.setArticleID(resultSet.getInt(resultSet.findColumn("article_id")));
                        comment.setCommentID(resultSet.getInt(resultSet.findColumn("comment_id")));
                        comment.setAuthorID(resultSet.getInt(resultSet.findColumn("author_id")));
                        comment.setDate(resultSet.getDate(resultSet.findColumn("date")));
                        comment.setContent(resultSet.getString(resultSet.findColumn("content")));
                        comments.add(comment);
                    }
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return comments;
    }

    //Returns a list of all comments by a particular author.
    public List<Comment> getCommentsByAuthor(MySQL DB, int authorID){
        List<Comment> comments = null;
        //Will be updated to our database details.
        try (Connection conn = DB.connection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM comments WHERE author_id = ?;")){
                statement.setInt(1, authorID);
                try (ResultSet resultSet = statement.executeQuery()){
                    comments = new ArrayList<>();
                    while (resultSet.next()){
                        Comment comment = new Comment();
                        comment.setAuthorID(authorID);
                        comment.setArticleID(resultSet.getInt(resultSet.findColumn("article_id")));
                        comment.setCommentID(resultSet.getInt(resultSet.findColumn("comment_id")));
                        comment.setDate(resultSet.getDate(resultSet.findColumn("date")));
                        comment.setContent(resultSet.getString(resultSet.findColumn("content")));
                        comment.setParentCommentID(resultSet.getInt(resultSet.findColumn("parent_comment_id")));
                        comments.add(comment);
                    }
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return comments;
    }

    public String addComment(MySQL DB, int commentID, int authorID, int articleID, int parentCommentID, Date date, String content){
        String status = "Could not add a comment at this time.";
        Comment comment = new Comment(commentID, articleID, authorID, parentCommentID, date, content);
        try (Connection conn = DB.connection()) {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO posted_comments (comment_id, article_id, author_id, parent_comment_id, date, comment_body) VALUES (?, ?, ?, ?, ?, ?)")){
                statement.setInt(1, comment.getCommentID());
                statement.setInt(2, comment.getArticleID());
                statement.setInt(3, comment.getAuthorID());
                statement.setInt(4, comment.getParentCommentID());
                statement.setDate(5, comment.getDate());
                statement.setString(6, comment.getContent());
                statement.executeUpdate();
                status = "Comment added successfully.";
                return status;
        }
        }catch (SQLException e){
            e.printStackTrace();

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return status;

    }

    public String deleteComment(MySQL DB, Comment comment) {
        String status = "Could not delete your comment at this time.";
        try (Connection conn = DB.connection()) {
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM posted_comments WHERE comment_id = ?")) {
                statement.setInt(1, comment.getCommentID());
                statement.executeUpdate();
                status = "Comment deleted successfully.";
                return status;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return status;
    }

    public String editComment(MySQL DB, int commentID, String newCommentContent){
        String status = "Comment could not be updated at this time.";
        try (Connection conn = DB.connection()){
            try (PreparedStatement statement = conn.prepareStatement("UPDATE posted_comments SET comment_body = ? WHERE comment_id = ?")){
                statement.setString(1, newCommentContent);
                statement.setInt(2, commentID);
                statement.executeUpdate();
                status = "Your comment has been updated.";
                return status;
            }

        }catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return status;
    }
}
