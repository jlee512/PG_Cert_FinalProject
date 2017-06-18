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
    <script src="Javascript/form_security_validation.js"></script>
    <style>
        <%--Disable mdbBootstrap form "readony" styling--%>
        input[type=date]:disabled, input[type=date][readonly=readonly], input[type=datetime-local]:disabled, input[type=datetime-local][readonly=readonly], input[type=email]:disabled, input[type=email][readonly=readonly], input[type=number]:disabled, input[type=number][readonly=readonly], input[type=password]:disabled, input[type=password][readonly=readonly], input[type=search-md]:disabled, input[type=search-md][readonly=readonly], input[type=search]:disabled, input[type=search][readonly=readonly], input[type=tel]:disabled, input[type=tel][readonly=readonly], input[type=text]:disabled, input[type=text][readonly=readonly], input[type=time]:disabled, input[type=time][readonly=readonly], input[type=url]:disabled, input[type=url][readonly=readonly], textarea.md-textarea:disabled, textarea.md-textarea[readonly=readonly] {
            color: black !important;
            border-bottom: none !important;
            background-color: transparent;
        }

        .add-article-button, .change-profile-pic-button {

            background-color: #b2ebf2 !important;
            -webkit-transition: background-color 1s;
            transition: background-color 1s;

        }

        .user-profile-pic-button, .default-profile-pic-button, .submit-user-pic {

            background-color: #c4f4a4 !important;
            -webkit-transition: background-color 1s;
            transition: background-color 1s;
            color: black;

        }

        .change-profile-pic-button {

            margin-top: 0px;
            margin-left: 0px;
            margin-right: 0px;

        }

        .add-article-button:hover, .change-profile-pic-button:hover, .user-profile-pic-button:hover, .default-profile-pic-button:hover, .submit-user-pic:hover {
            background-color: #ffd54f !important;
        }

        .fixedDefaultPictureSize {
            position: relative;
            margin-right: 5px;
            margin-bottom: 5px;
            width: 120px;
            height: 120px;
            background-position: 50% 50%;
            background-repeat: no-repeat;
            background-size: cover;
        }

        /*Styling for radio check boxes for profile pictures*/
        label > input { /* HIDE RADIO */
            visibility: hidden; /* Makes input not-clickable */
            position: absolute; /* Remove input from document flow */
        }

        label > input + img { /* IMAGE STYLES */
            cursor: pointer;
            border: 2px solid transparent;
        }

        label > input:checked + img { /* (RADIO CHECKED) IMAGE STYLES */
            border: 2px solid #ffd54f;
        }

        #editButton, #deleteaccount, #changePassword {

            background-color: #00acc1;

        }

        /*END OF STYLING*/
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


  <%---------------------------------ALERT MESSAGES-----------------------------%>
                    <%--If the article deleted parameter exists, display a top level panel header notifying the user of the article deletion status--%>
                <c:if test="${not empty param.articleDeleted}">
                    <c:choose>
                        <%--When the article is successfully deleted--%>
                        <c:when test="${param.articleDeleted}">
                            <div class="comment-delete-notification delete-true card" style="text-align: center; background-color: #c2f5a3;">
                                <div class="card-header">
                                    <h3>Article successfully deleted</h3>
                                </div>
                            </div>

                        </c:when>

                        <%--If the article is not successfully deleted--%>
                        <c:when test="${not param.articleDeleted}">
                            <div class="comment-delete delete-false card" style="text-align: center; background-color: #fad1d1;">
                                <div class="card-header">
                                    <h3>You do not have permission to delete this article</h3>
                                </div>
                            </div>

                        </c:when>
                    </c:choose>
                </c:if>

                    <%--If the articleadded parameter exists, display a top level panel header notifying the user of the article addition status--%>
                <c:if test="${not empty param.articleadded}">
                    <c:choose>
                        <%--When the article is successfully added--%>
                        <c:when test="${param.articleadded == 'successful'}">
                            <div class="article-add-notification add-true card" style="text-align: center; background-color: #c2f5a3;">
                                <div class="card-header">
                                    <h3>Article successfully added</h3>
                                </div>
                            </div>

                        </c:when>

                        <%--If the article is not successfully added (duplicate)--%>
                        <c:when test="${param.articleadded == 'duplicate'}">
                            <div class="article-add-notification add-duplicate card" style="text-align: center; background-color: #fff3cc;">
                                <div class="card-header">
                                    <h3>You have tried to add a duplicate article</h3>
                                </div>
                            </div>

                        </c:when>

                        <%--If the article is not successfully added (invalid)--%>
                        <c:when test="${param.articleadded == 'invalid'}">
                            <div class="article-add-notification add-invalid card" style="text-align: center; background-color: #fff3cc;">
                                <div class="card-header">
                                    <h3>The article contains invalid fields</h3>
                                </div>
                            </div>

                        </c:when>

                        <%--If the article is not successfully added (invalid)--%>
                        <c:when test="${param.articleadded == 'db'}">
                            <div class="article-add-notification add-db card" style="text-align: center; background-color: #fff3cc;">
                                <div class="card-header">
                                    <h3>The article could not be added to the database</h3>
                                </div>
                            </div>

                        </c:when>

                    </c:choose>
                </c:if>

          <%--If the multimediaAdditionStatus parameter exists, display a top level panel header notifying the user of the multimedia addition status--%>
      <c:if test="${not empty param.multimediaAdditionStatus}">
          <c:choose>
              <%--When the multimedia is successfully added--%>
              <c:when test="${param.multimediaAdditionStatus == 'success'}">
                  <div class="multimedia-add-notification mmadd-true card" style="text-align: center; background-color: #c2f5a3;">
                      <div class="card-header">
                          <h3>Multimedia files successfully added</h3>
                      </div>
                  </div>

              </c:when>

              <%--If the multimedia is not successfully added (invalid)--%>
              <c:when test="${param.multimediaAdditionStatus == 'invalid_upload'}">
                  <div class="multimedia-add-notification mmadd-invalid card" style="text-align: center; background-color: #fff3cc;">
                      <div class="card-header">
                          <h3>You multimedia uploads were not valid</h3>
                          <p>Compatible file upload formats (JPEG, PNG, GIF, MP3 and MP4)</p>
                          <p>YouTube videos to be in the form: https://www.youtube.com/watch?v=...</p>
                          <p>This video may already exist</p>
                      </div>
                  </div>

              </c:when>

              <%--If no multimedia is attached to the upload--%>
              <c:when test="${param.multimediaAdditionStatus == 'no_files'}">
                  <div class="multimedia-add-notification mmadd-none card" style="text-align: center; background-color: #fff3cc;">
                      <div class="card-header">
                          <h3>No files were added to the upload form</h3>
                      </div>
                  </div>

              </c:when>

              <%--If uploaded multimedia is too large--%>
              <c:when test="${param.multimediaAdditionStatus == 'file_too_large'}">
                  <div class="multimedia-add-notification mmadd-none card" style="text-align: center; background-color: #fff3cc;">
                      <div class="card-header">
                          <h3>The file you tried to upload was too large</h3>
                      </div>
                  </div>

              </c:when>

          </c:choose>
      </c:if>

          <%--If the edit_status parameter exists, display a top level panel header notifying the user of the article edit status--%>
      <c:if test="${not empty param.edit_status}">
          <c:choose>
              <%--When the article is successfully edited--%>
              <c:when test="${param.edit_status == 'success'}">
                  <div class="article-edit-notification edit-true card" style="text-align: center; background-color: #c2f5a3;">
                      <div class="card-header">
                          <h3>Article edited successfully</h3>
                      </div>
                  </div>

              </c:when>

              <%--If the article is not successfully edited (invalid)--%>
              <c:when test="${param.edit_status == 'invalid'}">
                  <div class="article-edit-notification edit-invalid card" style="text-align: center; background-color: #fff3cc;">
                      <div class="card-header">
                          <h3>Sorry, we were not able to update your article at this time</h3>
                          <p>Some invalid information was entered or you do not have permission to edit this article</p>
                      </div>
                  </div>

              </c:when>

          </c:choose>
      </c:if>

 <%-----------------------------ALERT MESSAGES FINISHED------------------------%>


                    <%------Profile Picture------%>
                <div>
                    <img id="profile-image" src="${sessionScope.userDetails.profile_picture}"
                         class="img-responsive center-block"
                         style="padding-top: 15px; padding-bottom: 15px;">
                    <div class="panel-body">
                        <div class="panel panel-default">
                            <div class="panel-heading change-profile-pic-button">
                                <p><i class="fa fa-user-circle-o" aria-hidden="true"></i> Change Profile Picture</p>
                            </div>
                        </div>
                        <div class="change-picture-options" style="text-align: center;">
                                <%--Change profile picture inserted here depending on toggle option--%>
                        </div>
                    </div>
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

                                <h4>Name</h4>
                                <div>
                                <input class="form-group" type="text" name="firstname"
                                       value="${sessionScope.userDetails.firstname} "></div>

                                <div><input class="form-group" type="text" name="lastname"
                                            value="${sessionScope.userDetails.lastname}"></div>

                                <h4 style="display: inline-block;">Occupation</h4>
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
                                <%--If a google-sign-in, include an alert letting the user know this is what is stored as a link to their google account--%>
                                <c:if test="${sessionScope.googleSignIn}">
                                    <div class="card-header text-center">
                                        <span class="badge badge-warning" style="background-color: #f9a825;">We noticed you have used Google Sign In.</span>
                                        <span class="badge badge-warning" style="background-color: #f9a825;">Your gmail address is linked to this account</span>
                                        <span class="badge badge-warning" style="background-color: #f9a825;">Before changing, please set your password</span>
                                    </div>
                                </c:if>


                                <h4 style="display: inline-block">Phone </h4>
                                <input style="display: inline" type="text" id="phone" name="phone" maxlength="14"
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
                    <h3><i class="fa fa-user" style="font-size: 30px"></i> Profile settings:</h3>

                    <div class="text-center">
                            <%--Change password--%>
                        <button class="btn btn-info btn-sm" id="changePassword"
                                onclick="location.href = 'ChangePassword?username=${sessionScope.userDetails.username}'">
                            Change password
                        </button>

                            <%--Delete account--%>
                        <button class="btn btn-info btn-sm" type="submit" id="deleteaccount">Delete
                            account
                        </button>

                            <%--Edit profile--%>
                        <button class="btn btn-info btn-sm" style="display: inline-block;" name="editButton"
                                id="editButton">edit profile
                        </button>
                    </div>

                </div>
            </div>
        </div>
        <%------End of user information------%>


        <div class="col-sm-8" id="userArticles">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title" style='width: 50%; display: inline-block; text-overflow: ellipsis; overflow: hidden; white-space: nowrap;'>${sessionScope.userDetails.username}'s Article Archive</h3>
                    <a href="MyGallery"><h3 id="multimedia-gallery-link" class="panel-title pull-right" style="color: white;"><span style="background-color: #00acc1; padding: 5px 10px 5px 10px; border-radius: 2px;" >My Gallery &nbsp <i class="fa fa-camera" aria-hidden="true"></i></span></h3></a>
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

