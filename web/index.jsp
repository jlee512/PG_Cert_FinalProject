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
    <title>$Title$</title>
</head>

<style>

    #homepageVideo {
        height: 100%;
        width: 100%;
        background-position: center;
        background-repeat: no-repeat;
        background-size: cover;
    }

</style>

<body>

<div>
    <video id="homepageVideo" autoplay loop muted src="Multimedia/Dog.mp4"></video>
    <div class="vertical-center text-center" style="position: absolute;">
       <div class="container">
            <div class="screen-heading" >
                <h1 style="color: white;" >Start your own blog,<br>because we've all got something to say.</h1>
                <br>
                <br>
                <a href="/Registration.jsp">
                    <button style="color: beige" type="button" class="btn">Get started</button>
                </a>
            </div>
        </div>
    </div>
</div>
</body>
<%@include file="BodyStylingLinks.jsp" %>
</html>
