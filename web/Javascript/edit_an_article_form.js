/**
 * Created by jlee512 on 12/06/2017.
 */

var articleEditFormString =
    "<div><form action='EditArticle' method='POST'><label for='article_title_input'>Article Title:</label>" +
    "<input type='text' name='article_title_input' class='article_input_form article_title_input' style='max-width: 100%;' maxlength='100' required>" +
    "<label for='article_body_input'>Article Body:</label>" +
    "<textarea name='article_body_input' class='form-control article_body_input' rows='5' style='max-width: 100%;' required></textarea><button class='btn btn-sm editArticle' type='editArticle' name='editArticle' style='background-color: #00acc1;'>Confirm Edit</button>" +
    "</form></div>";


$('.panel-body').on('click', '.edit_article', (function (e) {

    e.stopPropagation();

    articleEditForm = $(articleEditFormString);

    original_title = $(this).parent().parent().children().first().find('span').text();

    article_id = $(this).parent().attr("id");

    original_article_body = getCookie(article_id + "_full_article_body", article_id);

    console.log(original_article_body);

    if ($(this).parent().parent().hasClass("article_edit_open")) {

        $(this).parent().children().first().remove();
        $(this).parent().parent().removeClass("article_edit_open");
        $(this).parent().children().slice(0, 3).show();

    } else {

        $(this).parent().parent().addClass("article_edit_open");
        $(this).parent().children().slice(0, 3).hide();

        $('.article_title_input', articleEditForm).val(original_title);

        $('.article_body_input', articleEditForm).val(original_article_body);

        $('.editArticle', articleEditForm).val(article_id);

        $(this).parent().prepend(articleEditForm);
    }

}));

function getCookie(cookie_name, article_id) {

    var name = cookie_name + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var cookie_array = decodedCookie.split(';');
    for (var i = 0; i < cookie_array.length; i++) {
        var cookie = cookie_array[i];
        while (cookie.charAt(0) == ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(name) == 0) {
            full_article_body = cookie.substring(name.length, cookie.length);

            return full_article_body;
        }
    }
    return "";
}
