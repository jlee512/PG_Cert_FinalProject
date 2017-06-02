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

        <c:redirect url="/Content?username=${sessionScope.userDetails.username}"/>

    </c:if>

    <title>Welcome! Register an Account</title>

</head>


<body>

<div class="card" style="margin: 2%; padding: 3%">
    <form action="/RegistrationAttempt" method="POST">
        <div>
        <h1 class="text-center">sign up</h1>

        <fieldset id="fieldset">

            <%--Username input--%>
            <div class="md-form">
                <input class="form-control" type="text" id="username" name="username"
                       onchange="checkForSpaces(this)"/>
                <label for="username">New username</label>
            </div>


            <%--Nickname input--%>
            <div class="md-form">
                <input class="form-control" type="text" id="nickname" name="nickname"
                       onchange="checkForSpaces(this)"/>
                <label for="nickname">New nickname</label>
            </div>


            <%--Email input--%>
            <div class="md-form">
                <input class="form-control" type="email" id="email" name="email"
                       onchange="checkForSpaces(this)"/>
                <label for="email">Your email</label>
            </div>


            <%--Password input--%>
            <div class="md-form">
                <input class="form-control" type="password" id="password" name="password" minlength="5"
                       onchange="checkForSpaces(this)"
                       required/>
                <label for="password">Your password</label>
            </div>

                <%--PASSWORD VERIFICATION--%>
            <div class="md-form">
                <input class="form-control" type="password" id="passwordVerify" name="passwordVerify" minlength="5"
                       onchange="checkForSpaces(this)" required/>
                <label for="passwordVerify">Verify password</label>
            </div>


            <%--Sign Up button and link to RegistrationAttempt Servlet--%>
                <div class="text-center">
            <input type="submit" id="submit" class="btn btn-primary" value="sign up">
                </div>
                <a class="text-right" href="/Login">or log in here</a>
            <%--Selection of additional user feedback for different registration errors--%>
            <c:choose>
                <c:when test="${param.registrationStatus == 'passwordMismatch'}">
                    <br>
                    <p style="color: red">your passwords do not match, please try again</p>
                </c:when>
                <c:when test="${param.registrationStatus == 'exists'}">
                    <br>
                    <p style="color: red">the username: ${param.username} already exists, please try again</p>
                </c:when>
                <c:when test="${param.registrationStatus == 'invalid'}">
                    <br>
                    <p style="color: red">your chosen username or password is invalid, please try a different
                        combination</p>
                </c:when>
                <c:when test="${param.registrationStatus == 'dbConn'}">
                    <br>
                    <p style="color: red">the system could not connect to the database right now, please try again
                        soon</p>
                </c:when>
            </c:choose>


        </fieldset>
        </div>
    </form>
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
