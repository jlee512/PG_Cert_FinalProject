/**
 * Created by cbla080 on 8/06/2017.
 */

/*-------------------------------------------------------*/
/*This JavaScript file is used to reply to comments on a given article.
 /*-------------------------------------------------------*/

/*When the reply button is clicked, open the reply form.*/
$(document).on("click", ".add_reply", function () {

    /*Get username from the JSP.*/
    var username = $("#userdetails").text();

    /*Get article id from the cookie.*/
    var articleID = getArticleID();

    /*Get the parent comment ID from the reply button.*/
    var button = $(this);
    var parentID = button.val();
    var commentDiv = button.parent();
    console.log(commentDiv.prop('nodeName'));
    console.log(commentDiv.attr('class'));

    /*If reply form is open, close the form when button is clicked.*/
    if (button.hasClass("dialog_open")){
        button.removeClass("dialog_open");
        button.html("Reply");
        button.parent().next(".add_comment_panel_body").remove();

    } else {
        /*If reply form is not open, open the form.*/
        button.addClass("dialog_open");
        button.html("<p><i class='fa fa-pencil' aria-hidden='true'></i> REPLY</p>");
        var add_article_form = "<div class='panel-body add_comment_panel_body'>" + "<form action='AddCommentAttempt' method='POST'>" +
            "<div id='addComment'>" +
            "<label for='comment_body'>Comment:</label>" +
            "<textarea rows='4' cols='50' name='comment_body' id='comment_body'></textarea>" +

                /*Attach relevant information in hidden form fields to send to the servlet.*/
            "<input type='hidden' name='username' value='"+ username +"'>" +
            "<input type='hidden' name='article_id' value='"+ articleID +"'>" +
            "<input type='hidden' name='parent_comment_id' value='"+ parentID +"'>" +
            "<input class='btn btn-default btn-sm' type='submit' name='submit' value='Post Comment'>" +
            "</div>" + "</form>";

        $(add_article_form).insertAfter(commentDiv);
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