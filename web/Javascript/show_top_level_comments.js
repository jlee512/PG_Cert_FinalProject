/**
 * Created by Julian on 06-Jun-17.
 */

var commentPara = '<div class="panel panel-info">' +
                        '<div class="panel-heading">INSERT COMMENT HEADING</div>'+
                        '<div class="panel-body">INSERT COMMENT BODY</div>' +
                    '</div>';

var from = 0;
var count = 5;
moreComments = true;


function successfulCommentsLoad(msg) {

    var commentContainer = $(".top_level_comment_feed");

    if (msg.length == 0){
        /*Hide the loader picture, show the loaded underline and return that their are no further articles*/
        $('.loader-wrapper').hide();
        $('#loaded1, #loaded2, #loaded3, #loaded4').show();
        moreComments = false;

    } else {
        for (var i = 0; i < msg.length; i++) {

            var comment = msg[i];
            var commentDiv = $(commentPara);
            var date = new Date(comment.timestamp);
            if (comment.isParent) {
                isParent = true;
                var viewRepliesButton = '<button type="button" class="show_replies btn btn-default" value="' + comment.commentID + '">Show Replies</button>';
            } else {
                isParent = false;
            }
            var replyButton = '<a href="AddComment?article_id='+ getArticleID() + '&parentComment_id=' + comment.commentID + '" class="btn btn-default">Reply</a>';

            /*Add header to comment template*/
            commentDiv.find(".panel-heading").html("<p>" + comment.username + ", " + date.toDateString() + "</p>");
            /*Add body to comment template*/
            commentDiv.find(".panel-body").html("<p>" + comment.content + "</p>");
            commentDiv.append(replyButton);

            /*If the comment is a parent, add the show replies button*/
            if (comment.isParent) {
                commentDiv.append(viewRepliesButton);
            }

            /*Remove the loading icon*/
            $('.loader-wrapper').hide();

            /*Append the comment to the container in the ViewArticlePage JSP*/
            commentContainer.append(commentDiv);

        }
    }
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


function loadCommentsIncrement(article_id) {

    /*Show the articles loader*/
    $('.loader-wrapper').show();

    /*Start an AJAX call to load more articles*/
    $.ajax({

        url: '/GetComments_IndividualArticle',
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

function failedCommentsLoad(jqXHR, textStatus, errorThrown) {

    console.log(jqXHR.status);
    console.log(textStatus);
    console.log(errorThrown);

}

$(document).ready(function () {

    /*Get the article id from the 'article_id' cookie*/
    var article_id = getArticleID();

    /*Hide 'all comments loaded' bar*/
    $('#loaded1, #loaded2, #loaded3, #loaded4').hide();

    /*Add in infinite scrolling to load more comments*/
    $(window).scroll(function() {

        console.log(moreComments)
        /*Function to facilitate infinite scrolling of comments*/
        if ($(document).height() - window.innerHeight == $(window).scrollTop() & moreComments) {
            loadCommentsIncrement(article_id);
        }
    });

    /*Load initial four articles*/
    loadCommentsIncrement(article_id);
});