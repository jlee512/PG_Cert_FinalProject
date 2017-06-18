/**
 * Created by cbla080 on 5/06/2017.
 */

var commentPara = '<div class="panel panel-info">' +
    '<div class ="comment-content">' +
    '<div class="panel-heading">INSERT COMMENT HEADING</div>'+
    '<div class="panel-body">INSERT COMMENT BODY</div>' +
    '</div>' +
    '<div class="buttons"></div>' +
    '</div>';

var loaderWrapper = '<div class="loader-wrapper" style="margin-left: 3%; text-align: center;">' +
    '<div class="loader" style="display: inline-block; text-align: center;"></div>' +
    '</div>';

$(document).on("click", ".show_replies", function () {
    //Show replies
    var username = $("#userdetails").text();
    var authorUsername = $("#author").text();
    var button = $(this);
    $(button).mouseup(function() { this.blur() });
    var parentID = button.val();
    var commentContainer = $('<div style="margin-left: 5%"></div>');
    var buttonsDiv = button.parent();

    buttonsDiv.append(loaderWrapper);



    /*If the replies are displayed and the button is pressed, remove the comments*/
    if (button.hasClass("comments-displayed")) {
        console.log("comments loaded");
        buttonsDiv.find(".loader-wrapper").hide();
        buttonsDiv.find(".loader-wrapper").remove();
        button.removeClass("comments-displayed");
        button.html("Show Replies");
        console.log(buttonsDiv.prop('nodeName'));
        console.log(buttonsDiv.attr('class'));
        buttonsDiv.nextAll().remove();}



        /*If the replies are not displayed and the button is pressed, show the comments*/
        else
        {
            button.addClass("comments-displayed");
            $.ajax({
                url: "ShowNestedComments?parentCommentID=" + parentID,
                type: "GET",
                success: function loadNestedComments(msg) {
                    buttonsDiv.find(".loader-wrapper").show();

                    console.log(msg.length);

                    if (msg.length == 0){
                        /*Hide the loader picture, show the loaded underline and return that their are no further articles*/
                        $('.loader-wrapper').hide();
                        $('#loaded1, #loaded2, #loaded3, #loaded4').show();
                        moreComments = false;

                    } else {
                        for (i = 0; i < msg.length; i++) {

                            console.log("Test");

                            var commentContainerTemplate = $(commentPara);
                            var replyCommentsButtonPanel = $(commentContainerTemplate).find('.buttons');

                            var comment = msg[i];
                            var dateUnformatted = new Date(comment.timestamp);
                            var date = formatDate(dateUnformatted);


                /*--------Fillout the comment HTML template-----*/
                            commentContainerTemplate.find(".panel-heading").html('<p><strong><a href="PublicProfile?username='+ comment.author_username + '" style="color: #f9a825;">' + comment.author_username + '</a></strong>, ' + date + '</p>');
                            commentContainerTemplate.find(".panel-body").html("<p>" + comment.content + "</p>");

                /*--------Construct the buttons if required-----*/

                            /*Add a button to view replies if comment has replies*/
                            if (comment.is_parent) {
                                var viewRepliesButton = '<button type="button" class="show_replies btn btn-default btn-sm" value="' + comment.comment_id + '">Show Replies</button>';
                            }


                            /*Add a button to delete the comment if current user is comment author or article author*/
                            if (username == comment.author_username || username == authorUsername) {
                                var deleteButton = '<a href="DeleteComment?comment_id=' + comment.comment_id + '&article_id=' + getArticleID() + '&parent_comment_id=' + comment.parent_comment_id + '" class="btn btn-default btn-sm">Delete</a>'
                            }

                            if (username == comment.author_username) {
                                var editButton = '<button type="button" class="edit_comment btn btn-default btn-sm" value="' + comment.comment_id + '">Edit</button>';
                            }

                            /*Add a button to reply to the comment*/
                            var replyButton = '<button type="button" class="add_reply btn btn-default btn-sm" value="' + comment.comment_id + '">Reply</button>';

                            //Append the reply button.
                            replyCommentsButtonPanel.append(replyButton);

                            //If there are replies, append the Show Replies button.
                            if (comment.is_parent) {
                                replyCommentsButtonPanel.append(viewRepliesButton);
                            }

                            //If current user is authorized to delete the comment, append the delete button.
                            if (username == comment.author_username || username == authorUsername) {
                                replyCommentsButtonPanel.append(deleteButton);
                            }

                            //If current user is authorized to edit the comment, append the edit button.
                            if (username == comment.author_username) {
                                replyCommentsButtonPanel.append(editButton);
                            }

                            commentContainer.append(commentContainerTemplate);

                        }

                        //Insert replies at the bottom of the parent comment.
                        commentContainer.insertAfter(buttonsDiv);

                    buttonsDiv.find(".loader-wrapper").hide();
                    buttonsDiv.find(".loader-wrapper").remove();
                    button.html("Hide Replies");
                    }


                },
                error: loadNestedCommentsFail
            });
        }
});

function formatDate(date) {

    var days = date.getDate();
    var months = date.getMonth() + 1;
    var year = date.getFullYear();

    var hours = date.getHours();
    if (hours < 10) {
        hours = "0" + hours;
    }

    var minutes = date.getMinutes();
    if (minutes < 10) {
        minutes = "0" + minutes;
    }


    var amPM;

    if (hours >= 12) {
        amPM = 'PM';
    } else {
        amPM = 'AM';
    }

    return days + "/" + months + "/" + year + " " + hours +":" + minutes + " " + amPM;

}

function loadNestedCommentsFail(jqXHR, textStatus, errorThrown) {
    console.log(jqXHR.status);
    console.log(textStatus);
    console.log(errorThrown);

}

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