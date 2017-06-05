<%--
  Created by IntelliJ IDEA.
  User: jlee512
  Date: 29/05/2017
  Time: 7:59 AM
  To change this template use File | Settings | File Templates.
--%>

<%--Newsfeed/Frontpage--%>
<%--AJAX functionality can be added at a later date--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="login.system.dao.User" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.io.IOException" %>

<%
    /*------------------------------------------------------------------------------------------------*/
    /*Initial page setup*/

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
    System.out.println(randomQuote);

    File quotes_file = new File(quotes_file_path);
    try (BufferedReader br = new BufferedReader(new FileReader(quotes_file))) {

        for (int i = 0; i < numQuotes; i++) {
            if (i == (randomQuote)) {
                quote = br.readLine();
                author = br.readLine();
            } else {
                br.readLine();
                br.readLine();
            }
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

%>

<%-------------------------------------------------------------------------------------%>
<%--JSP content page development--%>

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

</style>

<link rel="shortcut icon" type="image/png" href="Multimedia/favicon.png">

<body>

<%--If user profile has been activated with a successful login, progress with presenting dynamic content--%>
<c:choose>
    <c:when test="${loginStatus == 'active'}">

        <p>Welcome ${sessionScope.userDetails.username}!</p>

        <p>"<%=quote%>"</p>
        <p><%=author%></p>

        <div>
            <a href="ChangePassword?username=${sessionScope.userDetails.username}">change your password</a>
        </div>


        <div>
            <a href="ViewArticle?article_id=4">View Article 4</a>
        </div>

        <div>
            <a href="Logout?username=${sessionScope.userDetails.username}" id="logoutButton">logout</a>
        </div>

        <div class="articleContainer">

        </div>

    </c:when>
    <%--When user is not logged in, if content page is accessed, redirect to the login page--%>
    <c:otherwise>

        <c:redirect url="/Login"/>

    </c:otherwise>
</c:choose>

<script src="Javascript/article_display.js"></script>

<%@include file="BodyStylingLinks.jsp" %>

</body>
</html>
