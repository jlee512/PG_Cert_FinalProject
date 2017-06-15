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

    <link rel="shortcut icon" type="image/png" href="Multimedia/favicon.png">

    <meta name="google-signin-client_id"
          content="17619298298-hlb3n0ra5pkquu73jbs8sir2m5i4b4b8.apps.googleusercontent.com">

    <script src="https://apis.google.com/js/api:client.js"></script>
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
                            <div id="gSignInDiv">
                                <div id="gSignInWrapper">
                                    <div id="customBtn" class="customGPlusSignIn">
                                        <span class="icon"></span>
                                        <span class="buttonText" style="text-align: left;">Google Sign In</span>
                                    </div>
                                </div>
                            </div>
                            <div id="name"></div>

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

<%--When keyboard appears, the content is not obscured. MUST BE ENCLOSED IN SEPERATE SCRIPT TAGS--%>
<script type="text/javascript">
    var isAndroid = navigator.userAgent.toLowerCase().indexOf("android") > -1; //&& ua.indexOf("mobile");
    if(isAndroid) {
        document.write('<meta name="viewport" content="width=device-width,height='+window.innerHeight+', initial-scale=1.0">');
    }
</script>

<%--Script inclusions--%>

<%--Google OAuth--%>
<script>startApp();</script>

</body>
</html>
