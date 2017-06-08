/**
 * Created by jlee512 on 8/06/2017.
 */
$(document).ready(function () {


    $(".add-article-button").click (function() {

        if ($(".dialog_open")[0]){
            console.log("Test")
            $(".add-article-button").removeClass("dialog_open");
            $(".add_article_panel_body").remove();
            $(".add-article-button").html("<p><i class='fa fa-plus' aria-hidden='true'></i> Add an Article</p>");

        } else {
            console.log("Test")
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
                "<button type='addArticle' id='addArticle'>Post Article</button>" +
                "</fieldset>" +
                "</form>" + "</div>";

            $(add_article_form).insertAfter($('.add-article-button'));
        }

    });

});