<%--
  Created by IntelliJ IDEA.
  User: jlee512
  Date: 29/05/2017
  Time: 7:59 AM
  To change this template use File | Settings | File Templates.
--%>

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

    /*Read in adventure quotes.txt file and extract a random quote*/
    String quotes_file_path = request.getServletContext().getRealPath("Text_Files/adventure_quotes.txt");

    /*Initialise quote and author strings as well as the numQuotes and randomQuote variables*/
    String quote = "";
    String author = "";
    int numQuotes = 6;
    int randomQuote = (int) (Math.random() * (numQuotes));

//    File quotes_file = new File(quotes_file_path);
//    try (BufferedReader br = new BufferedReader(new FileReader(quotes_file))) {
//
//        for (int i = 0; i < numQuotes; i++) {
//            if (i == (randomQuote)) {
//                quote = br.readLine();
//                author = br.readLine();
//            } else {
//                br.readLine();
//                br.readLine();
//            }
//        }
//
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
%>

<html>
<head>

    <title>Your Account</title>
    <%@ include file="HeadStylingLinks.jsp" %>
</head>

<style type="text/css">

    #logoutButton {
        position: absolute;
        top: 0%;
        right: 0%;
        margin-top: 1em;
        margin-right: 2em;
    }

    p {
        top: 0%;
        left: 0%;
        margin-top: 1em;
    }

    .footer {
        position: absolute;
        right: 0;
        bottom: 0;
        left: 0;
        text-align: right;
    }

    .loader {
        border: 7px solid #f3f3f3;
        border-radius: 50%;
        border-top: 7px solid #00acc1;
        border-right: 7px solid #b2ebf2 ;
        border-bottom: 7px solid #ffd54f ;
        border-left: 7px solid #f9a825 ;
        width: 50px;
        height: 50px;
        -webkit-animation: spin 2s linear infinite;
        -moz-animation: spin 2s linear infinite;
        animation: spin 2s linear infinite;
    }

    #loaded1 {
        background-color: #00acc1;
        height: 1%;
        width: 10%;
    }

    #loaded2 {
        background-color: #b2ebf2;
        height: 1%;
        width: 10%;
    }

    #loaded3 {
        background-color: #ffd54f;
        height: 1%;
        width: 10%;
    }

    #loaded4 {
        background-color: #f9a825;
        height: 1%;
        width: 10%;
    }

    @-webkit-keyframes spin {
        0% { -webkit-transform: rotate(0deg); }
        100% { -webkit-transform: rotate(360deg); }
    }

    @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
    }

</style>

<link rel="shortcut icon" type="image/png" href="/Multimedia/favicon.png">

<body>

<%--If user profile has been activated with a successful login, progress with presenting dynamic content--%>
<c:choose>
    <c:when test="${loginStatus == 'active'}">
        <%@include file="Navbar.jsp" %>

        <p class="text-center">Welcome <strong>${sessionScope.userDetails.username}</strong>! Good to see you</p>


        <div class="news_feed">
                <%--Articles should be dropped into here from AJAX calls--%>
        </div>

        <div class="loader-wrapper" style="text-align: center;">
            <div class="loader" style="display: inline-block;"></div>
        </div>
        <div class="loaded-wrapper" style="text-align: center;">
            <div id="loaded1" style="display: inline-block;"></div>
            <div id="loaded2" style="display: inline-block;"></div>
            <div id="loaded3" style="display: inline-block;"></div>
            <div id="loaded4" style="display: inline-block;"></div>
        </div>

    </c:when>
    <%--When user is not logged in, if content page is accessed, redirect to the login page--%>
    <c:otherwise>

        <c:redirect url="Login"/>

    </c:otherwise>
</c:choose>

<script src="Javascript/article_display.js"></script>

<%@ include file="BodyStylingLinks.jsp" %>

</body>
</html>