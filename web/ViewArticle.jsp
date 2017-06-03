<%--
  Created by IntelliJ IDEA.
  User: cbla080
  Date: 3/06/2017
  Time: 12:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:if test="${loginStatus != 'active'}">
        <c:redirect url="/Login"/>
    </c:if>
    <title>${article.article_title}</title>
</head>
<body>
<h1>${article.article_title}</h1>
<br>
<h3>${username}</h3>
<br>
<p>${article.article_body}</p>
<br>
<em>${article.article_date}</em>
<hr>
<c:forEach var="comment" items="${commentList}">
    <div>
        <p>${comment.content}</p>
        <a href="AddCommentForm.jsp">Reply</a>
    </div>
</c:forEach>
</body>
</html>
