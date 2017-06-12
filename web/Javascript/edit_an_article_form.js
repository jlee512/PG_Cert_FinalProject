/**
 * Created by jlee512 on 12/06/2017.
 */

var articleEditForm =
    "<div><form><label for='article_title_input'>Article Title:</label>" +
    "<input type='text' id='article_title_input' name='article_title_input' class='article_input_form' style='max-width: 100%;' maxlength='100' required>" +
    "<label for='article_body_input'>Article Body:</label>" +
    "<textarea name='article_body_input' id='article_body_input' class='form-control' rows='5' style='max-width: 100%;' required></textarea>" +
    "</form></div>";



$('.panel-body').on('click', '.edit_article', (function () {

    original_title = $(this).parent().parent().children().first().text();
    original_body = $(this).parent().children(':nth-child(3)').text();
    console.log(original_body);

    if ($(".article_edit_open")[0]) {

        $(this).parent().children().first().remove();
        $(this).parent().parent().removeClass("article_edit_open");
        $(this).parent().children().slice(0, 3).show();

    } else {

        $(this).parent().parent().addClass("article_edit_open");
        $(this).parent().children().slice(0, 3).hide();
        $(this).parent().prepend(articleEditForm);


    }

}));
