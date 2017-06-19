<%@ page import="backend.dao.Article" %>
<%@ page import="backend.servlets.ViewArticle" %>
<%@ page import="backend.dao.User" %><%--
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
    <%
        /*Prevents cache access of content/changepassword/logout pages*/
        response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
        response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
        response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
        response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
        User user = (User) session.getAttribute("userDetails");
        if (user == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("Login");
            dispatcher.forward(request, response);
        }
    %>
    <c:if test="${sessionScope.loginStatus != 'active'}">
        <c:redirect url="Login"/>
    </c:if>
    <title>${requestScope.article.article_title}</title>
    <link rel="stylesheet" type="text/css" href="CSS/loader_animation.css">
    <link rel="stylesheet" type="text/css" href="CSS/comment_styling.css">
</head>
<body>
<%@include file="Navbar.jsp" %>
<%----------------------------------------Article--------------------------------------------------%>

<%--Article title is recieved from the ViewArticle servlet--%>
<h1 class="display-4">${requestScope.article.article_title}</h1>
<br>
<%--Author username is recieved from the ViewArticle servlet and links to their public profile--%>
<h3 id="author">Published by: <strong><a href="PublicProfile?username=${requestScope.article.author_username}" style="color: #f9a825;">${requestScope.article.author_username}</a></strong></h3>
<br>

<%--Article body recieved from ViewArticle servlet--%>
<p style="white-space: pre-wrap;">${requestScope.article.article_body}</p>
<br>

<%--Article date received from ViewArticle servlet--%>
<em>Published: ${requestScope.date}</em>
<hr>

<%--------------------------------------Multimedia-------------------------------------------------%>

<div class="row">
<div class="uploadedPhotos col-sm-6">
    <%--Photos are inserted here via AJAX request--%>
</div>
<div class="uploadedVideos col-sm-6">
    <%--Videos are inserted here via AJAX request--%>
</div>
</div>

<%---------------------------------------Comments-------------------------------------------------%>
<button class="btn btn-default add-comment-button">Add New Comment</button>

<div class="top_level_comment_feed">
       <%--Top level comments are dropped into here from AJAX calls--%>
</div>


<%--Loader animation and bottom of comments symbol--%>
<div class="loader-wrapper" style="margin-left: 3%; text-align: center;">
    <div class="loader" style="display: inline-block;"></div>
</div>
<div class="loaded-wrapper" style="text-align: center;">
    <div id="loaded1" style="display: inline-block;"></div>
    <div id="loaded2" style="display: inline-block;"></div>
    <div id="loaded3" style="display: inline-block;"></div>
    <div id="loaded4" style="display: inline-block;"></div>
</div>

<%--Store the current user's username for use in JavaScript--%>
<div id="userdetails" style="display: none">${sessionScope.userDetails.username}</div>

<%---------------------------------------External JavaScript-------------------------------------------------%>
<script src="Javascript/show_comment_replies.js"></script>
<script src="Javascript/show_top_level_comments.js"></script>
<script src="Javascript/add_comment_form.js"></script>
<script src="Javascript/reply_to_comment_form.js"></script>
<script src="Javascript/edit_comment_form.js"></script>
<script type="application/javascript" src="Javascript/view_article_multimedia.js"></script>

<%--Include Bootstrap JS, jQuery and jQuery UI--%>
<%@include file="BodyStylingLinks.jsp"%>

</body>
</html>