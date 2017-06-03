<%--
  Created by IntelliJ IDEA.
  User: cbla080
  Date: 2/06/2017
  Time: 1:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:if test="${loginStatus != 'active'}">
        <c:redirect url="/Login"/>
    </c:if>
    <title>Add Comment</title>
</head>

<body>
<form action="/AddCommentAttempt" method="post">
    <label for="comment_body">Comment:</label>
    <textarea rows="4" cols="50" name="comment_body" id="comment_body"></textarea>
    <input type="hidden" name="username" value="${sessionScope.userDetails.username}">
    <input type="hidden" name="article_id" value="${param.article_id}">
    <input type="hidden" name="parentComment_id" value="${param.parentComment_id}">
    <input type="submit" name="submit" value="Post Comment">
</form>
</body>
</html>
