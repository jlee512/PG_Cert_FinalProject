<%--
  Created by IntelliJ IDEA.
  User: ycow194
  Date: 5/06/2017
  Time: 3:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar navbar-fixed navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="Content">Homepage</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="ProfilePage?username=${sessionScope.userDetails.username}" style="font-size: 18px">My Profile</a></li>
                <li><a href="Logout?username=${sessionScope.userDetails.username}" style="font-size: 18px">Logout</a></li>
            </ul>
        </div><!-- /.nav-collapse -->
    </div><!-- /.container -->
</nav>
<!-- /.navbar -->
