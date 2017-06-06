<%--
  Created by IntelliJ IDEA.
  User: ycow194
  Date: 5/06/2017
  Time: 12:47 PM
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
%>


<html>
<head>
    <title>${sessionScope.userDetails.username}'s profile</title>
    <%@ include file="HeadStylingLinks.jsp" %>
</head>
<link rel="shortcut icon" type="image/png" href="Multimedia/favicon.png">
<body>

<%--If user profile has been activated with a successful login, progress with presenting dynamic content--%>
<c:choose>
    <c:when test="${loginStatus == 'active'}">
        <%@include file="Navbar.jsp" %>

        <div class="col-sm-9 panel panel-default" id="profileContent">

                <%--Profile Picture--%>
            <img src="${sessionScope.userDetails.profile_picture}" class="img-circle img-responsive center-block">

                <%--Beginning of profile panel--%>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">${sessionScope.userDetails.username}'s Profile</h3>
                </div>

                    <%--User information--%>
                <div class="panel-body">
                    <ul>
                        <li>
                            User Details:
                            <ul>
                                <li><p><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                    Username: ${sessionScope.userDetails.username}</p>
                                </li>
                                <li>
                                    <p><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Name: first
                                        and last name here</p>
                                </li>
                                <li>
                                    <p><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Occupation
                                    </p>
                                </li>
                                <li>
                                    <p><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Location</p>
                                </li>
                            </ul>
                            Contact:
                            <ul>
                                <li>
                                    <p><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                        Email: ${sessionScope.userDetails.email}</p>
                                </li>
                                <li>
                                    <p><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Phone:
                                        021-564-3452</p>
                                </li>
                            </ul>
                            Profile settings:
                            <ul>
                                <li>
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><a
                                        href="ChangePassword?username=${sessionScope.userDetails.username}"> Change your
                                    Password</a>
                                </li>
                                <li>
                                    <p><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Delete
                                        account</p>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <p>About me: I like fish</p>
                </div>
                    <%--End of user information--%>

            </div>
        </div>
    </c:when>
    <%--When user is not logged in, if content page is accessed, redirect to the login page--%>
    <c:otherwise>

        <c:redirect url="Login"/>

    </c:otherwise>
</c:choose>

</body>
<%@include file="BodyStylingLinks.jsp" %>
</html>
