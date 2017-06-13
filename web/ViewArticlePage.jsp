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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="HeadStylingLinks.jsp"%>
<html>
<head>
    <c:if test="${loginStatus != 'active'}">
        <c:redirect url="Login"/>
    </c:if>
    <title>${requestScope.article.article_title}</title>
    <link rel="stylesheet" type="text/css" href="CSS/loader_animation.css">
    <link rel="stylesheet" type="text/css" href="CSS/comment_styling.css">
</head>
<body>
<%@include file="Navbar.jsp" %>
<%----------------------------------------Article--------------------------------------------------%>
<h1 class="display-4">${requestScope.article.article_title}</h1>
<br>
<h3 id="author">Published by: <strong><a href="PublicProfile?username=${requestScope.article.author_username}" style="color: #f9a825;">${requestScope.article.author_username}</a></strong></h3>
<br>

<p style="white-space: pre-wrap;">${requestScope.article.article_body}</p>
<br>

<em>Published: ${requestScope.date}</em>
<hr>

<%---------------------------------------Comments-------------------------------------------------%>
<button class="btn btn-default add-comment-button">Add New Comment</button>

<div class="top_level_comment_feed">
       <%--Top level comments are dropped into here from AJAX calls--%>
</div>


<%--Loader animation and bottom of comments symbol--%>
<div class="loader-wrapper" style="margin-left: 3%;">
    <div class="loader" style="display: inline-block;"></div>
</div>
<div class="loaded-wrapper">
    <div id="loaded1" style="display: inline-block;"></div>
    <div id="loaded2" style="display: inline-block;"></div>
    <div id="loaded3" style="display: inline-block;"></div>
    <div id="loaded4" style="display: inline-block;"></div>
</div>


<div id="userdetails" style="display: none">${sessionScope.userDetails.username}</div>
<div id="articleid" style="display: none">${requestScope.article.article_id}</div>
<script src="Javascript/show_comment_replies.js"></script>
<script src="Javascript/show_top_level_comments.js"></script>
<script src="Javascript/add_comment_form.js"></script>
<script src="Javascript/reply_to_comment_form.js"></script>
<script src="Javascript/edit_comment_form.js"></script>
<%@include file="BodyStylingLinks.jsp"%>

</body>
</html>
<%--Need to set up a function to retrieve replies if Show Replies button is clicked--%>