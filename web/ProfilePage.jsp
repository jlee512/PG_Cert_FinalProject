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
    <%@include file="HeadStylingLinks.jsp" %>

    <style>
        <%--Disable mdbBootstrap form "readony" styling--%>
        input[type=date]:disabled, input[type=date][readonly=readonly], input[type=datetime-local]:disabled, input[type=datetime-local][readonly=readonly], input[type=email]:disabled, input[type=email][readonly=readonly], input[type=number]:disabled, input[type=number][readonly=readonly], input[type=password]:disabled, input[type=password][readonly=readonly], input[type=search-md]:disabled, input[type=search-md][readonly=readonly], input[type=search]:disabled, input[type=search][readonly=readonly], input[type=tel]:disabled, input[type=tel][readonly=readonly], input[type=text]:disabled, input[type=text][readonly=readonly], input[type=time]:disabled, input[type=time][readonly=readonly], input[type=url]:disabled, input[type=url][readonly=readonly], textarea.md-textarea:disabled, textarea.md-textarea[readonly=readonly] {
            color: black !important;
            border-bottom: none !important;
            background-color: transparent;
        }

        .add-article-button {

            background-color: #b2ebf2 !important;
            -webkit-transition: background-color 1s;
            transition: background-color 1s;

        }

        .add-article-button:hover {
            background-color: #ffd54f !important;
        }
    </style>

    <link rel="shortcut icon" type="image/png" href="Multimedia/favicon.png">

    <link rel="stylesheet" type="text/css" href="CSS/loader_animation.css">

</head>

<body>

<%--If user profile has been activated with a successful login, progress with presenting dynamic content--%>
<c:choose>
    <c:when test="${loginStatus == 'active'}">
        <%@include file="Navbar.jsp" %>

        <%--Main  panel--%>
        <div class="col-sm-4" id="profileContent">
            <div class="panel panel-default" style="padding-right: 15px; padding-left: 15px;">


                    <%------Profile Picture------%>
                <div id="profilePicture">
                    <img id="profile-image" src="${sessionScope.userDetails.profile_picture}"
                         class="img-responsive center-block"
                         style="padding-top: 15px; padding-bottom: 15px;">
                    <div id="defaultPicture"></div>
                </div>
            </div>

                <%------Beginning of nested profile panel------%>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <strong>${sessionScope.userDetails.username}'s</strong>
                        Profile</h3>
                </div>

                    <%------User information------%>
                <div class="panel-body">


                    <form method="POST" action="EditUserDetails" style="color: black">
                        <div>
                                <%------User details------%>
                            <div>
                                <h3><i class="fa fa-address-card-o" aria-hidden="true" style="font-size: 30px"></i>
                                    User
                                    Details:</h3>

                                <h4>Username</h4>
                                <input type="text" id="username" name="username"
                                       value="${sessionScope.userDetails.username}">

                                <h4>Name </h4>
                                <input style="width: 20%" class="form-group" type="text" name="firstname"
                                       value="${sessionScope.userDetails.firstname} ">

                                <input style="width: 70%" class="form-group" type="text" name="lastname"
                                       value="${sessionScope.userDetails.lastname}">

                                <h4 style="display: inline-block;">Occupation: </h4>
                                <input style=" display: inline-block" type="text" id="occupation"
                                       name="occupation"
                                       value="${sessionScope.userDetails.occupation}">

                                <h4>Location </h4>
                                <input type="text" id="location" name="location"
                                       value="${sessionScope.userDetails.city}">

                            </div>

                                <%------Contact details------%>
                            <div>
                                <h3><i class="fa fa-book" aria-hidden="true" style="font-size: 30px"></i> Contact: </h3>

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
                                <textarea style="resize: none;" maxlength="200" id="aboutme"
                                          name="aboutme">${sessionScope.userDetails.profile_description}</textarea>
                            </div>

                                <%--Submit button (added using javascript for security)--%>
                            <div id="submit"></div>
                        </div>
                    </form>

                        <%------Profile settings------%>
                    <div>
                        <h3><i class="fa fa-user" style="font-size: 30px"></i> Profile settings:</h3>

                            <%--Change password--%>
                        <button onclick="location.href = 'ChangePassword?username=${sessionScope.userDetails.username}'">
                            Change password
                        </button>

                            <%--Delete account--%>
                        <button type="submit" id="deleteaccount">Delete
                            account
                        </button>

                            <%--Edit profile--%>
                        <button style="display: inline-block;" name="editButton"
                                id="editButton">edit profile</button>

                    </div>
                </div>
            </div>
        </div>
        <%------End of user information------%>


        <div class="col-sm-8" id="userArticles">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">${sessionScope.userDetails.username}'s Article Archive</h3>
                </div>
                <div class="panel-body">
                    <div class="panel panel-default">
                        <div class="panel-heading add-article-button">
                            <p><i class="fa fa-plus" aria-hidden="true"></i> Add an Article</p>
                        </div>
                    </div>
                    <div class="news_feed">
                        <%--Articles are inserted here via AJAX request--%>
                    </div>
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
            </div>
        </div>


    </c:when>
    <%--When user is not logged in, if content page is accessed, redirect to the login page--%>
    <c:otherwise>

        <c:redirect url="Login"/>

    </c:otherwise>
</c:choose>

<%---------------------------Script Inclusions---------------------------------%>

<%--Include Bootstrap JS, jQuery and jQuery UI--%>
<%@ include file="BodyStylingLinks.jsp" %>

<script type="application/javascript" src="Javascript/add_an_article_form.js"></script>
<script type="application/javascript" src="Javascript/author_article_display.js"></script>




<script>

    <%--Hide the upload image--%>
    $("#profile-image-upload").hide();

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
    saveChanges.hide();

    <%--Edit button makes the form editable and the save changes button appears--%>
    $("#editButton").click(function () {
        inputfields.removeAttr('readonly', 'readonly');
        if (!$('#saveChanges')[0]) {
            $("#submit").append("<input type='submit' id='saveChanges' name='savechange' value='save changes'>")
        }
    });

    <%--Save changes returns the form to readonly and the button becomes hidden--%>
    saveChanges.click(function () {
        inputfields.attr('readonly', 'readonly');
        $("#saveChanges").remove();

    });

    <%--Delete account + alert--%>
    $("#deleteaccount").click(function () {
        var result = confirm("Are you sure you want to delete your account?");
        if (result) {
            location.href = "DeleteUser";
        }
    });

    $("#profilePicture").click(function () {

        if (!$("#pictureForm")[0]) {
            $("#profilePicture").append("<form id='pictureForm' enctype='multipart/form-data' method='POST' action='UploadProfilePicture'>" +
                "<input id='profile-image-upload' name='file' required type='file'>" +
//                    insert choose default here
                "<input type='submit' id='updatePicture' value='Upload Photo'>" +
                "</form>")

        }
    })

</script>

</body>

</html>
