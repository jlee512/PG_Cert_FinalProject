<%--
  Created by IntelliJ IDEA.
  User: cbla080
  Date: 12/06/2017
  Time: 4:03 PM
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
    <title>${requestScope.user.username}'s profile</title>
    <%@include file="HeadStylingLinks.jsp" %>

    <style>
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


                    <%------Profile Picture------%>
                <div>
                    <img id="profile-image" src="${requestScope.user.profile_picture}"
                         class="img-responsive center-block"
                         style="padding-top: 15px; padding-bottom: 15px;">
                </div>
            </div>

                <%------Beginning of nested profile panel------%>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <strong>${requestScope.user.username}'s</strong>
                        Profile</h3>
                </div>

                    <%------User information------%>
                <div class="panel-body">

                        <div>
                                <%------User details------%>
                            <div>
                                <h3><i class="fa fa-address-card-o" aria-hidden="true" style="font-size: 30px"></i>
                                    User
                                    Details:</h3>

                                <h4>Username</h4>
                                <div id="username"><p>${requestScope.user.username}</p></div>

                                <h4>Name </h4>
                                <div style="width: 20%" class="form-group"><p>${requestScope.user.firstname}</p></div>

                                <div style="width: 70%" class="form-group"><p>${requestScope.user.lastname}</p></div>

                                <h4 style="display: inline-block;">Occupation: </h4>
                                <div style=" display: inline-block" id="occupation"><p>${requestScope.user.occupation}</p></div>

                                <h4>Location </h4>
                                <div id="location"><p>${requestScope.user.city}</p></div>
                            </div>

                                <%------Contact details------%>
                            <div>
                                <h3><i class="fa fa-book" aria-hidden="true" style="font-size: 30px"></i> Contact: </h3>

                                <h4>Email </h4>
                                <div id="email"><p>${requestScope.user.email}</p></div>

                                <h4 style="display: inline-block">Phone </h4>
                                <div style="display: inline"id="phone"><p>${requestScope.user.phone}</p></div>
                            </div>

                                <%------About me------%>
                            <div>
                                <h4>About me </h4>
                                <div style="resize: none;" id="aboutme"><p>${requestScope.user.profile_description}</p></div>
                            </div>
                        </div>

                </div>
            </div>
        </div>
        <%------End of user information------%>


        <div class="col-sm-8" id="userArticles">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">${requestScope.user.username}'s Article Archive</h3>
                </div>
                <div class="panel-body">
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

<script type="text/javascript" src="Javascript/author_article_display.js"></script>
<%------------------------------------------------------------------------------------------------------------------------------------%>


</body>

</html>
