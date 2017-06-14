/**
 * Created by jlee512 on 8/06/2017.
 */
$(document).ready(function () {


    $(".add-article-button").click (function() {

        if ($(".dialog_open")[0]){
            $(".add-article-button").removeClass("dialog_open");
            $(".add_article_panel_body").remove();
            $(".add-article-button").html("<p><i class='fa fa-plus' aria-hidden='true'></i> Add an Article</p>");

        } else {
            $(".add-article-button").addClass("dialog_open");
            $(".add-article-button").html("<p><i class='fa fa-pencil' aria-hidden='true'></i> Have your say</p>");
            var add_article_form = "<div class='panel-body add_article_panel_body'>" + "<form action='AddAnArticleAttempt' method='POST'>" +
                "<fieldset>" +
                "<!--Article Title Input-->" +
                "<div id='addArticleTitle'>" +
                "<label for='article_title_input'>Article Title:</label>" +
                "<input type='text' id='article_title_input' name='article_title_input' class='article_input_form' style='max-width: 100%;' maxlength='100' required>" +
                "</div>" +
                "<!--Article Body Input-->" +
                "<div id='addArticleBody' class='form-group'>" +
                "<label for='article_body_input'>Article Body:</label>" +
                "<textarea name='article_body_input' id='article_body_input' class='form-control' rows='5' style='max-width: 100%;' required></textarea>" +
                "</div>" +
                "<!--Article Date Calendar Input-->" +
                "<div id='addArticleDate' class='form-group'>" +
                "<p>Please select a publishing date: <input type='text' id='datepicker' name='calendar_input'></p>" +
                "</div>" +
                    /*Submit Button*/
                "<button class='btn btn-sm' type='addArticle' id='addArticle' style='background-color: #00acc1;'>Post Article</button>" +
                "</fieldset>" +
                "</form>" + "</div>" +
            "<script>$('#datepicker').datepicker(); </script>";

            $(add_article_form).insertAfter($('.add-article-button'));
        }

    });

});

setTimeout(fade_out, 3000);

function fade_out() {

    $('.comment-delete-notification').fadeOut().empty();
    $('.article-add-notification').fadeOut().empty();
    $('.multimedia-add-notification').fadeOut().empty();
    $('.article-edit-notification').fadeOut().empty();

}