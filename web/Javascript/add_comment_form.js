/**
 * Created by cbla080 on 8/06/2017.
 */
$(document).ready(function () {
    $(".add-comment-button").click(function () {

        var username = $("#userdetails").text();
    var articleID = $("#articleid").text();

    console.log($(this).hasClass("dialog_open"));

    if ($(this).hasClass("dialog_open")){
        $(this).removeClass("dialog_open");
        $(this).html("Add New Comment");
        $(this).next(".add_comment_panel_body").remove();

    } else {
        $(this).addClass("dialog_open");
        $(this).html("<p><i class='fa fa-pencil' aria-hidden='true'></i> COMMENT</p>");
        var add_article_form = "<div class='panel-body add_comment_panel_body'>" + "<form action='AddCommentAttempt' method='POST'>" +
            "<div id='addComment'>" +
            "<label for='comment_body'>Comment:</label>" +
            "<textarea rows='4' cols='50' name='comment_body' id='comment_body'></textarea>" +
            "<input type='hidden' name='username' value='"+ username +"'>" +
            "<input type='hidden' name='article_id' value='"+ articleID +"'>" +
            "<input type='hidden' name='parentComment_id' value=''>" +
            "<input type='submit' name='submit' value='Post Comment'>" +
            "</div>" + "</form>";

        $(add_article_form).insertAfter($('.add-comment-button'));
    }
})
});



