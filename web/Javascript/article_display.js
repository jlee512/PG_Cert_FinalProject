/**
 * Created by jlee512 on 5/06/2017.
 */

// Created a template which will be used for inserting new article HTML.
var articleTemplate =
    "<div class='container text-center' id='mainContent'>" +
        "<div class='row'>" +
            "<div class='col-md-1'></div>" +
                "<div class='panel panel-default col-sm-12 col-md-10'>" +
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
            "<div class='col-md-1'></div>" +
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
                var date = new Date(article.article_date);
                articleDiv.find(".panel-body").html("<p>Published by: " + article.author_username + "</p><p>" + date.toDateString() + "</p><p>" + article.article_body + "</p>");
                articleDiv.find(".panel-body").css("text-align","left");
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