/**
 * Created by cbla080 on 8/06/2017.
 */
$(document).ready(function () {
    $(".add-comment-button").click(function () {

        var username = $("#userdetails").text();
    var articleID = $("#articleid").text();

    if ($(".dialog_open")[0]){
        $(".add-comment-button").removeClass("dialog_open");
        $(".add-comment-button").html("Add New Comment")
        $(".add_comment_panel_body").remove();

    } else {
        $(".add-comment-button").addClass("dialog_open");
        $(".add-comment-button").html("<p><i class='fa fa-pencil' aria-hidden='true'></i> Have your say</p>");
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



