/**
 * Created by cbla080 on 5/06/2017.
 */
$("document").ready(function () {
    $(".show_replies").click(function() {
        //Show replies
        //URL will be https://tomcat1.sporadic.co.nz/while_e_coyote_remote_deploy
        var button = $(".show_replies");
        var parentID = button.val();
        console.log(parentID);
        var paragraph = button.parent();
        console.log(paragraph.text());
        $.ajax({
            url: "/ShowNestedComments?parentCommentID=" + parentID,
            type: "GET",
            success: function loadNestedComments(msg){
                console.log(msg);
                var div = paragraph.parent();

                for (i = 0; i < msg.length; i++) {
                    var comment = msg[i];
                    var commentContainer = paragraph.clone(true);
                    var date = new Date(comment.timestamp);
                    var heading = commentContainer.find(".panel-heading");
                    heading.html("<p>" + comment.author_username + " " + date.toDateString() + "</p>");
                    var body = commentContainer.find(".panel-body");
                    body.html("<p>" + comment.content + "</p>");
                    var replybutton = commentContainer.find(".reply");
                    var link = replybutton.get(0).href;
                    var linkSubstring = link.substring(0, (link.lastIndexOf("=")+1));
                    replybutton.attr("href", linkSubstring + comment.comment_id);
                    if (comment.is_parent == false){
                        var button = commentContainer.find(".show_replies");
                        button.remove();
                    }
                    div.append(commentContainer);
                }
            },
            error: loadNestedCommentsFail
        });
    });
});

function loadNestedCommentsFail(jqXHR, textStatus, errorThrown) {
    console.log(jqXHR.status);
    console.log(textStatus);
    console.log(errorThrown);
}