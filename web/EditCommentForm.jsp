<%--
  Created by IntelliJ IDEA.
  User: cbla080
  Date: 7/06/2017
  Time: 3:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Comment</title>
</head>
<body>
<form action="EditComment" method="post">
    <label for="comment_body">Comment:</label>
    <textarea rows="4" cols="50" name="comment_body" id="comment_body">${param.comment_body}</textarea>
    <input type="hidden" name="comment_id" value="${param.comment_id}">
    <input type="hidden" name="article_id" value="${param.article_id}">
    <input type="submit" name="submit" value="Post Comment">
</form>
</body>
</html>
