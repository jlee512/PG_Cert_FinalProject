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
                    <button name="editButton" id="editButton">edit</button>
                </div>

                    <%------User information------%>
                <div class="panel-body">


                    <form method="POST" action="EditUserDetails">
                        <div>

                                <%------User details------%>
                            <h4><i class="fa fa-address-card-o" aria-hidden="true" style="font-size: 30px"></i> User
                                Details:</h4>
                            <label for="username">Username: </label>
                            <input type="text" class="form-control" id="username" name="username"
                                   value="${sessionScope.userDetails.username}">

                            <label for="fullname">Name: </label>
                            <input type="text" id="fullname" name="fullname"
                                   value="${sessionScope.userDetails.firstname} ${sessionScope.userDetails.lastname}">

                            <label for="occupation">Occupation: </label>
                            <input type="text" id="occupation" name="occupation"
                                   value="${sessionScope.userDetails.occupation}">

                            <label for="location">Location: </label>
                            <input type="text" id="location" name="location"
                                   value="${sessionScope.userDetails.city}">
                        </div>

                            <%------Contact details------%>
                        <div>
                            <h4><i class="fa fa-book" aria-hidden="true" style="font-size: 30px"></i> Contact:</h4>

                            <label for="email">Email: </label>
                            <input type="text" id="email" name="email"
                                   value="${sessionScope.userDetails.email}">

                            <label for="phone">Phone: </label>
                            <input type="text" id="phone" name="phone"
                                   value="${sessionScope.userDetails.phone}">

                        </div>

                            <%------About me------%>
                        <div>
                            <h4>About me: </h4>
                            <input type="text" id="aboutme" name="aboutme"
                                   value="${sessionScope.userDetails.profile_description}">
                        </div>

                        <input type="submit" id="saveChanges" name="savechanges" value="save changes">
                    </form>

                        <%------Profile settings------%>
                    <h4><i class="fa fa-user" style="font-size: 30px"></i> Profile settings:</h4>
                    <ul>
                        <li>
                            <button onclick="location.href = 'ChangePassword?username=${sessionScope.userDetails.username}'">
                                Change password
                            </button>
                        </li>
                        <li>

                            <button type="submit" id="deleteaccount">Delete
                                account
                            </button>


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
<script>

    <%--Make variable names for all input fields and the submit button--%>
    var inputfields = $("form").find(':input');
    var saveChanges = $("form").find($("#saveChanges"));

    <%---Set variables to readonly and hide submit button--%>
    inputfields.attr('readonly', 'readonly');
    saveChanges.attr('hidden', 'hidden');

    <%--Edit button makes the form editable and the save changes button appears--%>
    $("#editButton").click(function () {
        inputfields.removeAttr('readonly', 'readonly');
        saveChanges.removeAttr('hidden', 'hidden');
    });

    <%--Save changes returns the form to readonly and the button becomes hidden--%>
    saveChanges.click(function () {
        inputfields.attr('readonly', 'readonly');
        saveChanges.attr('hidden', 'hidden');
    });

    $("#deleteaccount").click(function () {
        var result = confirm("Are you sure you want to delete your account?");
        if (result) {
            location.href = "DeleteUser"
        }
    })
</script>
</html>
