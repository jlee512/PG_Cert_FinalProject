/**
 * Created by cbla080 on 5/06/2017.
 */
var commentPara = "<p class='nestedComment'></p>";

    $(".show_replies").click(function() {
        //Show replies
        //URL will be https://tomcat1.sporadic.co.nz/while_e_coyote_remote_deploy
        var parentID = $(".show_replies").val();
        console.log(parentID);
        $.ajax({
            url: "/ShowNestedComments?parentCommentID=" + parentID,
            type: "GET",
            success: loadNestedComments,
            error: loadNestedCommentsFail
        });
    });

function loadNestedComments(msg) {
        console.log(msg);
        var paragraph = this.parent();
        var div = paragraph.parent();

    for (i = 0; i < msg.length; i++) {
        var comment = msg[i];
        var commentContainer = $(commentPara);
        commentContainer.text(comment.content);
        div.appendChild(commentContainer);
    }
}