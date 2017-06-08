<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="HeadStylingLinks.jsp" %>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<form action="AddAnArticleAttempt" method="POST">

    <fieldset>

        <!--Article Title Input-->
        <div id="addArticleTitle">
        <label for="article_title_input">Article Title:</label>
        <input type="text" id="article_title_input" name="article_title_input" class="article_input_form" style="max-width: 100%; maxlength="100" required>
        </div>

        <!--Article Body Input-->
        <div id="addArticleBody" class="form-group">
            <label for="article_body_input">Article Body:</label>
            <textarea name="article_body_input" id="article_body_input" class="form-control" rows="5" style="max-width: 100%;" required></textarea>
        </div>
        <button type="addArticle" id="addArticle">Post Article</button>


    </fieldset>

</form>

<%@include file="BodyStylingLinks.jsp" %>

</body>
</html>