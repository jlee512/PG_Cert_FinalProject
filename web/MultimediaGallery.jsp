<%--
  Created by IntelliJ IDEA.
  User: Tammy
  Date: 12/06/2017
  Time: 5:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="login.system.dao.User" %>

<%
    /*Prevents cache access of content/changepassword/logout pages*/
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server

    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance

    response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"

    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
    User user = (User) session.getAttribute("userDetails");


%>
<html>
<head>
    <%@include file="HeadStylingLinks.jsp" %>
    <link rel="stylesheet" type="text/css" href="CSS/loader_animation.css">
    <title>Gallery</title>
</head>
<body>
<c:choose>
    <c:when test="${loginStatus == 'active'}">
<%@include file="Navbar.jsp" %>




        <div class="news_feed">
            <%--Articles are inserted here via AJAX request--%>
        </div>

    <div class="loader-wrapper" style="text-align: center;">
        <div class="loader" style="display: inline-block;"></div>
    </div>
    <br class="loader-wrapper">
    <br class="loader-wrapper">
    <div class="loaded-wrapper" style="text-align: center;">
        <div id="loaded1" style="display: inline-block;"></div>
        <div id="loaded2" style="display: inline-block;"></div>
        <div id="loaded3" style="display: inline-block;"></div>
        <div id="loaded4" style="display: inline-block;"></div>
    </div>
    <br class="loaded-wrapper">
    <br class="loaded-wrapper">





<%@ include file="BodyStylingLinks.jsp" %>
<script type="application/javascript" src="Javascript/multimedia_display.js"></script>
    </c:when>
    <%--When user is not logged in, if content page is accessed, redirect to the login page--%>
    <c:otherwise>

        <c:redirect url="Login"/>

    </c:otherwise>
</c:choose>
</body>




</html>