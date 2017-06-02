<%--
  Created by IntelliJ IDEA.
  User: jlee512
  Date: 29/05/2017
  Time: 7:59 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="login.system.dao.User" %>
<%
    /*Prevents cache access of content/changepassword/logout pages*/
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
    User user = (User)session.getAttribute("userDetails");
    if (user == null) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Login");
        dispatcher.forward(request, response);
    }
%>
<html>
<head>
    <title>See you next time!</title>
</head>

<style type="text/css">

    p {
        top: 0%;
        left: 0%;
        margin-top: 1em;
    }

    #logoutButtonContainer {
        padding-left: 1em;
        padding-right: 1em;
        background-color: lightgray;
        text-align: center;
        margin-left: 2em;
        transition-timing-function: ease;
        transition-duration: 2s;
    }

    #logoutButtonContainer:hover {
        background-color: whitesmoke;
        transition-timing-function: ease;
        transition-duration: 2s;
    }

</style>

<body>

<%--If user has already logged out, redirect to login screen with message--%>
<c:choose>
    <c:when test="${sessionScope.loginStatus != null}">
        <p>See you next time <span id="logoutButtonContainer"><a href="/LogoutAttempt">Logout</a></span></p>
    </c:when>
    <c:otherwise>
        <c:redirect url="/Login?loginStatus=loggedOut" />
    </c:otherwise>
</c:choose>


</body>
</html>
