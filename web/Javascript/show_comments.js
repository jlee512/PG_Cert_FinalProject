/**
 * Created by cbla080 on 5/06/2017.
 */
var commentPara = '<div class="panel-heading">Blah blah blah</div>'+
                    '<div class="panel-body">This is a div</div>';

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
                    var date = new Date(comment.timestamp);
                    var heading = commentContainer.find(".panel-heading")
                    console.log(heading.text());
                    heading.html("<p>" + comment.author_id + date.toDateString() + "</p>");
                    var body = commentContainer.find(".panel-body");
                    console.log(body.text())
                    body.html("<p>" + comment.content + "</p>");
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