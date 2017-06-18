package backend.dao;

/**
 * Created by catherinedechalain on 1/06/17.
 */

import backend.db.MySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The CommentDAO class links the backend representation of a comment object with the database.
 *
 * The contained methods are called from the relevant servlets.
 *
 */

public class CommentDAO {


    /*---------------------------------------------------------------*/
    /*This method is used to access the first 'N' comments in date order.
    * The method takes an article_id and a number of comments.
    * It is called from the GetComments_IndividualArticle Servlet (which is accessed via AJAX)*/

    public static List<Comment> getTopLevelCommentsByArticle(MySQL DB, int articleID, int fromComments, int numComments) {

        /*Dummy list of comments to be returned if comments not found*/
        List<Comment> comments = null;

        try (Connection conn = DB.connection()) {

            try (PreparedStatement statement = conn.prepareStatement("SELECT comment_id, parent_comment_id, timestamp, comment_body, is_parent, username, firstname, lastname FROM posted_comments LEFT JOIN registered_users ON author_id = user_id WHERE article_id = ? AND parent_comment_id IS NULL ORDER BY TIMESTAMP LIMIT ? OFFSET ?;")) {

                statement.setInt(1, articleID);
                statement.setInt(2, numComments);
                statement.setInt(3, fromComments);

                try (ResultSet resultSet = statement.executeQuery()) {
                    comments = new ArrayList<>();

                    /*For each result returned from the query, assign the corresponding column values to comment objects and add the objects to the List of top level comments*/
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

        /*Returns a list of 'N' comments or a list of size = 0 if no comments are returned by a given query*/
        return comments;
    }


    /*---------------------------------------------------------------*/
    //Returns a list of all children comments for a given parent_comment_id
    //Linked with the 'Show_Replies' button

    public static List<Comment> getNestedComments(MySQL DB, int parentCommentID) {

        /*Dummy list of comments to be returned if comments not found*/
        List<Comment> comments = null;

        try (Connection conn = DB.connection()) {

            try (PreparedStatement statement = conn.prepareStatement("SELECT article_id, comment_id, author_id, timestamp, comment_body, is_parent, username, firstname, lastname FROM posted_comments LEFT JOIN registered_users ON author_id = user_id WHERE parent_comment_id = ?;")) {
                statement.setInt(1, parentCommentID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    comments = new ArrayList<>();

                    /*For each result returned from the query, assign the corresponding column values to comment objects and add the objects to the List of top level comments*/
                    processListOfComments(parentCommentID, comments, resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
         /*Returns a list of 'N' comments or a list of null list if no comments are returned by a given query*/
        return comments;
    }

       /*---------------------------------------------------------------*/
    //Returns a list of all comments from a particular author. Used for cases where a user wishes to delete their account

    public static List<Comment> getCommentsByAuthor(MySQL DB, int authorID) {

        /*Dummy list of comments to be returned if comments not found*/
        List<Comment> comments = null;

        try (Connection conn = DB.connection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM posted_comments WHERE author_id = ?;")) {
                statement.setInt(1, authorID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    comments = new ArrayList<>();

                    /*For each result returned from the query, assign the corresponding column values to comment objects and add the objects to the List of top level comments*/
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
        /*Returns a list of 'N' comments or a list of null list if no comments are returned by a given query*/
        return comments;
    }

       /*---------------------------------------------------------------*/
    /*Add a new comment to the database*/

    public static String addComment(MySQL DB, int authorID, int articleID, int parentCommentID, Timestamp timestamp, String content) {

        /*Setup a status string to be recognised in the corresponding servlet*/
        String status = "Could not add a comment at this time.";

        /*Create an comment instance to be used when passing values to the database*/
        Comment comment = new Comment(articleID, authorID, parentCommentID, timestamp, content);

        try (Connection conn = DB.connection()) {

            /*Case (A) If parentCommentID does not exist, add a comment with a 'NULL' parent to represent this*/
            if (parentCommentID == -1) {
                try (PreparedStatement statement = conn.prepareStatement("INSERT INTO posted_comments (article_id, author_id, parent_comment_id, timestamp, comment_body, is_parent) VALUES (?, ?, ?, ?, ?, ?)")) {
                    statement.setInt(1, comment.getArticleID());
                    statement.setInt(2, comment.getAuthorID());
                    statement.setNull(3, Types.INTEGER);
                    statement.setTimestamp(4, comment.getTimestamp());
                    statement.setString(5, comment.getContent());
                    statement.setBoolean(6, false);
                    statement.executeUpdate();

                    /*Update the status to be successful if no exceptioons are thrown*/
                    status = "Comment added successfully.";
                    return status;
                }


                /*Case (B) Else if parentCommentID does exist, add a comment with the parentCommentID*/
            } else {
                try (PreparedStatement statement = conn.prepareStatement("INSERT INTO posted_comments (article_id, author_id, parent_comment_id, timestamp, comment_body, is_parent) VALUES (?, ?, ?, ?, ?, ?)")) {
                    statement.setInt(1, comment.getArticleID());
                    statement.setInt(2, comment.getAuthorID());
                    statement.setInt(3, comment.getParentCommentID());
                    statement.setTimestamp(4, comment.getTimestamp());
                    statement.setString(5, comment.getContent());
                    statement.setBoolean(6, false);
                    statement.executeUpdate();

                    /*Update the status to be successful if no exceptioons are thrown*/
                    status = "Comment added successfully.";
                    return status;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*Return the status string representing the comment addition*/
        return status;
    }

       /*---------------------------------------------------------------*/
    /*Method for adding reply comments*/

    public static String addReplyComment(MySQL DB, int authorID, int articleID, int parentCommentID, Timestamp timestamp, String content) {

        /*Setup a status string to be recognised in the corresponding servlet*/
        String status = "Could not add a comment at this time.";

        /*Create an comment instance to be used when passing values to the database*/
        Comment comment = new Comment(articleID, authorID, parentCommentID, timestamp, content);

        try (Connection conn = DB.connection()) {

            /*Case (A) If parentCommentID does not exist, add a comment with a 'NULL' parent to represent this*/
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


                /*Case (B) Else if parentCommentID does exist, add a comment with the parentCommentID*/
            } else {
                try (PreparedStatement statement = conn.prepareStatement("INSERT INTO posted_comments (article_id, author_id, parent_comment_id, timestamp, comment_body, is_parent) VALUES (?, ?, ?, ?, ?, ?)")) {
                    statement.setInt(1, comment.getArticleID());
                    statement.setInt(2, comment.getAuthorID());
                    statement.setInt(3, comment.getParentCommentID());
                    statement.setTimestamp(4, comment.getTimestamp());
                    statement.setString(5, comment.getContent());
                    statement.setBoolean(6, false);
                    statement.executeUpdate();

                    /*child comment addition*/

                    /*Update the isParent status for the parent comment that has now been replied to*/
                    try (PreparedStatement statement1 = conn.prepareStatement("UPDATE posted_comments SET is_parent = ? WHERE comment_id = ?")) {
                        statement1.setBoolean(1, true);
                        statement1.setInt(2, parentCommentID);
                        statement1.executeUpdate();
                    }

                    /*Update the status accordingly if no exceptions are thrown*/
                    status = "Comment added successfully.";
                    return status;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*Return the status string representing the comment addition*/
        return status;

    }

    /*--------------------------------------------------------------------------*/
    /*Delete an individual (top-level) comment with no children*/

    public static String deleteComment(MySQL DB, int commentID) {

        /*Setup a status string to be recognised in the corresponding servlet*/
        String status = "Could not delete your comment at this time.";

        Integer parentCommentID = -1;

        try (Connection conn = DB.connection()) {

            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM posted_comments WHERE comment_id = ?;")) {
                statement.setInt(1, commentID);
                statement.executeUpdate();

                /*Update the status accordingly if no exceptions are thrown*/
                status = "Comment deleted successfully.";
                return status;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*Return the status string representing the comment addition*/
        return status;
    }


    /*--------------------------------------------------------------------------*/
    /*Delete an individual (nested) comment with a parent*/


    public static String deleteIndividualNestedComment(MySQL DB, int commentID, int parentCommentID) {

        /*Initialise a blank list of comments*/
        List<Comment> comments = null;

        /*Setup a status string to be recognised in the corresponding servlet*/
        String status = "Could not delete your comment at this time.";

        try (Connection conn = DB.connection()) {

            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM posted_comments WHERE comment_id = ?;")) {

                statement.setInt(1, commentID);
                statement.executeUpdate();
                /*Update the status if no exceptions are thrown*/
                status = "Single comment deleted successfully.";

                /*Get a list of all of the child comments with the same parent comment_id as the one being deleted*/
                try (PreparedStatement statement1 = conn.prepareStatement("SELECT article_id, comment_id, author_id, timestamp, comment_body, is_parent, username, firstname, lastname FROM posted_comments LEFT JOIN registered_users ON author_id = user_id WHERE parent_comment_id = ?;")) {
                    statement1.setInt(1, parentCommentID);
                    try (ResultSet resultSet = statement1.executeQuery()) {
                        comments = new ArrayList<>();
                        processListOfComments(parentCommentID, comments, resultSet);
                    }
                }

                /*If the list of child comments is of size = 0 the parent comment has no more children (other than the comment being deleted) and the parent comment is no longer (and we want to hide the show replies button)*/
                if (comments.size() == 0) {

                    try (PreparedStatement statement2 = conn.prepareStatement("UPDATE posted_comments SET is_parent = ? WHERE comment_id = ?")){
                        statement2.setBoolean(1, false);
                        statement2.setInt(2, parentCommentID);
                        statement2.executeUpdate();
                    }

                    /*If the list of child comments is of size = 1 the parent comment has no more children (other than the comment being deleted) and the parent comment is no longer (and we want to hide the show replies button)*/
                } else if (comments.size() == 1) {
                    Comment childComment = comments.get(0);
                    if (childComment.getCommentID() == commentID) {

                        try (PreparedStatement statement2 = conn.prepareStatement("UPDATE posted_comments SET is_parent = ? WHERE comment_id = ?")){
                            statement2.setBoolean(1, false);
                            statement2.setInt(2, parentCommentID);
                            statement2.executeUpdate();
                        }

                    }
                }

//                parent comment update completed
                /*Return the status string representing the comment addition*/
                return status;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*Return the status string representing the comment addition*/
        return status;
    }

    /*-------------------------------------------------------------*/
    /*Edit an individual comments content*/

    public static String editComment(MySQL DB, int commentID, String newCommentContent) {

        /*Setup a status string to be recognised in the corresponding servlet*/
        String status = "Comment could not be updated at this time.";


        try (Connection conn = DB.connection()) {

            try (PreparedStatement statement = conn.prepareStatement("UPDATE posted_comments SET comment_body = ? WHERE comment_id = ?")) {

                statement.setString(1, newCommentContent);
                statement.setInt(2, commentID);
                statement.executeUpdate();

                /*Update the status if no exceptions are thrown*/
                status = "Your comment has been updated.";
                 /*Return the status string representing the comment addition*/
                return status;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
         /*Return the status string representing the comment addition*/
        return status;
    }

    /*-------------------------------------------------------------*/
    /*Gets a specific comment by its auto incremented, unique id number*/

    public static Comment getCommentByID(MySQL DB, int commentID) {
        /*Initialise a dummy comment instance variable*/
        Comment comment = new Comment();

        try (Connection conn = DB.connection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM posted_comments WHERE comment_id = ?")) {
                statement.setInt(1, commentID);
                try (ResultSet resultSet = statement.executeQuery()) {

                    /*Fill out the dummy comment instance variable with the fields returned from the query*/
                    if (resultSet.next()) {
                        comment.setCommentID(commentID);
                        comment.setAuthorID(resultSet.getInt(resultSet.findColumn("author_id")));
                        comment.setArticleID(resultSet.getInt(resultSet.findColumn("article_id")));
                        comment.setTimestamp(resultSet.getTimestamp(resultSet.findColumn("timestamp")));
                        comment.setContent(resultSet.getString(resultSet.findColumn("comment_body")));
                        comment.setParentCommentID(resultSet.getInt(resultSet.findColumn("parent_comment_id")));
                        comment.setIsParent(resultSet.getBoolean(resultSet.findColumn("is_parent")));
                    } else {
//                        Could not find specified comment
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*Return the comment instance with the variables set from the query (or null if not found in the database)*/
        return comment;
    }

       /*-------------------------------------------------------------*/
    /*Updates a given comment to be a parent (i.e. isParent = true)*/

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

    /*-------------------------------------------------------------*/
    /*Updates a given comment to not be a parent (i.e. isParent = false)*/

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

        /*-------------------------------------------------------------*/
        /* Processes a table of comments from the MySQL database into a List of comment instances*/

    private static void processListOfComments(int parentCommentID, List<Comment> comments, ResultSet resultSet) throws SQLException {
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
