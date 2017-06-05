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
<<<<<<< HEAD

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
//
//        if (user == null) {
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/Login");
//            dispatcher.forward(request, response);
//        }
//    }
=======
    if (user == null) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Login");
        dispatcher.forward(request, response);
    }
>>>>>>> origin/master
%>

<html>
<head>

    <title>Your Account</title>
<<<<<<< HEAD

    <%@ include file="HeadStylingLinks.jsp" %>

=======
    <%@ include file="HeadStylingLinks.jsp" %>
>>>>>>> origin/master
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


</style>

<link rel="shortcut icon" type="image/png" href="/Multimedia/favicon.png">

<body>
<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="Content.jsp">Homepage</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="ProfilePage.jsp" style="font-size: 18px">My profile</a></li>
                <li><a href="Logout?username=${sessionScope.userDetails.username}" style="font-size: 18px">Logout</a>

                </li>
            </ul>
        </div><!-- /.nav-collapse -->
    </div><!-- /.container -->
</nav>
<!-- /.navbar -->

<%--If user profile has been activated with a successful login, progress with presenting dynamic content--%>
<c:choose>
    <c:when test="${loginStatus == 'active'}">
<<<<<<< HEAD
=======
        <%@include file="Navbar.jsp" %>
>>>>>>> origin/master

        <p class="text-center">Welcome ${sessionScope.userDetails.username}! Good to see you</p>

        <p class="text-center">"<%=quote%>"</p>
        <p class="text-center"><%=author%></p>

        <div class="news_feed" style="margin-top: 5%;">
            <%--Articles should be dropped into here from AJAX calls--%>
        </div>

        <div class="footer">
            <hr>
            <a href="ChangePassword?username=${sessionScope.userDetails.username}">change your password</a>
        </div>


<<<<<<< HEAD
=======
            <div class="footer">
                <hr>
                <a href="ChangePassword?username=${sessionScope.userDetails.username}">change your password</a>
            </div>


>>>>>>> origin/master
    </c:when>
    <%--When user is not logged in, if content page is accessed, redirect to the login page--%>
    <c:otherwise>

        <c:redirect url="Login"/>

    </c:otherwise>
</c:choose>

</body>
</html>