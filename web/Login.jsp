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
    <title>Login</title>
</head>

<style type="text/css">

    #username, #password {
        border-top: none;
        border-left: none;
        border-right: none;
        border-bottom: 1px solid lightgray;
    }

    #fieldset {
        border: none;
    }

</style>

<body>

<h1>sign in</h1>

<form action="/LoginAttempt" method="POST">

    <%--If user is logged in (i.e. the login status is stored in the current session, return to the content page--%>
    <c:if test="${loginStatus == 'active'}" >

        <c:redirect url="/Content?username=${sessionScope.userDetails.username}" />

    </c:if>

    <%--jsp scriptlet to assess whether an invalid password was entered. If so, prepopulate username field with original username--%>
    <%
        String usernameAttempt = request.getParameter("username");
        String usernamePrepopulate = "";
        if (usernameAttempt != null){
            usernamePrepopulate = usernameAttempt;
        }
    %>

    <%--Form input for login page--%>
    <fieldset id="fieldset">
        <br>
        <%--USERNAME--%>
        <input type="text" id="username" name="username" placeholder="email or username"
               oninvalid="this.setCustomValidity('please enter your username')" oninput="this.setCustomValidity('')" value="<%=usernamePrepopulate%>"
               required>
        <br><br>

        <%--PASSWORD--%>
        <input type="password" id="password" name="password" placeholder="password"
               oninvalid="this.setCustomValidity('please enter your password')" oninput="this.setCustomValidity('')"
               required>

        <br>
        <br>
        <input type="submit" id="submit" value="sign in">
        <a href="/Registration">sign up</a>

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
        <%
            }
        %>

    </fieldset>

</form>

</body>
</html>
