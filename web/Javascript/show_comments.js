/**
 * Created by cbla080 on 5/06/2017.
 */
var commentPara = "<p class='nestedComment'></p>";

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
                console.log(paragraph.text());
                var div = paragraph.parent();

                for (i = 0; i < msg.length; i++) {
                    var comment = msg[i];
                    console.log(comment.content);
                    var commentContainer = $(commentPara);
                    commentContainer.text(comment.content);
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