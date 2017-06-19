/**
 * Created by cbla080 on 8/06/2017.
 */

/*This JavaScript file contains the toggle-able comment addition form*/

$(document).ready(function () {

    /*When add comment button is clicked, either show input form or hide input form*/
    $(".add-comment-button").click(function () {

        /*Pull the username and article id*/
        var username = $("#userdetails").text();
        var articleID = $("#articleid").text();

        /*If the add comment form is open and button is clicked, close it*/
        if ($(this).hasClass("dialog_open")) {
            $(this).removeClass("dialog_open");
            $(this).html("Add New Comment");
            $(this).next(".add_comment_panel_body").remove();

            /*If the add comment form is hidden and button is clicked, open it*/
        } else {
            $(this).addClass("dialog_open");
            $(this).html("<p><i class='fa fa-pencil' aria-hidden='true'></i> COMMENT</p>");
            var add_article_form = "<div class='panel-body add_comment_panel_body'>" + "<form action='AddCommentAttempt' method='POST'>" +
                "<div id='addComment'>" +
                "<label for='comment_body'>Comment:</label>" +
                "<textarea rows='4' cols='50' name='comment_body' id='comment_body'></textarea>" +
                "<input type='hidden' name='username' value='" + username + "'>" +
                "<input type='hidden' name='article_id' value='" + articleID + "'>" +
                "<input type='hidden' name='parent_comment_id' value=''>" +
                "<input class='btn btn-default btn-sm' type='submit' name='submit' value='Post Comment'>" +
                "</div>" + "</form>";

            $(add_article_form).insertAfter($('.add-comment-button'));
        }
    })
});

/*---------------------------*/
/*End of JavaScript file*/
/*---------------------------*/

