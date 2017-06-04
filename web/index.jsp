<%--
  Created by IntelliJ IDEA.
  User: jlee512
  Date: 31/05/2017
  Time: 3:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="HeadStylingLinks.jsp" %>
    <title>Welcome to Team While E Coyote's Website!</title>
</head>

<style>

    #homepageVideo {
        height: 100%;
        background-position: center;
        background-repeat: no-repeat;
        background-size: cover;
    }

</style>

<body>
<div class="panel panel-default" style="background-color: transparent">
    <div class="panel-body">
        <ul>
            <li>
                <a href="Login">
                    <button class="button">Log in</button>
                </a>
            </li>
        </ul>
    </div>
</div>
<div>
    <video id="homepageVideo" autoplay loop muted src="/Multimedia/Swimming.mp4"></video>
    <div class="vertical-center">
       <div class="container">
            <div class="text-center">
                <h1>Write your journey.</h1>
                <br>
                <br>
                <a href="Registration">
                    <button type="button" class="btn">Get started</button>
                </a>
            </div>
        </div>
    </div>
</div>
</body>
<%@include file="BodyStylingLinks.jsp" %>
</html>
