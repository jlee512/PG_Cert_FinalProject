<%--
  Created by IntelliJ IDEA.
  User: Julian
  Date: 30-May-17
  Time: 9:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="login.system.dao.User" %>

<%
    /*Prevents cache access of content/changepassword/logout pages*/
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
    User user = (User) session.getAttribute("userDetails");
    if (user == null) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("Login");
        dispatcher.forward(request, response);
    }
%>

<html>
<head>
    <%@include file="HeadStylingLinks.jsp" %>
    <title>Password change</title>
</head>

<style type="text/css">

    #currentPassword, #newPassword, #newPasswordVerify {
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

<%--If user is not logged in, re-direct to the login page--%>
<c:if test="${loginStatus != 'active'}">
    <c:choose>

        <%--If the username parameter has been stored, prepopulate the username field with the login--%>
        <c:when test="${param.username != null}">
            <c:redirect url="Login?username=${param.username}"/>
        </c:when>
        <%--Otherwise direct to blank login page--%>
        <c:otherwise>
            <c:redirect url="Login"/>
        </c:otherwise>
    </c:choose>
</c:if>
<%---------------------------------------------------------%>

<div class="backGroundImage">
    <div class="vertical-center">
        <div class="container setOpacity">
            <form action="ChangePasswordAttempt" method="POST">
                <fieldset id="fieldset">

                    <%--Username--%>
                    <div class="text-center">
                        <h3>${param.username}, please select your new password</h3>
                    </div>

                    <%--Current Password--%>
                    <div class="md-form">
                        <input class="form-control" type="password" id="currentPassword" name="currentPassword"
                               oninvalid="this.setCustomValidity('please enter your password')"
                               oninput="this.setCustomValidity('')"
                               required>
                        <label for="currentPassword">Current password</label>
                    </div>

                    <%--New Password (1)--%>
                    <div class="md-form">
                        <input class="form-control" type="password" id="newPassword" name="newPassword"
                               oninvalid="this.setCustomValidity('please enter your password')"
                               oninput="this.setCustomValidity('')"
                               required>
                        <label for="newPassword">New password</label>
                    </div>

                    <%--New Password (2)--%>
                    <div class="md-form">
                        <input class="form-control" type="password" id="newPasswordVerify" name="newPasswordVerify"
                               oninvalid="this.setCustomValidity('please enter your password')"
                               oninput="this.setCustomValidity('')"
                               required>
                        <label for="newPasswordVerify">Verify password</label>
                    </div>

                    <%--Submit button--%>
                    <div class="text-center">
                        <input class="btn btn-primary btn-rounded " type="submit" id="submit" value="change password">
                    </div>

                    <%--Selection of additional user feedback for different registration errors--%>
                    <c:choose>
                        <c:when test="${param.passwordChangeStatus == 'incorrect'}">
                            <br>
                            <p style="color: red">your current password is not correct, please try again</p>
                        </c:when>
                        <c:when test="${param.passwordChangeStatus == 'newPasswordMismatch'}">
                            <br>
                            <p style="color: red">the new passwords do not match, please try again</p>
                        </c:when>
                        <c:when test="${param.passwordChangeStatus == 'invalid'}">
                            <br>
                            <p style="color: red">your chosen password is invalid, please try a different password</p>
                        </c:when>
                        <c:when test="${param.passwordChangeStatus == 'dbConn'}">
                            <br>
                            <p style="color: red">the system could not connect to the database right now, please try
                                again
                                soon</p>
                        </c:when>
                    </c:choose>
                    <%--------------------------------------------------------------------------%>

                </fieldset>
            </form>
        </div>
    </div>
</div>

<%@include file="BodyStylingLinks.jsp" %>

</body>
</html>