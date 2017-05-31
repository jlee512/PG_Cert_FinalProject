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

    <%--If user is logged in (i.e. the login status is stored in the current session, return to the content page--%>
    <c:if test="${loginStatus == 'active'}">

        <c:redirect url="/Content?username=${sessionScope.userDetails.username}"/>

    </c:if>

    <title>Welcome! Register an Account</title>

    <style type="text/css">

        #fieldset {
            border: none;
        }

    </style>

</head>


<body>
<h1>sign up</h1>

<form action="/RegistrationAttempt" method="POST">

    <fieldset id="fieldset">

        <%--Username input--%>
        <label for="username">username</label>
        <br>
        <input type="text" id="username" name="username" placeholder="username" onchange="checkForSpaces(this)"/>

        <br><br>

        <%--Nickname input--%>
        <label for="nickname">username</label>
        <br>
        <input type="text" id="nickname" name="nickname" placeholder="nickname" onchange="checkForSpaces(this)"/>

        <br><br>

        <%--Email input--%>
        <label for="email">username</label>
        <br>
        <input type="email" id="email" name="email" placeholder="email" onchange="checkForSpaces(this)"/>

        <br><br>

        <%--Password input--%>
        <label for="password">password</label>
        <br>
        <input type="password" id="password" name="password" placeholder="password" onchange="checkForSpaces(this)"
               required/>

        <br>
        <br>
        <%--PASSWORD VERIFICATION--%>
        <input type="password" id="passwordVerify" name="passwordVerify" placeholder="verify your password"
               onchange="checkForSpaces(this)" required/>

        <br>
        <br>

        <%--Sign Up button and link to RegistrationAttempt Servlet--%>
        <input type="submit" id="submit" value="sign up">
        <a href="/Login">or log in here</a>

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
                <p style="color: red">the system could not connect to the database right now, please try again soon</p>
            </c:when>
        </c:choose>


    </fieldset>

</form>

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

</body>
</html>
