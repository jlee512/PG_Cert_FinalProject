<%--
  Created by IntelliJ IDEA.
  User: ycow194
  Date: 5/06/2017
  Time: 3:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%--Import JSTL tag library--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--Navbar constructed using Bootstrap (note this file must be included with Head Styling Links and Body Styling Links--%>
<nav class="navbar navbar-fixed navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar" style="background-color: #f9a825;">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="ProfilePage?username=${sessionScope.userDetails.username}" style="padding-top: 0; height: 50px; left: 15px;"><img src="${sessionScope.userDetails.profile_picture}" style="height:50px;"></a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-left">
                <li><a href="ProfilePage?username=${sessionScope.userDetails.username}" style="font-size: 18px;">Profile</a></li>
                <li><a href="Content" style="font-size: 18px">Articles</a></li>
                <li><a href="MultimediaGallery" style="font-size: 18px">Gallery</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="LogoutAttempt?username=${sessionScope.userDetails.username}" style="font-size: 18px">Logout</a></li>
            </ul>
        </div><!-- /.nav-collapse -->
    </div><!-- /.container -->
</nav>
<!-- /.navbar -->