<script>


    <%--resizing the textarea also rezise when window is resizes--%>
    <%--REVISIT THIS make it resize when window is reduced--%>
    $("textarea").height($("textarea")[0].scrollHeight);

    window.onresize = function (event) {
        $("textarea").height($("textarea")[0].scrollHeight);
    };

    <%--Makes all inputs of the form readonly--%>
    var inputfields = $("form").find(':input');
    inputfields.attr('readonly', 'readonly');


    <%--Edit button makes the form editable and the save changes button is appended--%>
    $("#editButton").click(function () {
        inputfields.removeAttr('readonly', 'readonly');

        if ($(".edit_User_Details")[0]) {

            $("#editButton").removeClass("edit_User_Details");
            inputfields.attr('readonly', 'readonly');
            $("#saveChanges").remove();

        } else {
            $("#editButton").addClass("edit_User_Details");
            $("#submit").append("<button type='submit' class='btn btn-sm btn-success' id='saveChanges' name='savechange' >save changes</button>")
        }

    });

    <%--Save changes returns the form to readonly and the button is removed--%>
    $("#saveChanges").click(function () {
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



</script>

<%---------------------------Script Inclusions---------------------------------%>

<%--Include Bootstrap JS, jQuery and jQuery UI--%>
<%@ include file="BodyStylingLinks.jsp" %>

<script type="text/javascript" src="Javascript/add_an_article_form.js"></script>
<script type="text/javascript" src="Javascript/author_article_display.js"></script>
<%----------When you click the profile picture a form appends and you are able to upload a new photo OR select from default photos-----------%>
<%--Moved by JUL to separate JS file--%>
<script type="text/javascript" src="Javascript/update_profile_picture.js"></script>
<script type="text/javascript" src="Javascript/edit_an_article_form.js"></script>
<script type="text/javascript" src="Javascript/upload_multimedia.js"></script>
<%------------------------------------------------------------------------------------------------------------------------------------%>


</body>

</html>
