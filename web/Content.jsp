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
    <meta name="google-signin-client_id"
          content="17619298298-hlb3n0ra5pkquu73jbs8sir2m5i4b4b8.apps.googleusercontent.com">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script>
        window.onLoadCallback = function () {
            gapi.load('auth2', function () {

                gapi.auth2.init();

            });
        }
    </script>
    <script>
        function signOut() {
            var auth2 = gapi.auth2.getAuthInstance();
            auth2.signOut().then(function () {
                console.log('User signed out.');
            });
        }
        setTimeout(fade_out, 3000);

        function fade_out() {

            $('.not-found').fadeOut().empty();

        }
    </script>

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

        .view_comments:hover {
            /*Override default hover function*/
            box-shadow: 0 2px 5px 0 rgba(0, 0, 0, .16), 0 2px 10px 0 rgba(0, 0, 0, .12) !important;
        }

    </style>

    <link rel="stylesheet" type="text/css" href="CSS/loader_animation.css">

    <link rel="shortcut icon" type="image/png" href="Multimedia/favicon.png">

</head>

<body>
<%--If user profile has been activated with a successful login, progress with presenting dynamic content--%>
<c:choose>
    <c:when test="${loginStatus == 'active'}">
        <%@include file="Navbar.jsp" %>

        <%--Show a message if redirected from failed GET request--%>
        <c:if test="${not empty param.notFound}">
            <%--When the article is successfully deleted--%>
            <c:choose>
                <c:when test="${param.notFound}">
                    <div class="not-found card" style="text-align: center; background-color: #FFFACD;">
                        <div class="card-header">
                            <h3>Sorry, we couldn't find that page.</h3>
                        </div>
                    </div>
                </c:when>
            </c:choose>
        </c:if>

        <p class="text-center">Welcome <strong><a href="ProfilePage?username=${sessionScope.userDetails.username}"
                                                  style="color: #f9a825;">${sessionScope.userDetails.username}</a></strong>!
            Good to see you</p>

        <div class="sorting-button-container" style="text-align: center;">
            <a id="title-sort-button" class="btn btn-sm btn-rounded" style="background-color: #d2f7ba; color:black !important;">Sort by Title</a>
            <a id="date-sort-button" class="btn btn-sm btn-rounded" style="background-color: #d2f7ba; color:black !important;">Sort by Date</a>
            <a id="author-sort-button" class="btn btn-sm btn-rounded" style="background-color: #d2f7ba; color:black !important;">Sort by Author</a>
        </div>

        <div class="search-bar-container" style="text-align: center">
            <input type="search" name="searchbar" id="searchbar" style="width: 30%">
            <a id="search-button" class="btn btn-sm btn-rounded" style="background-color: #d2f7ba; color:black !important;"><i class="fa fa-search" aria-hidden="true"></i></a>
        </div>


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