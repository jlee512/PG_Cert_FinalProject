/**
 * Created by catherinedechalain on 13/06/17.
 */
/**
 * Created by jlee512 on 5/06/2017.
 */

/*-------------------------------------------------------*/
/*This JavaScript file is used to display articles on the public profile page. It incorporates and AJAX call to the ViewPublicArticles*/
/*-------------------------------------------------------*/

// Created a template which will be used for inserting new article HTML.
var articleTemplate =
    "<div class='panel panel-default'>" +
    "<a class='individualArticleLink'>" + "<div class='panel-heading article-heading' style='background-color: #00acc1; color: white'>" +
    "<h3 class='panel-title'><i class='fa fa-newspaper-o' aria-hidden='true'></i></h3>" +
    "</div>" + "</a>" +
    "<div class='panel-body'>" +
    "</div>" +
    "</div>";

/*-------------------------------------------------------*/
//Get the username of the author.
var username = $("#username").text();

/*-------------------------------------------------------*/
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
$('div.news_feed').on('mouseenter', '.individualArticleLink', hoverBackgroundColor);

$('div.news_feed').on('mouseleave', '.individualArticleLink', normalBackgroundColor);


/*-------------------------------------------------------*/
/* Setup global variables to store the state of article loading on the page at a given point in time*/
var from = 0;
var count = 6;
var moreArticles = true;


/*If a successful AJAX call is made, this function is called to process the results and populate the user's articles 'news_feed'*/
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
                "<i class='fa fa-comment-o' aria-hidden='true'><span class='sr-only'>Comments</span></i> " +
                article.comment_count + "</div>");

            /*Get the article id*/
            articleDiv.find(".panel-body").attr("id", article.article_id);

            var date = new Date(article.article_timestamp);

            var formattedDate = formatDate(date);

            /*Format the panel body with the article preview and
             * (1) View button ONLY
             */
            articleDiv.find(".panel-body").html("<p>Published by: " + article.author_username + "</p>" +
                "<p>" + formattedDate + "</p>" +
                "<p>" + article.article_body + "</p>" +
                "<a href='" + href + "' style='color: white;'>" +
                "<div class='btn btn-sm' style='background-color: #64dd17;'>" +
                "<i class='fa fa-eye' aria-hidden='true'><span class='sr-only'>View Article</span></i>" +
                "</div>" +
                "</a>");

            articleDiv.find(".panel-body").css("text-align", "left");

            /*Remove the loading icon*/
            $('.loader-wrapper').hide();

            /*Append the article to the user's individual article news_feed*/
            articleContainer.append(articleDiv);

        }

        /*If the message length is less than the requested count, hide the loader and set 'moreArticles' to false as the end of the article list has been reached*/
        if (msg.length < count) {
            $('.loader-wrapper').hide();
            $('#loaded1, #loaded2, #loaded3, #loaded4').show();
            moreArticles = false;
        }
    }
}

/*-------------------------------------------------------*/


/*Format date function to process the backend timestamp variable*/
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

/*-------------------------------------------------------*/
/*If the AJAX call is failed, output an error message to the console*/
function failedArticleLoad(jqXHR, textStatus, errorThrown) {

    console.log(jqXHR.status);
    console.log(textStatus);
    console.log(errorThrown);

}

/*-------------------------------------------------------*/
/*AJAX call to ViewPublicArticles servlet to access an increment of articles*/
function loadArticlesIncrement() {

    /*Show the articles loader*/
    $('.loader-wrapper').show();

    /*Start an AJAX call to load more articles*/
    $.ajax({

        url: 'ViewPublicArticles',
        type: 'GET',
        data: {from: from, count: count, username: username},
        success: function (msg) {
            successfulArticleLoad(msg);
        },
        error: failedArticleLoad
    });

    /*Increment the current "from" by the count so that next time the function is called, the next set of articles is loaded*/
    from += count;
}

/*-------------------------------------------------------*/
/*On loading, populate the page with the first round of articles*/
$(document).ready(function () {

    $('#loaded1, #loaded2, #loaded3, #loaded4').hide();

    /*Add in infinite scrolling to load more articles*/
    $(window).scroll(function() {

        /*Function to facilitate infinite scrolling of articles*/
        if ($(document).height() - window.innerHeight <= ($(window).scrollTop() + 10) && moreArticles) {
            loadArticlesIncrement();
        }
    });

    /*Load initial four articles*/
    loadArticlesIncrement();
});

/*---------------------------*/
/*End of JavaScript file*/
/*---------------------------*/