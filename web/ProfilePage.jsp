<%--
  Created by IntelliJ IDEA.
  User: ycow194
  Date: 5/06/2017
  Time: 12:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${sessionScope.userDetails.username}'s profile</title>
    <%@ include file="HeadStylingLinks.jsp" %>
</head>
<link rel="shortcut icon" type="image/png" href="Multimedia/favicon.png">
<body>
<%@include file="Navbar.jsp" %>

<div class="col-sm-3 panel panel-default" id="profileContent">

    <img src="images/remarkables8145706.jpg" class="img-circle img-responsive center-block" id="mountainImage">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Profile</h3>
        </div>

        <div class="panel-body">
            <p><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Full name</p>
            <p><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Location</p>
        </div>
    </div>

</div>




</body>
<%@include file="BodyStylingLinks.jsp" %>
</html>
