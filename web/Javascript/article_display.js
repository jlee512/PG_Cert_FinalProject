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
                                "<a class='individualArticleLink'>" +
                                    "<div class='panel-heading article-heading' style='background-color: #00acc1; color: white'>" +
                                        "<h3 class='panel-title'><i class='fa fa-newspaper-o' aria-hidden='true'></i></h3>" +
                                    "</div></a>" +
                                    "<div class='panel-body'>" +
                                    "</div>" +
                            "</div>" +
                        "</div>" +
                    "</div>" +
                "<div class='col-md-1'></div>" +
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

$('.news_feed').on('mouseenter', '.individualArticleLink', hoverBackgroundColor);

$('.news_feed').on('mouseleave', '.individualArticleLink', normalBackgroundColor);

/* Setup to/count article variables to store the state of article loading on the page at a given point in time*/
var from = 0;
var count = 4;
var moreArticles = true;
var sort_by = "date";


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

            /*Add in the article title and comment count*/
            articleDiv.find(".panel-title").append(" <div style='width: 70%; display: inline-block; text-overflow: ellipsis; overflow: hidden; white-space: nowrap;'>" + article.article_title + "</div>");
            articleDiv.find(".panel-title").append("<div class='view_comments pull-right' style='color: white' style='display: inline-block;'>"+
                "<i class='fa fa-comment-o' aria-hidden='true'></i> " +
                article.comment_count + "</div>");

            articleDiv.find(".panel-body").attr("id", article.article_id);

            var date = new Date(article.article_timestamp);
            var formattedDate = formatDate(date);


            articleDiv.find(".panel-body").html("<p>Published by: <strong><a href='PublicProfile?username=" + article.author_username + "'style='color: #f9a825;'>" + article.author_username + "</a></strong></p><p>" + formattedDate + "</p><p>" + article.article_body + "</p>");
            articleDiv.find(".panel-body").css("text-align", "left");

            /*Remove the loading icon*/
            $('.loader-wrapper').hide();

            articleContainer.append(articleDiv);

            if (msg.length < count) {
                $('.loader-wrapper').hide();
                $('#loaded1, #loaded2, #loaded3, #loaded4').show();
                moreArticles = false;
            }
        }
    }
}

function formatDate(date) {

    var days = date.getDate();
    var months = date.getMonth() + 1;
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

        url: 'MainContentAccess',
        type: 'GET',
        data: {from: from, count: count, sort_by: sort_by},
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
        if ($(document).height() - window.innerHeight <= ($(window).scrollTop() + 10) && moreArticles && sort_by == "date") {
            loadArticlesIncrement();
        }
    });

    /*Load initial four articles*/
    loadArticlesIncrement();
});

$("#title-sort-button").on("click", function() {

    $('#loaded1, #loaded2, #loaded3, #loaded4').hide();

    sort_by = "title";
    console.log(sort_by);

    /*Remove all existing articles*/
    $(".news_feed").empty();

    /*Reset article count and'more_articles' variables*/
    from = 0;
    moreArticles = true;

    /*Load first increment of articles*/
    loadArticlesIncrement();

});

$("#date-sort-button").on("click", function() {

    $('#loaded1, #loaded2, #loaded3, #loaded4').hide();

    sort_by = "date";
    console.log(sort_by);

    /*Remove all existing articles*/
    $(".news_feed").empty();

    /*Reset article count and'more_articles' variables*/
    from = 0;
    moreArticles = true;

    /*Load first increment of articles*/
    loadArticlesIncrement();

});

$("#author-sort-button").on("click", function() {

    $('#loaded1, #loaded2, #loaded3, #loaded4').hide();

    sort_by = "author";
    console.log(sort_by);

    /*Remove all existing articles*/
    $(".news_feed").empty();

    /*Reset article count and'more_articles' variables*/
    from = 0;
    moreArticles = true;

    /*Load first increment of articles*/
    loadArticlesIncrement();

});