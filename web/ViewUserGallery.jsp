<%--
  Created by IntelliJ IDEA.
  User: julia
  Date: 14/06/2017
  Time: 10:45 PM
  To change this template use File | Settings | File Templates.
--%>

<%--Import JSTL Standard Tag Library--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="backend.dao.User" %>

<%--Prevents cache access of content/changepassword/logout pages--%>
<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server

    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance

    response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"

    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
    User user = (User) session.getAttribute("userDetails");


%>

<html>

<head>
    <%@include file="HeadStylingLinks.jsp" %>

    <%--Import Loader CSS Stylesheet--%>
    <link rel="stylesheet" type="text/css" href="CSS/loader_animation.css">

    <title>User Gallery</title>
</head>

<body>
<c:choose>
    <c:when test="${loginStatus == 'active'}">
        <%@include file="Navbar.jsp" %>




        <div class="uploadedPhotos col-sm-6">
                <%--Photos are inserted here via AJAX request--%>
        </div>
        <div class="uploadedVideos col-sm-6">
                <%--Videos are inserted here via AJAX request--%>
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
        <script type="application/javascript" src="Javascript/view_user_multimedia.js"></script>
    </c:when>
    <%--When user is not logged in, if content page is accessed, redirect to the login page--%>
    <c:otherwise>

        <c:redirect url="Login"/>

    </c:otherwise>
</c:choose>
</body>




</html>
