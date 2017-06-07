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


    <%--Font awesome--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.3.0/css/mdb.min.css">

    <%--Responsiveness for all screen sizes--%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <script src="//code.jquery.com/jquery-1.10.2.js"></script>


    <style>


        input[type=date]:disabled, input[type=date][readonly=readonly], input[type=datetime-local]:disabled, input[type=datetime-local][readonly=readonly], input[type=email]:disabled, input[type=email][readonly=readonly], input[type=number]:disabled, input[type=number][readonly=readonly], input[type=password]:disabled, input[type=password][readonly=readonly], input[type=search-md]:disabled, input[type=search-md][readonly=readonly], input[type=search]:disabled, input[type=search][readonly=readonly], input[type=tel]:disabled, input[type=tel][readonly=readonly], input[type=text]:disabled, input[type=text][readonly=readonly], input[type=time]:disabled, input[type=time][readonly=readonly], input[type=url]:disabled, input[type=url][readonly=readonly], textarea.md-textarea:disabled, textarea.md-textarea[readonly=readonly] {
            color: black !important;
            border-bottom: none !important;
            background-color: transparent;
        }

    </style>


</head>

<link rel="shortcut icon" type="image/png" href="Multimedia/favicon.png">
<body id="body">

<%--If user profile has been activated with a successful login, progress with presenting dynamic content--%>
<c:choose>
    <c:when test="${loginStatus == 'active'}">
        <%@include file="Navbar.jsp" %>

        <div class="col-sm-9 panel panel-default" id="profileContent">

                <%------Profile Picture------%>

            <img src="${sessionScope.userDetails.profile_picture}" class="rounded img-responsive center-block">

                <%------Beginning of profile panel------%>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 style="display: inline-block;" class="panel-title"><strong>${sessionScope.userDetails.username}'s</strong>
                        Profile</h3>

                </div>

                    <%------User information------%>
                <div class="panel-body">


                    <form method="POST" action="EditUserDetails" style="color: black">
                        <div>
                                <%------User details------%>
                            <h3><i class="fa fa-address-card-o" aria-hidden="true" style="font-size: 30px"></i> User
                                Details:</h3>

                            <h4>Username</h4>
                            <input type="text" id="username" name="username"
                                   value="${sessionScope.userDetails.username}">


                            <h4>Name </h4>
                            <input type="text" id="fullname" name="fullname"
                                   value="${sessionScope.userDetails.firstname} ${sessionScope.userDetails.lastname}">


                            <h4>Occupation </h4>
                            <input type="text" id="occupation" name="occupation"
                                   value="${sessionScope.userDetails.occupation}">


                            <h4>Location </h4>
                            <input type="text" id="location" name="location"
                                   value="${sessionScope.userDetails.city}">

                        </div>

                            <%------Contact details------%>
                        <div>
                            <h3><i class="fa fa-book" aria-hidden="true" style="font-size: 30px"></i> Contact:</h3>

                            <h4>Email </h4>
                            <input type="text" id="email" name="email"
                                   value="${sessionScope.userDetails.email}">

                            <h4 style="display: inline-block">Phone </h4>
                            <input style="display: inline" type="text" id="phone" name="phone"
                                   value="${sessionScope.userDetails.phone}">
                        </div>

                            <%------About me------%>
                        <div>
                            <h4>About me </h4>
                            <textarea maxlength="200" id="aboutme"
                                      name="aboutme">${sessionScope.userDetails.profile_description}</textarea>
                            <br>
                        </div>

                            <%--Submit button--%>
                        <div>
                            <input type="submit" id="saveChanges" name="savechanges" value="save changes"></div>
                    </form>

                        <%------Profile settings------%>
                    <div>
                        <h3><i class="fa fa-user" style="font-size: 30px"></i> Profile settings:</h3>

                        <button onclick="location.href = 'ChangePassword?username=${sessionScope.userDetails.username}'">
                            Change password
                        </button>

                        <button type="submit" id="deleteaccount">Delete
                            account
                        </button>

                        <button style="display: inline-block;" name="editButton"
                                id="editButton">edit details
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <%------End of user information------%>


        <div>This is for Julian</div>


        <%--Space to type code--%>


    </c:when>
    <%--When user is not logged in, if content page is accessed, redirect to the login page--%>
    <c:otherwise>

        <c:redirect url="Login"/>

    </c:otherwise>
</c:choose>

</body>

<%--Include jQuery--%>
<script
        src="https://code.jquery.com/jquery-3.2.1.min.js"
        integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
        crossorigin="anonymous"></script>

<%--Bootstrap core JavaScript--%>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script>

    <%--resizing the textarea--%>
    $("textarea").height($("textarea")[0].scrollHeight);

    window.onresize = function (event) {
        $("textarea").height($("textarea")[0].scrollHeight);
    };


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

    <%--Delete account + alert--%>
    $("#deleteaccount").click(function () {
        var result = confirm("Are you sure you want to delete your account?");
        if (result) {
            location.href = "DeleteUser"
        }
    });

</script>
</html>
