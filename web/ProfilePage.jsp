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

//    Read only needs to be implemented ***************
    String readonly = "readonly";
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

                <%------Profile Picture------%>
            <img src="${sessionScope.userDetails.profile_picture}" class="img-circle img-responsive center-block">

                <%------Beginning of profile panel------%>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">${sessionScope.userDetails.username}'s Profile</h3>
                    <a>edit</a>
                </div>

                    <%------User information------%>
                <div class="panel-body">


                    <form>
                        <div>

                                <%------User details------%>
                            <h4><i class="fa fa-address-card-o" aria-hidden="true" style="font-size: 30px"></i> User
                                Details:</h4>
                            <label for="username">Username: </label>
                            <input type="text" id="username" name="username" <%=readonly%>
                                   value="${sessionScope.userDetails.username}">

                            <label for="fullname">Name: </label>
                            <input type="text" id="fullname" name="fullname" <%=readonly%>
                                   value="${sessionScope.userDetails.firstname} ${sessionScope.userDetails.lastname}">

                            <label for="occupation">Occupation: </label>
                            <input type="text" id="occupation" name="occupation" <%=readonly%>
                                   value="${sessionScope.userDetails.occupation}">

                            <label for="location">Location: </label>
                            <input type="text" id="location" name="location" <%=readonly%> value="${sessionScope.userDetails.city}">
                        </div>

                            <%------Contact details------%>
                        <div>
                            <h4><i class="fa fa-book" aria-hidden="true" style="font-size: 30px"></i> Contact:</h4>

                            <label for="email">Email: </label>
                            <input type="text" id="email" name="email" <%=readonly%> value="${sessionScope.userDetails.email}">

                            <label for="phone">Phone: </label>
                            <input type="text" id="phone" name="phone" <%=readonly%>
                                   value="${sessionScope.userDetails.phone}">

                        </div>

                            <%------About me------%>
                        <div>
                            <h4>About me: </h4>
                            <input type="text" name="aboutme" <%=readonly%>
                                   value="${sessionScope.userDetails.profile_description}">
                        </div>
                    </form>

                    <%------Profile settings------%>
                    <h4><i class="fa fa-user" style="font-size: 30px"></i> Profile settings:</h4>
                    <ul>
                        <li>
                            <a href="ChangePassword?username=${sessionScope.userDetails.username}"> Change your
                                Password</a>
                        </li>
                        <li>
                            <a href="https://www.youtube.com/watch?v=dQw4w9WgXcQ">Delete account</a>
                        </li>
                    </ul>


                </div>
                    <%------End of user information------%>

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
