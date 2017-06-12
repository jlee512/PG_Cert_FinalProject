<%--
  Created by IntelliJ IDEA.
  User: Tammy
  Date: 12/06/2017
  Time: 5:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="HeadStylingLinks.jsp" %>
    <title>Gallery</title>
</head>
<body>





        <div class="news_feed">
            <%--Articles are inserted here via AJAX request--%>
        </div>

    <div class="loader-wrapper" style="text-align: center;">
        <div class="loader" style="display: inline-block;"></div>
    </div>
    <br class="loader-wrapper">
    <br class="loader-wrapper">
    <div class="loaded-wrapper" style="text-align: center;">
        <div id="loaded1" style="display: inline-block;"></div>
        <div id="loaded2" style="display: inline-block;"></div>
        <div id="loaded3" style="display: inline-block;"></div>
        <div id="loaded4" style="display: inline-block;"></div>
    </div>
    <br class="loaded-wrapper">
    <br class="loaded-wrapper">





<%@ include file="BodyStylingLinks.jsp" %>
<script type="application/javascript" src="Javascript/multimedia_display.js"></script>
</body>




</html>
