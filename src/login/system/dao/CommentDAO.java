package login.system.dao;

/**
 * Created by catherinedechalain on 1/06/17.
 */

import login.system.db.MySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {


    //Returns a list of comments for a particular article.
    public static List<Comment> getCommentsByArticle(MySQL DB, int articleID) {
        List<Comment> comments = null;
        //Will be updated to our database details.
        try (Connection conn = DB.connection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM posted_comments WHERE article_id = ?;")) {
                statement.setInt(1, articleID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    comments = new ArrayList<>();
                    while (resultSet.next()) {
                        Comment comment = new Comment();
                        comment.setArticleID(articleID);
                        comment.setAuthorID(resultSet.getInt(resultSet.findColumn("author_id")));
                        comment.setCommentID(resultSet.getInt(resultSet.findColumn("comment_id")));
                        comment.setParentCommentID(resultSet.getInt(resultSet.findColumn("parent_comment_id")));
                        comment.setTimestamp(resultSet.getTimestamp(resultSet.findColumn("timestamp")));
                        comment.setContent(resultSet.getString(resultSet.findColumn("comment_body")));
                        comment.setIsParent(resultSet.getBoolean(resultSet.findColumn("is_parent")));
                        if (comment.getParentCommentID() == 0) {
                            comments.add(comment);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return comments;
    }

    public static List<Comment> getTopLevelCommentsByArticle(MySQL DB, int articleID, int fromComments, int numComments) {
        List<Comment> comments = null;
        System.out.println(numComments);
        //Will be updated to our database details.
        try (Connection conn = DB.connection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT comment_id, parent_comment_id, timestamp, comment_body, is_parent, username, firstname, lastname FROM posted_comments LEFT JOIN registered_users ON author_id = user_id WHERE article_id = ? AND parent_comment_id IS NULL ORDER BY TIMESTAMP LIMIT ? OFFSET ?;")) {
                statement.setInt(1, articleID);
                statement.setInt(2, numComments);
                statement.setInt(3, fromComments);

                try (ResultSet resultSet = statement.executeQuery()) {
                    comments = new ArrayList<>();
                    while (resultSet.next()) {
                        Comment comment = new Comment();
                        comment.setArticleID(articleID);
                        comment.setCommentID(resultSet.getInt(resultSet.findColumn("comment_id")));
                        comment.setParentCommentID(resultSet.getInt(resultSet.findColumn("parent_comment_id")));
                        comment.setTimestamp(resultSet.getTimestamp(resultSet.findColumn("timestamp")));
                        comment.setContent(resultSet.getString(resultSet.findColumn("comment_body")));
                        comment.setIsParent(resultSet.getBoolean(resultSet.findColumn("is_parent")));
                        comment.setAuthor_username(resultSet.getString("username"));
                        comment.setAuthor_firstname(resultSet.getString("firstname"));
                        comment.setAuthor_lastname(resultSet.getString("lastname"));
                        comments.add(comment);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return comments;
    }

    //Returns a list of all children comments of a particular comment.
    public static List<Comment> getNestedComments(MySQL DB, int parentCommentID) {
        List<Comment> comments = null;
        //Will be updated to our database details.
        try (Connection conn = DB.connection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT article_id, comment_id, author_id, timestamp, comment_body, is_parent, username, firstname, lastname FROM posted_comments LEFT JOIN registered_users ON author_id = user_id WHERE parent_comment_id = ?;")) {
                statement.setInt(1, parentCommentID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    comments = new ArrayList<>();
                    while (resultSet.next()) {
                        Comment comment = new Comment();
                        comment.setParentCommentID(parentCommentID);
                        comment.setArticleID(resultSet.getInt(resultSet.findColumn("article_id")));
                        comment.setCommentID(resultSet.getInt(resultSet.findColumn("comment_id")));
                        comment.setAuthorID(resultSet.getInt(resultSet.findColumn("author_id")));
                        comment.setTimestamp(resultSet.getTimestamp(resultSet.findColumn("timestamp")));
                        comment.setContent(resultSet.getString(resultSet.findColumn("comment_body")));
                        comment.setIsParent(resultSet.getBoolean(resultSet.findColumn("is_parent")));
                        comment.setAuthor_username(resultSet.getString(resultSet.findColumn("username")));
                        comment.setAuthor_firstname(resultSet.getString(resultSet.findColumn("firstname")));
                        comment.setAuthor_lastname(resultSet.getString(resultSet.findColumn("lastname")));
                        comments.add(comment);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return comments;
    }

    //Returns a list of all comments by a particular author.
    public static List<Comment> getCommentsByAuthor(MySQL DB, int authorID) {
        List<Comment> comments = null;
        //Will be updated to our database details.
        try (Connection conn = DB.connection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM posted_comments WHERE author_id = ?;")) {
                statement.setInt(1, authorID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    comments = new ArrayList<>();
                    while (resultSet.next()) {
                        Comment comment = new Comment();
                        comment.setAuthorID(authorID);
                        comment.setArticleID(resultSet.getInt(resultSet.findColumn("article_id")));
                        comment.setCommentID(resultSet.getInt(resultSet.findColumn("comment_id")));
                        comment.setTimestamp(resultSet.getTimestamp(resultSet.findColumn("timestamp")));
                        comment.setContent(resultSet.getString(resultSet.findColumn("comment_body")));
                        comment.setParentCommentID(resultSet.getInt(resultSet.findColumn("parent_comment_id")));
                        comment.setIsParent(resultSet.getBoolean(resultSet.findColumn("is_parent")));
                        comments.add(comment);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public static String addComment(MySQL DB, int authorID, int articleID, int parentCommentID, Timestamp timestamp, String content) {
        String status = "Could not add a comment at this time.";
        Comment comment = new Comment(articleID, authorID, parentCommentID, timestamp, content);
        try (Connection conn = DB.connection()) {
            /*If parentCommentID does not exist, add a comment with a 'NULL' parent*/
            if (parentCommentID == -1) {
                try (PreparedStatement statement = conn.prepareStatement("INSERT INTO posted_comments (article_id, author_id, parent_comment_id, timestamp, comment_body, is_parent) VALUES (?, ?, ?, ?, ?, ?)")) {
                    statement.setInt(1, comment.getArticleID());
                    statement.setInt(2, comment.getAuthorID());
                    statement.setNull(3, Types.INTEGER);
                    statement.setTimestamp(4, comment.getTimestamp());
                    statement.setString(5, comment.getContent());
                    statement.setBoolean(6, false);
                    statement.executeUpdate();
                    status = "Comment added successfully.";
                    return status;
                }
                /*Else if parentCommentID does exist, add a comment with the parentCommentID*/
            } else {
                try (PreparedStatement statement = conn.prepareStatement("INSERT INTO posted_comments (article_id, author_id, parent_comment_id, timestamp, comment_body, is_parent) VALUES (?, ?, ?, ?, ?, ?)")) {
                    statement.setInt(1, comment.getArticleID());
                    statement.setInt(2, comment.getAuthorID());
                    statement.setInt(3, comment.getParentCommentID());
                    statement.setTimestamp(4, comment.getTimestamp());
                    statement.setString(5, comment.getContent());
                    statement.setBoolean(6, false);
                    statement.executeUpdate();
                    status = "Comment added successfully.";
                    return status;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return status;

    }

    /*--------------Adding reply comments - minimise data connections ----------*/

    public static String addReplyComment(MySQL DB, int authorID, int articleID, int parentCommentID, Timestamp timestamp, String content) {
        String status = "Could not add a comment at this time.";
        Comment comment = new Comment(articleID, authorID, parentCommentID, timestamp, content);
        try (Connection conn = DB.connection()) {
            /*If parentCommentID does not exist, add a comment with a 'NULL' parent*/
            if (parentCommentID == -1) {
                try (PreparedStatement statement = conn.prepareStatement("INSERT INTO posted_comments (article_id, author_id, parent_comment_id, timestamp, comment_body, is_parent) VALUES (?, ?, ?, ?, ?, ?)")) {
                    statement.setInt(1, comment.getArticleID());
                    statement.setInt(2, comment.getAuthorID());
                    statement.setNull(3, Types.INTEGER);
                    statement.setTimestamp(4, comment.getTimestamp());
                    statement.setString(5, comment.getContent());
                    statement.setBoolean(6, false);
                    statement.executeUpdate();

                    System.out.println("non-child comment addition");

                    status = "Comment added successfully.";
                    return status;
                }
                /*Else if parentCommentID does exist, add a comment with the parentCommentID*/
            } else {
                try (PreparedStatement statement = conn.prepareStatement("INSERT INTO posted_comments (article_id, author_id, parent_comment_id, timestamp, comment_body, is_parent) VALUES (?, ?, ?, ?, ?, ?)")) {
                    statement.setInt(1, comment.getArticleID());
                    statement.setInt(2, comment.getAuthorID());
                    statement.setInt(3, comment.getParentCommentID());
                    statement.setTimestamp(4, comment.getTimestamp());
                    statement.setString(5, comment.getContent());
                    statement.setBoolean(6, false);
                    statement.executeUpdate();

                    System.out.println("child comment addition");

                    System.out.println("updating parent status");
                    try (PreparedStatement statement1 = conn.prepareStatement("UPDATE posted_comments SET is_parent = ? WHERE comment_id = ?")) {
                        statement1.setBoolean(1, true);
                        statement1.setInt(2, parentCommentID);
                        statement1.executeUpdate();
                    }

                    status = "Comment added successfully.";
                    return status;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return status;

    }

    /*--------------------------------------------------------------------------*/

    public static String deleteComment(MySQL DB, int commentID) {
        String status = "Could not delete your comment at this time.";
        Integer parentCommentID = -1;
        try (Connection conn = DB.connection()) {
            /*Get parent comment_id if it exists and set the parent comment_id's isParent boolean to null*/
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM posted_comments WHERE comment_id = ?;")) {
                statement.setInt(1, commentID);
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

    /*----------Individual Comment Delete Update-------------------*/

    public static String deleteIndividualNestedComment(MySQL DB, int commentID, int parentCommentID) {

        /*Initialise variables as required*/
        List<Comment> comments = null;

        String status = "Could not delete your comment at this time.";
        try (Connection conn = DB.connection()) {

            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM posted_comments WHERE comment_id = ?;")) {

                statement.setInt(1, commentID);
                statement.executeUpdate();
                status = "Single comment deleted successfully.";

                try (PreparedStatement statement1 = conn.prepareStatement("SELECT article_id, comment_id, author_id, timestamp, comment_body, is_parent, username, firstname, lastname FROM posted_comments LEFT JOIN registered_users ON author_id = user_id WHERE parent_comment_id = ?;")) {
                    statement1.setInt(1, parentCommentID);
                    try (ResultSet resultSet = statement1.executeQuery()) {
                        comments = new ArrayList<>();
                        while (resultSet.next()) {
                            Comment comment = new Comment();
                            comment.setParentCommentID(parentCommentID);
                            comment.setArticleID(resultSet.getInt(resultSet.findColumn("article_id")));
                            comment.setCommentID(resultSet.getInt(resultSet.findColumn("comment_id")));
                            comment.setAuthorID(resultSet.getInt(resultSet.findColumn("author_id")));
                            comment.setTimestamp(resultSet.getTimestamp(resultSet.findColumn("timestamp")));
                            comment.setContent(resultSet.getString(resultSet.findColumn("comment_body")));
                            comment.setIsParent(resultSet.getBoolean(resultSet.findColumn("is_parent")));
                            comment.setAuthor_username(resultSet.getString(resultSet.findColumn("username")));
                            comment.setAuthor_firstname(resultSet.getString(resultSet.findColumn("firstname")));
                            comment.setAuthor_lastname(resultSet.getString(resultSet.findColumn("lastname")));
                            comments.add(comment);
                        }
                    }
                }

                if (comments.size() == 0) {
                    System.out.println("Parent comment has no other children, updating status");
                    /*If comments size is zero, there are no other child comments so update parent status to false*/
                    try (PreparedStatement statement2 = conn.prepareStatement("UPDATE posted_comments SET is_parent = ? WHERE comment_id = ?")){
                        statement2.setBoolean(1, false);
                        statement2.setInt(2, parentCommentID);
                        statement2.executeUpdate();
                    }

                } else if (comments.size() == 1) {
                    Comment childComment = comments.get(0);
                    if (childComment.getCommentID() == commentID) {
                        System.out.println("Parent comments only child is the comment being deleted, updating status");
                        /*Else if comments size is one and the comment being deleted is the only child, update the parent status to false*/
                        try (PreparedStatement statement2 = conn.prepareStatement("UPDATE posted_comments SET is_parent = ? WHERE comment_id = ?")){
                            statement2.setBoolean(1, false);
                            statement2.setInt(2, parentCommentID);
                            statement2.executeUpdate();
                        }

                    }
                }

                System.out.println("parent comment update completed");

                return status;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return status;
    }

    /*-------------------------------------------------------------*/

    public static String editComment(MySQL DB, int commentID, String newCommentContent) {
        String status = "Comment could not be updated at this time.";
        try (Connection conn = DB.connection()) {
            try (PreparedStatement statement = conn.prepareStatement("UPDATE posted_comments SET comment_body = ? WHERE comment_id = ?")) {
                statement.setString(1, newCommentContent);
                statement.setInt(2, commentID);
                statement.executeUpdate();
                status = "Your comment has been updated.";
                return status;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static Comment getCommentByID(MySQL DB, int commentID) {
        Comment comment = new Comment();
        try (Connection conn = DB.connection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM posted_comments WHERE comment_id = ?")) {
                statement.setInt(1, commentID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        comment.setCommentID(commentID);
                        comment.setAuthorID(resultSet.getInt(resultSet.findColumn("author_id")));
                        comment.setArticleID(resultSet.getInt(resultSet.findColumn("article_id")));
                        comment.setTimestamp(resultSet.getTimestamp(resultSet.findColumn("timestamp")));
                        comment.setContent(resultSet.getString(resultSet.findColumn("comment_body")));
                        comment.setParentCommentID(resultSet.getInt(resultSet.findColumn("parent_comment_id")));
                        comment.setIsParent(resultSet.getBoolean(resultSet.findColumn("is_parent")));
                    } else {
                        System.out.println("Could not find specified comment.");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return comment;
    }

    public static void setCommentAsParent(MySQL DB, int parentCommentID) {
        try (Connection conn = DB.connection()) {
            try (PreparedStatement statement = conn.prepareStatement("UPDATE posted_comments SET is_parent = ? WHERE comment_id = ?")) {
                statement.setBoolean(1, true);
                statement.setInt(2, parentCommentID);
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setCommentNotParent(MySQL DB, int parentCommentID){
        try (Connection conn = DB.connection()){
            try (PreparedStatement statement = conn.prepareStatement("UPDATE posted_comments SET is_parent = ? WHERE comment_id = ?")){
                statement.setBoolean(1, false);
                statement.setInt(2, parentCommentID);
                statement.executeUpdate();
            }

        }catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
