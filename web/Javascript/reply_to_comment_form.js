/**
 * Created by cbla080 on 8/06/2017.
 */
$(document).on("click", ".add_reply", function () {
    var username = $("#userdetails").text();
    var articleID = $("#articleid").text();
    var button = $(this);
    var parentID = button.val();
    var commentDiv = button.parent();

    if (button.hasClass("dialog_open")){
        button.removeClass("dialog_open");
        button.html("Reply");
        button.parent().next(".add_comment_panel_body").remove();

    } else {
        button.addClass("dialog_open");
        button.html("<p><i class='fa fa-pencil' aria-hidden='true'></i> REPLY</p>");
        var add_article_form = "<div class='panel-body add_comment_panel_body'>" + "<form action='AddCommentAttempt' method='POST'>" +
            "<div id='addComment'>" +
            "<label for='comment_body'>Comment:</label>" +
            "<textarea rows='4' cols='50' name='comment_body' id='comment_body'></textarea>" +
            "<input type='hidden' name='username' value='"+ username +"'>" +
            "<input type='hidden' name='article_id' value='"+ articleID +"'>" +
            "<input type='hidden' name='parentComment_id' value='"+ parentID +"'>" +
            "<input type='submit' name='submit' value='Post Comment'>" +
            "</div>" + "</form>";

        $(add_article_form).insertAfter(commentDiv);
    }
});