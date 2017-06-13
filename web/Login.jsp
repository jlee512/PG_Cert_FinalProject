<%--
  Created by IntelliJ IDEA.
  User: jlee512
  Date: 29/05/2017
  Time: 7:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%--Setup .jsp file with jstl tag library--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <%@ include file="HeadStylingLinks.jsp" %>
    <title>Login</title>
    <meta name="google-signin-client_id"
          content="17619298298-hlb3n0ra5pkquu73jbs8sir2m5i4b4b8.apps.googleusercontent.com">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <link rel="shortcut icon" type="image/png" href="Multimedia/favicon.png">
    <style>

        .g-signin2 > div {

            margin: 0 auto;

        }

    </style>

</head>
<body>

<div class="backGroundImage">
    <div class="vertical-center">
        <div class="card container setOpacity">
            <div class="card-block">
                <form action="LoginAttempt" method="POST" style="margin-top: 2%">

                    <%--Login title--%>
                    <div class="text-center">
                        <h3><i class="fa fa-lock"></i> Login:</h3>
                        <hr class="mt-2 mb-2">
                    </div>
                    <%--Login Title--%>

                    <%--If user is logged in (i.e. the login status is stored in the current session, return to the content page--%>
                    <c:if test="${loginStatus == 'active'}">
                        <c:redirect url="Content?username=${sessionScope.userDetails.username}"/>
                    </c:if>

                    <%--jsp scriptlet to assess whether an invalid password was entered. If so, prepopulate username field with original username--%>
                    <%
                        String usernameAttempt = request.getParameter("username");
                        String usernamePrepopulate = "";
                        if (usernameAttempt != null) {
                            usernamePrepopulate = usernameAttempt;
                        }
                    %>

                    <%--Form input for login page--%>
                    <fieldset id="fieldset">

                        <%--USERNAME--%>
                        <div class="md-form">
                            <i class="fa fa-user prefix"></i>
                            <input class="form-control" type="text" id="username" name="username"

                                   oninvalid="this.setCustomValidity('please enter your username')"
                                   oninput="this.setCustomValidity('')" value="<%=usernamePrepopulate%>"
                                   required>
                            <label for="username">Your Username</label>
                        </div>

                        <%--PASSWORD--%>
                        <div class="md-form">
                            <i class="fa fa-lock prefix"></i>
                            <input class="form-control" type="password" id="password" name="password"

                                   oninvalid="this.setCustomValidity('please enter your password')"
                                   oninput="this.setCustomValidity('')"
                                   required>
                            <label for="password">Your password</label>
                        </div>

                        <%--SIGN IN SUBMIT BUTTON--%>
                        <div class="text-center">
                            <button class="btn btn-primary btn-rounded" type="submit" id="submit">Sign in</button>
                        </div>

                        <br>

                        <%--GOOGLE SIGN-IN BUTTON--%>
                        <div class="g-signin2" data-onsuccess="onSignIn" style="width: 100%;"></div>

                        <br>

                        <%--SIGN UP LINK--%>
                        <div class="modal-footer">
                            <div class="options">
                                <p>Don't have an account? <a href="Registration">Sign up</a></p>
                            </div>
                        </div>

                        <%--Selection for additional user feedback for different login errors--%>
                        <% String loginStatus = request.getParameter("loginStatus");
                            if (loginStatus != null && loginStatus.equals("invalidPassword")) {%>
                        <br>
                        <p style="color: red">your password is invalid password, please try again</p>
                        <%} else if (loginStatus != null && loginStatus.equals("invalidUsername")) {%>
                        <br>
                        <p style="color: red">your username was not recognised, please try again</p>
                        <%} else if (loginStatus != null && loginStatus.equals("loggedOut")) {%>
                        <br>
                        <p style="color: darkblue">you are no longer logged in, please try again</p>
                        <%} else if (loginStatus != null && loginStatus.equals("invalidGoogleSignIn")) {%>
                            <br>
                            <p style="color: red">your google sign in details were incorrect, please try again</p>
                        <%
                            }
                        %>
                        <%----------------------------------------------------------------------%>

                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="BodyStylingLinks.jsp" %>

<%--Script inclusions--%>

<%--Google OAuth--%>

<script>

    function onSignIn(googleUser) {
        var profile = googleUser.getBasicProfile();
        console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
        console.log('Name: ' + profile.getName());
        console.log('Image URL: ' + profile.getImageUrl());
        console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.

        //use jquery to post data dynamically to google to verify identity
        var form = $('<form action="LoginGoogleOAuth" method="post">' +
            '<input type="text" name="id_token" value="' +
            googleUser.getAuthResponse().id_token + '" />' +
            '</form>');
        $('body').append(form);
        form.submit();
    }

</script>

</body>
</html>
