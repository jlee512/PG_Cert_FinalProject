/**
 * Created by jlee512 on 12/06/2017.
 */



$('.panel-body').on('click', '.edit_article', (function () {

    if ($(".article_edit_open")[0]) {

        $(this).parent().parent().removeClass("article_edit_open");
        $(this).parent().children().slice(0, 3).show();

    } else {

        $(this).parent().parent().addClass("article_edit_open");
        $(this).parent().children().slice(0, 3).hide();

    }

}));
