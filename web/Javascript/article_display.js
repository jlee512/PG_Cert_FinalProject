/**
 * Created by jlee512 on 5/06/2017.
 */

// Created a template which will be used for inserting new article HTML.
var articleTemplate =
    "<div class='container text-center vertical-center' id='mainContent'>" +
        "<div class='panel panel-default col-sm-12'>" +
            "<div style='padding-top: 2%'>" +
                "<div class='panel panel-default'>" +
                    "<div class='panel-heading'>" +
                        "<h3 class='panel-title'></h3>" +
                    "</div>" +
                    "<div class='panel-body'>" +
                    "</div>" +
                "</div>" +
            "</div>" +
        "</div>" +
    "</div>";

$("document").ready(function () {

    var container = $(".news_feed");

    $.ajax({

        url: '/MainContentAccess',
        type: 'GET',
        success: function (msg) {

            for (var i = 0; i < msg.length; i++) {

                var article = msg[i];

                var articleDiv = $(articleTemplate);
                articleDiv.find(".panel-title").text(article.article_title);
                articleDiv.find(".panel-body").text(article.author_username + "\n" + article.article_date + "\n" + article.article_body);
                console.log(articleDiv);
                container.append(articleDiv);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {

            console.log(jqXHR.status);
            console.log(textStatus);
            console.log(errorThrown);

        }
    });

});

function onLoadArticlesFailure(msg) {

    console.log("failed to load articles");

}