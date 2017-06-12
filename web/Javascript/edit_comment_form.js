/**
 * Created by cbla080 on 12/06/2017.
 */
$(document).on("click", ".edit_comment", function () {
    var articleID = $("#articleid").text();
    var button = $(this);
    var comment_id = button.val();
    var commentContainerDiv = button.closest(".panel-info");
    var commentBodyDiv = commentContainerDiv.find(".panel-body");
    var commentHeadingDiv = commentContainerDiv.find(".panel-heading");
    var commentContent = commentBodyDiv.text();

    if (button.hasClass("edit_open")){
        button.removeClass("edit_open");
        button.html("Edit");
        commentBodyDiv.show();
        $(".edit_comment_body").remove();



    } else {
        button.addClass("edit_open");
        button.html("<p><i class='fa fa-pencil' aria-hidden='true'></i>Edit</p>");
        var edit_comment_form = "<div class='panel-body edit_comment_body'>" + "<form action='EditComment' method='POST'>" +
            "<div id='editComment'>" +
            "<label for='comment_body'>Comment:</label>" +
            "<textarea rows='4' cols='50' name='comment_body' id='comment_body'>" + commentContent + "</textarea>" +
            "<input type='hidden' name='article_id' value='"+ articleID +"'>" +
            "<input type='hidden' name='comment_id' value='"+ comment_id +"'>" +
            "<input type='submit' name='submit' value='Update Comment'>" +
            "</div>" + "</form>";

        commentBodyDiv.hide();
        $(edit_comment_form).insertAfter(commentHeadingDiv);
    }

});