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
    <title>Escapades</title>
</head>

<style>

    #homepageVideo {
        height: 100%;
        width: 100%;
        background-position: center;
        background-repeat: no-repeat;
        background-size: cover;
    }

    html {
        overflow-y: hidden;
    }

</style>

<body>

<div id="homepageVideo">
    <video autoplay loop muted src="Multimedia/indexVideo.mp4"></video>
    <div class="vertical-center " style="position: absolute; width: 100%">
        <div class="text-center">
            <div class=" container">
                <h1 style="color: white;">Start your own blog,<br>because we've all got something to say.</h1>
                <br>
                <br>
                <button onclick="location.href = 'Registration'"  class="btn btn-primary btn-rounded">Get started</button>
                <button onclick="location.href = 'Login'" class="btn btn-default btn-rounded">Log in</button>
            </div>
        </div>
    </div>
</div>

</body>
<%@include file="BodyStylingLinks.jsp" %>
</html>
