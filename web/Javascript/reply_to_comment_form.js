/**
 * Created by cbla080 on 8/06/2017.
 */

/*-------------------------------------------------------*/
/*This JavaScript file is used for the comment reply interface*/
/*-------------------------------------------------------*/

//When the reply button is clicked, open the reply form.
$(document).on("click", ".add_reply", function () {
    //Get key values from the page.
    var username = $("#userdetails").text();
    var articleID = $("#articleid").text();

    //Get the parent comment ID from the reply button.
    var button = $(this);
    var parentID = button.val();
    var commentDiv = button.parent();

    //If reply form is open, close the form when button is clicked.
    if (button.hasClass("dialog_open")){
        button.removeClass("dialog_open");
        button.html("Reply");
        button.parent().next(".add_comment_panel_body").remove();

    } else {
        //If reply form is not open, open the form.
        button.addClass("dialog_open");
        button.html("<p><i class='fa fa-pencil' aria-hidden='true'></i> REPLY</p>");

        /*The edit comment form*/
        var add_comment_form = "<div class='panel-body add_comment_panel_body'>" + "<form action='AddCommentAttempt' method='POST'>" +
            "<div id='addComment'>" +
            "<label for='comment_body'>Comment:</label>" +
            "<textarea rows='4' cols='50' name='comment_body' id='comment_body'></textarea>" +
                //Attach relevent information in hidden form fields to send to the servlet.
            "<input type='hidden' name='username' value='"+ username +"'>" +
            "<input type='hidden' name='article_id' value='"+ articleID +"'>" +
            "<input type='hidden' name='parent_comment_id' value='"+ parentID +"'>" +
            "<input class='btn btn-default btn-sm' type='submit' name='submit' value='Post Comment'>" +
            "</div>" + "</form>";

        $(add_comment_form).insertAfter(commentDiv);
    }
});

/*---------------------------*/
/*End of JavaScript file*/
/*---------------------------*/