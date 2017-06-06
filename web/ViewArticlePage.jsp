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
<%@include file="HeadStylingLinks.jsp"%>
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
<a href="AddComment?article_id=${requestScope.article.article_id}" class="btn btn-default">Add New Comment</a>
<c:forEach var="comment" items="${requestScope.commentList}">
    <div class="panel-group">
    <div class="panel panel-info">
        <div class="panel-heading">${comment.authorUsername}</div>
        <div class="panel-body">${comment.content}</div>
        <a href="AddComment?article_id=${requestScope.article.article_id}&parentComment_id=${comment.commentID}" class="btn btn-default">Reply</a>
        <c:if test="${comment.isParent}">
            <button type="button" class="show_replies btn btn-default" value="${comment.commentID}">Show Replies</button>
        </c:if>
    </div>
    </div>
</c:forEach>
<%@include file="BodyStylingLinks.jsp"%>
</body>
</html>
<%--Need to set up a function to retrieve replies if Show Replies button is clicked--%>