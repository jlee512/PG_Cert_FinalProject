/**
 * Created by Julian on 06-Jun-17.
 */

/*-------------------------------------------------------*/
/*This JavaScript file is used to show the top level comments for a given article using an AJAX call to the GetComments_IndividualArticle servlet*/
/*-------------------------------------------------------*/

//Comment panel template.
var commentPara = '<div class="panel panel-info">' +
                        '<div class ="comment-content">' +
                            '<div class="panel-heading">INSERT COMMENT HEADING</div>'+
                            '<div class="panel-body">INSERT COMMENT BODY</div>' +
                        '</div>' +
                        '<div class="buttons"></div>' +
                    '</div>';

/*-------------------------------------------------------*/
/* Setup global variables to store the state of comment loading on the page at a given point in time*/
var from = 0;
var count = 5;
var moreComments = true;

/*-------------------------------------------------------*/
/*If a successful AJAX call is made, this function is called to process the results and populate the top level comments into the 'top_level_comment_feed'*/
function successfulCommentsLoad(msg) {

    //Get page element to append comments to.
    var commentContainer = $(".top_level_comment_feed");

    /*Get username of current user*/
    var username = $("#userdetails").text();

    /*Get username of article author*/
    var authorUsername = $("#author").text();

    if (msg.length == 0){
        /*Hide the loader picture, show the loaded underline and return that there are no further comments*/
        $('.loader-wrapper').hide();
        $('#loaded1, #loaded2, #loaded3, #loaded4').show();
        moreComments = false;

    } else {
        for (var i = 0; i < msg.length; i++) {

            var comment = msg[i];
            var commentDiv = $(commentPara);
            var buttonsDiv = commentDiv.find('.buttons');

            //Get the formatted date.
            var dateUnformatted = new Date(comment.timestamp);
            var date = formatDate(dateUnformatted);

            /*Add a button to view replies if comment has replies*/
            if (comment.isParent) {
                isParent = true;
                var viewRepliesButton = '<button type="button" class="show_replies btn btn-default btn-sm" value="' + comment.comment_id + '">Show Replies</button>';
            } else {
                isParent = false;
            }

            /*Add a button to reply to the comment*/
            var replyButton = '<button type="button" class="add_reply btn btn-default btn-sm" value="' + comment.comment_id + '">Reply</button>';

            /*Add a button to delete the comment if current user is comment author or article author*/
            if (username == comment.username || username == authorUsername) {
                var deleteButton = '<a href="DeleteComment?comment_id=' + comment.comment_id + '&article_id=' + getArticleID() + '" class="btn btn-default btn-sm">Delete</a>'
            }

            /*Add a button to edit the comment if current user is comment author*/
            if (username == comment.username) {
                var editButton = '<button type="button" class="edit_comment btn btn-default btn-sm" value="' + comment.comment_id + '">Edit</button>';
            }

            /*Add header to comment template*/
            commentDiv.find(".panel-heading").html('<p><strong><a href="PublicProfile?username='+ comment.username + ' "style="color: #f9a825;">' + comment.username + '</a></strong>, ' + date + '</p>');
            /*Add body to comment template*/
            commentDiv.find(".panel-body").html("<p>" + comment.content + "</p>");

            /*Add the replies button to the comment template*/
            buttonsDiv.append(replyButton);

            /*If the comment is a parent, add the show replies button*/
            if (comment.isParent) {
                buttonsDiv.append(viewRepliesButton);
            }

            /*If current user is the commenter or the author of the article, allow them to delete the comment*/
            if (username == comment.username || username == authorUsername) {
                buttonsDiv.append(deleteButton);
            }

            /*If current user is the commenter, allow them to edit the comment*/
            if (username == comment.username) {
                buttonsDiv.append(editButton);
            }

            /*Remove the loading icon*/
            $('.loader-wrapper').hide();

            /*Append the comment to the container in the ViewArticlePage JSP*/
            commentContainer.append(commentDiv);

            /*If the message length is less than the requested count, hide the loader and set 'moreComments' to false as the end of the comments list has been reached*/
            if (msg.length < count) {
                $('.loader-wrapper').hide();
                $('#loaded1, #loaded2, #loaded3, #loaded4').show();
                moreComments = false;
            }
        }
    }
}

/*-------------------------------------------------------*/
/*Format date function to process the backend timestamp variable*/
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


/*-------------------------------------------------------*/
/*Function to obtain the article_id from the client-side cookie*/
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

/*-------------------------------------------------------*/
/*AJAX call to GetComments_IndividualArticle servlet to access an increment of top-level comments*/
function loadCommentsIncrement(article_id) {

    /*Show the articles loader*/
    $('.loader-wrapper').show();

    /*Start an AJAX call to load more articles*/
    $.ajax({

        url: 'GetComments_IndividualArticle',
        type: 'GET',
        data: {article_id: article_id, from: from, count: count},
        success: function (msg) {
            successfulCommentsLoad(msg);
        },
        error: failedCommentsLoad
    });

    /*Increment the current "from" by the count so that next time the function is called, the next set of articles is loaded*/
    from += count;
}

/*-------------------------------------------------------*/
/*If the AJAX call is failed, output an error message to the console*/
function failedCommentsLoad(jqXHR, textStatus, errorThrown) {

    console.log(jqXHR.status);
    console.log(textStatus);
    console.log(errorThrown);

}

/*-------------------------------------------------------*/
/*On loading, populate the page with the first round of top_level_comments*/
$(document).ready(function () {

    /*Get the article id from the 'article_id' cookie*/
    var article_id = getArticleID();

    /*Hide 'all comments loaded' bar*/
    $('#loaded1, #loaded2, #loaded3, #loaded4').hide();

    /*Add in infinite scrolling to load more comments*/
    $(window).scroll(function() {

        /*Function to facilitate infinite scrolling of comments*/
        if ($(document).height() - window.innerHeight == $(window).scrollTop() & moreComments) {
            loadCommentsIncrement(article_id);
        }
    });

    /*Load initial five comments*/
    loadCommentsIncrement(article_id);
});

/*---------------------------*/
/*End of JavaScript file*/
/*---------------------------*/