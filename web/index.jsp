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

<%--Page Specific Styling--%>
<style>

    .homepageVideo {
        height: 100%;
        width: 100%;
        background-position: center;
        background-repeat: no-repeat;
        background-size: cover;
    }

    html {
        overflow-y: hidden;
    }

    .homepageImage {
        /*background-image: url("Multimedia/BackgroundImages/2.jpg");*/
        height: 100%;
        background-position: center;
        background-repeat: no-repeat;
        background-size: cover;
        background-color: #def4f7;
    }

</style>

<body>



<div class="homepageVideo toggleViewPort">

    <%--<video autoplay loop muted src="Multimedia/indexVideo.mp4"></video>--%>
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
<script>

    // Size of the viewport
    var w = Math.max(document.documentElement.clientWidth, window.innerWidth || 0);
    var h = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);

    if ( w > 1200 && h > 500) {
        $(".homepageVideo").prepend("<video autoplay loop muted src='Multimedia/indexVideo.mp4'></video>");
    } else {
        $('div.toggleViewPort').removeClass("homepageVideo");
        $(".toggleViewPort").addClass("homepageImage");
        $(".homepageImage").css("background-image", "url('Multimedia/BackgroundImages/7.jpg')")

    }


</script>
</body>
<%@include file="BodyStylingLinks.jsp" %>
</html>
