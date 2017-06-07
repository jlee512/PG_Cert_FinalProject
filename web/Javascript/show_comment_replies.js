/**
 * Created by cbla080 on 5/06/2017.
 */
var commentPara = '<div class="panel-heading">INSERT COMMENT HEADING</div>'+
    '<div class="panel-body">INSERT COMMENT BODY</div>';

var loaderWrapper = '<div class="loader-wrapper" style="margin-left: 3%;">' +
    '<div class="loader" style="display: inline-block;"></div>' +
    '</div>';

$(document).on("click", ".show_replies", function () {
        //Show replies
        //URL will be https://tomcat1.sporadic.co.nz/while_e_coyote_remote_deploy
        var button = $(this);
        var parentID = button.val();
        console.log(parentID);
        var top_level_comment_div = button.parent();
        console.log(top_level_comment_div.text());
        top_level_comment_div.append(loaderWrapper);

        $.ajax({
            url: "/ShowNestedComments?parentCommentID=" + parentID,
            type: "GET",
            success: function loadNestedComments(msg){
                $('.loader-wrapper').show();
                console.log(msg);
                for (i = 0; i < msg.length; i++) {
                    var comment = msg[i];
                    var commentContainer = $(commentPara);
                    var date = new Date(comment.timestamp);
                    if (comment.isParent) {
                        var viewRepliesButton = '<button type="button" class="show_replies btn btn-default" value="' + comment.comment_id + '">Show Replies</button>';
                    }
                    var replyButton = '<a href="AddComment?article_id=' + getArticleID() + '&parentComment_id=' + comment.comment_id + '" class="btn btn-default">Reply</a>';
                    var heading = commentContainer.find(".panel-heading");
                    commentContainer.find(".panel-heading").html("<p>" + comment.author_username + ", " + date.toDateString() + "</p>");
                    commentContainer.find(".panel-body").html("<p>" + comment.content + "</p>");
                    commentContainer.append(replyButton);
                    if (comment.isParent) {
                        commentContainer.append(viewRepliesButton);
                    }
                    top_level_comment_div.append(commentContainer);
                    }
                $('.loaded-wrapper').hide();
                $('.loader-wrapper').remove();
                button.remove();
                },
            error: loadNestedCommentsFail
        });
});

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
