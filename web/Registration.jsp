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


    <%--Font awesome--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Latest html skins from mdbootstrap.com-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.3.0/css/mdb.min.css">


</head>


<body>

<div class="container">
    <form action="/RegistrationAttempt" method="POST">
        <h1>sign up</h1>
        <fieldset id="fieldset">

            <%--Username input--%>
            <label for="username">username</label>
            <br>
            <input type="text" id="username" name="username" placeholder="username" onchange="checkForSpaces(this)"/>

            <br><br>

            <%--Nickname input--%>
            <label for="nickname">nickname</label>
            <br>
            <input type="text" id="nickname" name="nickname" placeholder="nickname" onchange="checkForSpaces(this)"/>

            <br><br>

            <%--Email input--%>
            <label for="email">email</label>
            <br>
            <input type="email" id="email" name="email" placeholder="email" onchange="checkForSpaces(this)"/>

            <br><br>

            <%--Password input--%>
            <label for="password">password</label>
            <br>
            <input type="password" id="password" name="password" minlength="5" placeholder="password" onchange="checkForSpaces(this)"
                   required/>

            <br>
            <br>
            <%--PASSWORD VERIFICATION--%>
            <input type="password" id="passwordVerify" name="passwordVerify" minlength="5" placeholder="verify your password"
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
                    <p style="color: red">the system could not connect to the database right now, please try again
                        soon</p>
                </c:when>
            </c:choose>


        </fieldset>

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

<%--Include jQuery--%>
<script
        src="https://code.jquery.com/jquery-3.2.1.min.js"
        integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
        crossorigin="anonymous"></script>

<%--Include bootstrap tooltips--%>

<%--Bootstrap core JavaScript--%>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<%--MDB core JavaScript--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.3.0/js/mdb.min.js"></script>

</body>
</html>
