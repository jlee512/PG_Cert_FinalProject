<%@ page import="login.system.dao.Article" %>
<%@ page import="login.system.servlets.ViewArticle" %><%--
  Created by IntelliJ IDEA.
  User: cbla080
  Date: 3/06/2017
  Time: 12:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:if test="${loginStatus != 'active'}">
        <c:redirect url="Login"/>
    </c:if>
    <title>${requestScope.article.article_title}</title>
    <script src="Javascript/show_comments.js"></script>
</head>
<body>
<h1>${requestScope.article.article_title}</h1>
<br>
<h3>${requestScope.username}</h3>
<br>
<p>${requestScope.article.article_body}</p>
<br>
<em>${requestScope.article.article_date}</em>
<hr>
<a href="AddComment?article_id=${requestScope.article.article_id}">Add New Comment</a>
<c:forEach var="comment" items="${requestScope.commentList}">
    <div>
        <p>${comment.content}</p>
        <a href="AddComment?article_id=${requestScope.article.article_id}&parentComment_id=${comment.commentID}">Reply</a>
        <c:if test="${comment.isParent}">
            <button type="button" class="show_replies" value="${comment.commentID}">Show Replies</button>
        </c:if>
    </div>
</c:forEach>
</body>

</html>
<%--Need to set up a function to retrieve replies if Show Replies button is clicked--%>