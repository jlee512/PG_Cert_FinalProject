/**
 * Created by cbla080 on 12/06/2017.
 */

/*-------------------------------------------------------*/
/*This JavaScript file is used to edit comments on a given article.
/*-------------------------------------------------------*/

//When edit button is clicked, open the comment editing form.
$(document).on("click", ".edit_comment", function () {

    //Get the article id from the cookie.
    var articleID = getArticleID();

    //Get the comment id from the button.
    var button = $(this);
    var comment_id = button.val();

    //Get the container div for the comment being edited.
    var commentContainerDiv = button.closest(".panel-info");
    var commentBodyDiv = commentContainerDiv.find(".panel-body:first");
    var commentHeadingDiv = commentContainerDiv.find(".panel-heading:first");

    //Get the current content of the comment being edited.
    var commentContent = commentBodyDiv.text();

    //If the editing form is open when the button is clicked, close it.
    if (button.hasClass("edit_open")){
        $(".panel-body").show();
        button.removeClass("edit_open");
        button.html("Edit");
        $(".edit_comment_body").remove();


        //If the editing form is closed, open it.
    } else {
        button.addClass("edit_open");
        button.html("<p><i class='fa fa-pencil' aria-hidden='true'></i>Edit</p>");
        var edit_comment_form = "<div class='panel-body edit_comment_body'>" + "<form action='EditComment' method='POST'>" +
            "<div id='editComment'>" +
            "<label for='comment_body'>Comment:</label>" +
            "<textarea rows='4' cols='50' name='comment_body' id='comment_body'>" + commentContent + "</textarea>" +
            "<input type='hidden' name='article_id' value='"+ articleID +"'>" +
            "<input type='hidden' name='comment_id' value='"+ comment_id +"'>" +
            "<input class='btn btn-default btn-sm' type='submit' name='submit' value='Update Comment'>" +
            "</div>" + "</form>";

        commentBodyDiv.hide();
        $(edit_comment_form).insertAfter(commentHeadingDiv);
    }

});
function getArticleID () {
    /*Create the cookie search text*/
    var cookieName = "article_id=";

    /*Decode the cookie string */
    var decodedCookie = decodeURIComponent(document.cookie);

    /*Split the document.cookie result based on semi colons*/
    var cookieArray = decodedCookie.split(';');

    for (var i = 0; i < cookieArray.length; i++) {
        /*Access cookies one-by-one (crumb-by-crumb XD)*/
        var cookie = cookieArray[i];
        /*Trim any whitespace*/
        cookie = cookie.replace(/ /g, '');
        /*If the cookie is found, return the value of the cookie*/
        if (cookie.indexOf(cookieName) == 0 ) {
            return cookie.substring(cookieName.length, cookie.length);
        }
    }
    /*Return blank if not found*/
    return "";
}
/*---------------------------*/
/*End of JavaScript file*/
/*---------------------------*/