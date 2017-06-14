<%--
  Created by IntelliJ IDEA.
  User: jlee512
  Date: 29/05/2017
  Time: 7:59 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <%@ include file="HeadStylingLinks.jsp" %>

    <%--If user is logged in (i.e. the login status is stored in the current session, return to the content page--%>
    <c:if test="${loginStatus == 'active'}">

        <c:redirect url="Content?username=${sessionScope.userDetails.username}"/>

    </c:if>
    <%------------------------------------------------------------------------------------------------------------%>

    <title>Welcome! Register an Account</title>
    <%--Google recaptcha--%>
    <script src='https://www.google.com/recaptcha/api.js'></script>

    <%--Google Sign Up--%>
    <meta name="google-signin-client_id"
          content="17619298298-hlb3n0ra5pkquu73jbs8sir2m5i4b4b8.apps.googleusercontent.com">
    <script src="https://apis.google.com/js/platform.js" async defer></script>

    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet" type="text/css">
    <script src="https://apis.google.com/js/api:client.js"></script>


    <script>

        function onSignIn(googleUser) {
            var profile = googleUser.getBasicProfile();
            console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
            console.log('Name: ' + profile.getName());
            console.log('Image URL: ' + profile.getImageUrl());
            console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.

            //use jquery to post data dynamically to google to verify identity
            var form = $('<form action="GoogleRegistration" method="post">' +
                '<input type="text" name="id_token" value="' +
                googleUser.getAuthResponse().id_token + '" />' +
                '</form>');
            $('body').append(form);
            form.submit();
        }

        var googleUser = {};
        var startApp = function() {
            gapi.load('auth2', function(){
                // Retrieve the singleton for the GoogleAuth library and set up the client.
                auth2 = gapi.auth2.init({
                    client_id: '17619298298-hlb3n0ra5pkquu73jbs8sir2m5i4b4b8.apps.googleusercontent.com',
                    cookiepolicy: 'single_host_origin',
                    // Request scopes in addition to 'profile' and 'email'
                    //scope: 'additional_scope'
                });
                attachSignin(document.getElementById('customBtn'));
            });
        };

        function attachSignin(element) {
            console.log(element.id);
            auth2.attachClickHandler(element, {},
                function(googleUser) {
                    document.getElementById('name').innerText = "Signed in: " +
                        googleUser.getBasicProfile().getName();
                    onSignIn(googleUser)
                }, function(error) {
                    alert(JSON.stringify(error, undefined, 2));
                });
        }
    </script>
    <style type="text/css">
        #customBtn {
            text-align: center;
            display: inline-block;
            background: #4285f4;
            color: white;
            width: 155px;
            border-radius: 5px;
            white-space: nowrap;
        }

        #customBtn:hover {
            cursor: pointer;
        }

        span.label {
            font-weight: bold;
        }

        span.icon {
            background: url('Multimedia/GoogleSignIn/google_logo.png') transparent 5px 50% no-repeat;
            display: inline-block;
            vertical-align: middle;
            width: 42px;
            height: 42px;
            border-right: #2265d4 1px solid;
        }

        span.buttonText {
            display: inline-block;
            vertical-align: middle;
            padding-left: 3px;
            padding-right: 42px;
            font-size: 14px;
            font-weight: bold;
            /* Use the Roboto font that is loaded in the <head> */
            font-family: 'Roboto', sans-serif;
        }

        #gSignInWrapper {

            text-align: center;

        }

    </style>

