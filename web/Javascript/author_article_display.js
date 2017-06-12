/**
 * Created by jlee512 on 5/06/2017.
 */

// Created a template which will be used for inserting new article HTML.
var articleTemplate =
        "<div class='panel panel-default'>" +
            "<a class='individualArticleLink'>" + "<div class='panel-heading article-heading' style='background-color: #00acc1; color: white'>" +
                "<h3 class='panel-title'><i class='fa fa-newspaper-o' aria-hidden='true'></i></h3>" +
            "</div>" + "</a>" +
            "<div class='panel-body'>" +
            "</div>" +
        "</div>";

/*jQuery function to animate each article header on hover*/

function hoverBackgroundColor() {

    $(this).find('.article-heading').stop().animate({
        backgroundColor: "#ffd54f",
        color: "black"
    }, 'slow');

}

function normalBackgroundColor() {

    $(this).find('.article-heading').stop().animate({
        backgroundColor: "#00acc1",
        color: "white"
    }, 'slow');

}

/*jQuery function to animate headings of articles as they are hovered over*/

$('div.news_feed').on('mouseenter', '.individualArticleLink', hoverBackgroundColor);

$('div.news_feed').on('mouseleave', '.individualArticleLink', normalBackgroundColor);

/* Setup to/count article variables to store the state of article loading on the page at a given point in time*/
var from = 0;
var count = 6;
var moreArticles = true;


function successfulArticleLoad(msg) {

    var articleContainer = $(".news_feed");

    if (msg.length == 0){
        /*Hide the loader picture, show the loaded underline and return that their are no further articles*/
        $('.loader-wrapper').hide();
        $('#loaded1, #loaded2, #loaded3, #loaded4').show();
        moreArticles = false;

    } else {
        for (var i = 0; i < msg.length; i++) {

            var article = msg[i];

            var articleDiv = $(articleTemplate);

            /*Make each article container a link to the full article*/
            var href = "ViewArticle?article_id=" + article.article_id;

            articleDiv.find(".individualArticleLink").attr("href", href);

            articleDiv.find(".panel-title").append(" " + article.article_title);
            articleDiv.find(".panel-body").attr("id", article.article_id);

            var date = new Date(article.article_timestamp);

            var formattedDate = formatDate(date);

            articleDiv.find(".panel-body").html("<p>Published by: " + article.author_username + "</p>" +
                                                    "<p>" + formattedDate + "</p>" +
                                                    "<p>" + article.article_body + "</p>" +
                                                    "<a href=" + href + " style='color: white;'>" +
                                                        "<div class='btn btn-sm' style='background-color: #64dd17;'>" +
                                                            "<i class='fa fa-eye' aria-hidden='true'></i>" +
                                                        "</div>" +
                                                    "</a>" +
                                                    "<a href='DeleteAnArticle?article_id=" + article.article_id + "' style='color: white;'>" +
                                                        "<div class='btn btn-sm' style='background-color: #b23434;'>" +
                                                            "<i class='fa fa-trash' aria-hidden='true'></i>" +
                                                        "</div>" +
                                                    "</a>" +
                                                    "<div type='button' class='btn btn-sm edit_article' style='background-color: #f9a825; color: white;'>" +
                                                        "<i class='fa fa-pencil-square-o' aria-hidden='true'></i>" +
                                                    "</div>");

            articleDiv.find(".panel-body").css("text-align", "left");

            /*Remove the loading icon*/
            $('.loader-wrapper').hide();

            articleContainer.append(articleDiv);

            /*Create a cookie which stores the full article body for reference in editing (lasts one 1/2 day)*/
            var cookie_date = new Date();
            cookie_date.setTime(cookie_date.getTime() + (24 * 60 * 60 * 1000 * 0.5));
            var expires = "expires=" + cookie_date.toUTCString();
            document.cookie = article.article_id + "_full_article_body=" + article.article_body_full + ";" + expires + "; path=/";

        }

        if (msg.length < count) {
            $('.loader-wrapper').hide();
            $('#loaded1, #loaded2, #loaded3, #loaded4').show();
            moreArticles = false;
        }
    }
}



function formatDate(date) {

    var days = date.getDate();
    var months = date.getMonth();
    var year = date.getFullYear();

    var hours = date.getHours();
    if (hours < 10) {
        hours = "0" + hours;
    }

    var minutes = date.getMinutes();

    if (minutes < 10) {
        minutes = "0" + minutes;
    }
    var amPM;

    if (hours >= 12) {
        amPM = 'PM';
    } else {
        amPM = 'AM';
    }

    return days + "/" + months + "/" + year + " " + hours +":" + minutes + " " + amPM;

}

function failedArticleLoad(jqXHR, textStatus, errorThrown) {

    console.log(jqXHR.status);
    console.log(textStatus);
    console.log(errorThrown);

}

function loadArticlesIncrement() {

    /*Show the articles loader*/
    $('.loader-wrapper').show();

    /*Start an AJAX call to load more articles*/
    $.ajax({

        url: 'ViewIndividualArticles',
        type: 'GET',
        data: {from: from, count: count},
        success: function (msg) {
            successfulArticleLoad(msg);
        },
        error: failedArticleLoad
    });

    /*Increment the current "from" by the count so that next time the function is called, the next set of articles is loaded*/
    from += count;
}

$(document).ready(function () {

    $('#loaded1, #loaded2, #loaded3, #loaded4').hide();

    /*Add in infinite scrolling to load more articles*/
    $(window).scroll(function() {

        /*Function to facilitate infinite scrolling of articles*/
        if ($(document).height() - window.innerHeight == $(window).scrollTop() & moreArticles) {
            loadArticlesIncrement();
        }
    });

    /*Load initial four articles*/
    loadArticlesIncrement();
});