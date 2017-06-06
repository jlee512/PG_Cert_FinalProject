/**
 * Created by jlee512 on 5/06/2017.
 */

// Created a template which will be used for inserting new article HTML.
var articleTemplate =
    "<a class='individualArticleLink'>" +
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
        "</div>" +
    "</a>";

/* Setup to/count article variables to store the state of article loading on the page at a given point in time*/
var from = 0;
var count = 4;


function successfulArticleLoad(msg) {

    var articleContainer = $(".news_feed");

    for (var i = 0; i < msg.length; i++) {

        var article = msg[i];

        var articleDiv = $(articleTemplate);

        /*Make each article container a link to the full article*/
        var href = "ViewArticle?article_id=" + article.article_id;
        articleDiv.attr("href",href);

        articleDiv.find(".panel-title").text(article.article_title);

        var date = new Date(article.article_date);


        articleDiv.find(".panel-body").html("<p>Published by: " + article.author_username + "</p><p>" + date.toDateString() + "</p><p>" + article.article_body + "</p>");
        articleDiv.find(".panel-body").css("text-align", "left");
        articleContainer.append(articleDiv);

    }

    /*Check if less than the requested number of articles has been returned, disable the load more articles button*/
    if (msg.length < count) {

        $("#loadArticleButtonContainer").html("");

    }
}

function failedArticleLoad(jqXHR, textStatus, errorThrown) {

    console.log(jqXHR.status);
    console.log(textStatus);
    console.log(errorThrown);

}

function loadArticlesIncrement() {



    /*Start an AJAX call to load more articles*/
    $.ajax({

        url: '/MainContentAccess',
        type: 'GET',
        data: {from: from, count: count},
        success: successfulArticleLoad,
        error: failedArticleLoad
    });

    /*Increment the current "from" by the count so that next time the function is called, the next set of articles is loaded*/
    from += count;
}

$(document).ready(function () {

    /*Add in load more articles button*/
    $(window).scroll(function() {

        /*Function to facilitate infinite scrolling of articles*/
        if ($(document).height() - window.innerHeight == $(window).scrollTop()) {
            loadArticlesIncrement();
        }
    });

    /*Load initial four articles*/
    loadArticlesIncrement();
});