</head>
<body>
<div class="backGroundImage">
    <div class="vertical-center">
        <div class="card container setOpacity">
            <form action="RegistrationAttempt" method="POST">
                <div>
                    <h1 class="text-center">sign up</h1>

                    <fieldset id="fieldset">

                        <%--Username input--%>
                        <div class="md-form">
                            <i class="fa fa-user prefix"></i>
                            <input class="form-control" type="text" id="username" name="username" required minlength="3"
                                   onchange="checkForSpaces(this)"/>
                            <label for="username">New username</label>
                        </div>

                        <%--Email input--%>
                        <div class="md-form">
                            <i class="fa fa-envelope prefix"></i>
                            <input class="form-control" type="email" id="email" name="email"
                                   pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"
                                   oninvalid="setCustomValidity('Please ensure you enter a valid email address in lowercase')"
                                   onchange="checkForSpaces(this)"/>
                            <label for="email">Your email</label>
                        </div>

                        <%--Password input--%>
                        <div class="md-form">
                            <i class="fa fa-unlock-alt prefix"></i>
                            <input class="form-control" type="password" id="password" name="password" pattern="^(?=.*[^a-zA-Z])(?=.*[a-z])(?=.*[A-Z])\S{8,}$" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
                                   onchange="checkForSpaces(this)"
                                   required/>
                            <label for="password">Your password</label>
                        </div>

                        <%--PASSWORD VERIFICATION--%>
                        <div class="md-form">
                            <i class="fa fa-lock prefix"></i>
                            <input class="form-control" type="password" id="passwordVerify" name="passwordVerify"
                                   onchange="checkForSpaces(this)" required/>
                            <label for="passwordVerify">Verify password</label>
                        </div>

                        <%--GOOGLE RECAPTCHA--%>
                        <div class="md-form" style="opacity: 1">
                            <div class="g-recaptcha" data-sitekey="6LdFMSUUAAAAAIXRxfSlQ6gkpE9-jjAHZGs0DHBR"></div>
                        </div>

                        <%--Sign Up button and link to RegistrationAttempt Servlet--%>
                        <div class="text-center">
                            <button type="submit" id="submit" class="btn btn-primary">sign up</button>
                        </div>

                        <br>

                        <%--GOOGLE SIGN-IN BUTTON--%>
                        <!-- In the callback, you would hide the gSignInWrapper element on a
successful sign in -->

                        <div id="gSignInDiv">
                            <div id="gSignInWrapper">
                                <div id="customBtn" class="customGPlusSignIn">
                                    <span class="icon"></span>
                                    <span class="buttonText" style="text-align: left;">Google Sign Up</span>
                                </div>
                            </div>
                        </div>
                            <div id="name"></div>

                        <br>

                        <a class="text-right" href="Login">or log in here</a>

                        <%--Selection of additional user feedback for different registration errors--%>
                        <c:choose>
                            <c:when test="${param.registrationStatus == 'passwordMismatch'}">
                                <br>
                                <p style="color: red">your passwords do not match, please try again</p>
                            </c:when>
                            <c:when test="${param.registrationStatus == 'exists'}">
                                <br>
                                <p style="color: red">the username: ${param.username} already exists, please try
                                    again</p>
                            </c:when>
                            <c:when test="${param.registrationStatus == 'invalid'}">
                                <br>
                                <p style="color: red">your chosen username or password is invalid, please try a
                                    different
                                    combination</p>
                            </c:when>
                            <c:when test="${param.registrationStatus == 'dbConn'}">
                                <br>
                                <p style="color: red">the system could not connect to the database right now, please try
                                    again
                                    soon</p>
                            </c:when>
                            <c:when test="${param.registrationStatus == 'recaptchaNull'}">
                                <br>
                                <p style="color: red">you did not complete the recaptcha validation</p>
                            </c:when>
                            <c:when test="${param.registrationStatus == 'invalidGoogleSignIn'}">
                                <br>
                                <p style="color: red">your google sign in details were incorrect, please try again</p>
                            </c:when>

                        </c:choose>
                        <%------------------------------------------------------------------------------%>

                    </fieldset>
                </div>
            </form>
        </div>
    </div>
</div>

<%@include file="BodyStylingLinks.jsp" %>

<script>
    function checkForSpaces(textFieldInput) {
        var textFieldInputTest = textFieldInput.value;
        if (textFieldInputTest.replace(/\s/g, "").length == 0 && (textFieldInputTest.length != 0)) {
            console.log("false");
            textFieldInput.setCustomValidity("Please enter a caption");
        } else {
            console.log("passed");
            textFieldInput.setCustomValidity("");
        }
    }

</script>


<script>startApp();</script>

</body>
</html>
