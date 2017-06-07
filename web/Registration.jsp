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
                                   onchange="checkForSpaces(this)"/>
                            <label for="email">Your email</label>
                        </div>

                        <%--Password input--%>
                        <div class="md-form">
                            <i class="fa fa-unlock-alt prefix"></i>
                            <input class="form-control" type="password" id="password" name="password" minlength="5"
                                   onchange="checkForSpaces(this)"
                                   required/>
                            <label for="password">Your password</label>
                        </div>

                        <%--PASSWORD VERIFICATION--%>
                        <div class="md-form">
                            <i class="fa fa-lock prefix"></i>
                            <input class="form-control" type="password" id="passwordVerify" name="passwordVerify"
                                   minlength="5"
                                   onchange="checkForSpaces(this)" required/>
                            <label for="passwordVerify">Verify password</label>
                        </div>

                        <%--GOOGLE RECAPTCHA--%>
                        <div class="md-form" style="opacity: 1">
                            <div class="g-recaptcha"
                                 data-sitekey="6LdfICQUAAAAAKDVV0dEaobS0ecRWPLFvdfKTyzn"></div>
                        </div>

                        <%--Sign Up button and link to RegistrationAttempt Servlet--%>
                        <div class="text-center">
                            <button type="submit" id="submit" class="btn btn-primary">sign up</button>
                        </div>
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
                        </c:choose>
                        <%------------------------------------------------------------------------------%>

                    </fieldset>
                </div>
            </form>
        </div>
    </div>
</div>
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
<%@include file="BodyStylingLinks.jsp" %>
</body>
</html>
