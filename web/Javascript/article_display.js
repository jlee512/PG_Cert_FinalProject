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

/* Setup global variables to store the state of article loading on the page at a given point in time as well as sorting mechanisms*/
var from = 0;
var count = 4;
var moreArticles = true;
var sort_by = "date";
var ordering = "DESC";
var search_term = "";
var date_default_ordering = "DESC";
var title_default_ordering = "ASC";
var author_default_ordering = "ASC";



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

function loadArticlesIncrement(asynchronous) {

    /*Show the articles loader*/
    $('.loader-wrapper').show();

    console.log(sort_by);
    console.log(ordering);
    console.log(search_term);

    /*Start an AJAX call to load more articles*/
    $.ajax({

        url: 'MainContentAccess',
        async: true,
        type: 'GET',
        data: {from: from, count: count, sort_by: sort_by, ordering: ordering, search_term: search_term},
        success: function (msg) {
            successfulArticleLoad(msg);
        },
        error: failedArticleLoad
    });

    /*Increment the current "from" by the count so that next time the function is called, the next set of articles is loaded*/
    from += count;
    console.log(from);
    console.log(count);
}

$(document).ready(function () {

    $('#loaded1, #loaded2, #loaded3, #loaded4').hide();

    /*Add in infinite scrolling to load more articles*/
    $(window).scroll(function() {

        /*Function to facilitate infinite scrolling of articles*/
        if ($(document).height() - window.innerHeight <= ($(window).scrollTop() + 10) && moreArticles) {
            loadArticlesIncrement(false);
        }
    });

    /*Load initial four articles*/
    loadArticlesIncrement(true);
});

/*---------------------------------Title sort----------------------------------*/

$("#title-sort-button").on("click", function() {

    /*Reset the other sorting field icons*/
    $("#author-sort-button > i").remove();
    $("#author-sort-button").append("<i class='fa fa-sort' aria-hidden='true'></i>");
    $("#date-sort-button > i").remove();
    $("#date-sort-button").append("<i class='fa fa-sort' aria-hidden='true'></i>");

    $('#loaded1, #loaded2, #loaded3, #loaded4').hide();

    /*Remove all sorting classes from the other fields*/
    $("#author-sort-button").removeClass("sorted");
    $("#author-sort-button").removeClass("reverse-sorting-order");
    $("#date-sort-button").removeClass("sorted");
    $("#date-sort-button").removeClass("reverse-sorting-order");

    date_default_ordering = "DESC";
    author_default_ordering = "ASC";

    /*If already sorted and sorted in reverse, revert to regular sorting order*/
    if ($("#title-sort-button").hasClass("sorted") && $("#title-sort-button").hasClass("reverse-sorting-order")) {

        /*If the order is already reversed, remove the class and set the order back to standard*/
        $("#title-sort-button").removeClass("reverse-sorting-order");
        title_default_ordering = "ASC";
        /*Remove the reverse icon and add the asecnding icon*/
        $("#title-sort-button > i").remove();
        $("#title-sort-button").append("<i class='fa fa-sort-alpha-asc' aria-hidden='true'></i>");

        /*Else if sorted and not in reverse order, reverse the sorting order*/
    } else if ($("#title-sort-button").hasClass("sorted")) {

        $("#title-sort-button").addClass("reverse-sorting-order");
        title_default_ordering = "DESC";
        /*Remove the reverse icon and add the asecnding icon*/
        $("#title-sort-button > i").remove();
        $("#title-sort-button").append("<i class='fa fa-sort-alpha-desc' aria-hidden='true'></i>");

        /*Else the field is being sorted for the first time and set the default ordering*/
    } else {

        $("#title-sort-button").addClass("sorted");
        title_default_ordering = "ASC";
        /*Remove the standard icon and add the asecnding icon*/
        $("#title-sort-button > i").remove();
        $("#title-sort-button").append("<i class='fa fa-sort-alpha-asc' aria-hidden='true'></i>");

    }

    sort_by = "title";
    ordering = title_default_ordering;
    search_term = "";
    console.log(sort_by);
    console.log(title_default_ordering);

    /*Remove all existing articles*/
    $(".news_feed").empty();

    /*Reset article count and'more_articles' variables*/
    from = 0;
    moreArticles = true;

    /*Load first increment of articles*/
    loadArticlesIncrement(true);

});

/*-----------------------------------Date Sort-----------------------------------*/

$("#date-sort-button").on("click", function() {

    /*Reset the other sorting field icons*/
    $("#author-sort-button > i").remove();
    $("#author-sort-button").append("<i class='fa fa-sort' aria-hidden='true'></i>");
    $("#title-sort-button > i").remove();
    $("#title-sort-button").append("<i class='fa fa-sort' aria-hidden='true'></i>");

    $('#loaded1, #loaded2, #loaded3, #loaded4').hide();

    /*Remove all sorting classes from the other fields*/
    $("#title-sort-button").removeClass("sorted");
    $("#title-sort-button").removeClass("reverse-sorting-order");

    $("#author-sort-button").removeClass("sorted");
    $("#author-sort-button").removeClass("reverse-sorting-order");

    title_default_ordering = "ASC";
    author_default_ordering = "ASC";

    /*If already sorted and sorted in reverse, revert to regular sorting order*/
    if ($("#date-sort-button").hasClass("sorted") && $("#date-sort-button").hasClass("reverse-sorting-order")) {

        /*If the order is already reversed, remove the class and set the order back to standard*/
        $("#date-sort-button").removeClass("reverse-sorting-order");
        date_default_ordering = "DESC";

        $("#date-sort-button > i").remove();
        $("#date-sort-button").append("<i class='fa fa-sort-desc' aria-hidden='true'></i>");

        /*Else if sorted and not in reverse order, reverse the sorting order*/
    } else if ($("#date-sort-button").hasClass("sorted")) {

        $("#date-sort-button").addClass("reverse-sorting-order");
        date_default_ordering = "ASC";

        $("#date-sort-button > i").remove();
        $("#date-sort-button").append("<i class='fa fa-sort-asc' aria-hidden='true'></i>");

        /*Else the field is being sorted for the first time and set the default ordering*/
    } else {

        $("#date-sort-button").addClass("sorted");
        date_default_ordering = "DESC";

        $("#date-sort-button > i").remove();
        $("#date-sort-button").append("<i class='fa fa-sort-desc' aria-hidden='true'></i>");

    }

    sort_by = "date";
    ordering = date_default_ordering;
    search_term = "";
    console.log(sort_by);
    console.log(date_default_ordering);

    /*Remove all existing articles*/
    $(".news_feed").empty();

    /*Reset article count and'more_articles' variables*/
    from = 0;
    moreArticles = true;

    /*Load first increment of articles*/
    loadArticlesIncrement(true);

});

/*-----------------------------------Author Sort---------------------------------------*/

$("#author-sort-button").on("click", function() {

    /*Reset the other sorting field icons*/
    $("#title-sort-button > i").remove();
    $("#title-sort-button").append("<i class='fa fa-sort' aria-hidden='true'></i>");
    $("#date-sort-button > i").remove();
    $("#date-sort-button").append("<i class='fa fa-sort' aria-hidden='true'></i>");

    $('#loaded1, #loaded2, #loaded3, #loaded4').hide();

    /*Remove all sorting classes from the other fields*/
    $("#title-sort-button").removeClass("sorted");
    $("#title-sort-button").removeClass("reverse-sorting-order");
    $("#date-sort-button").removeClass("sorted");
    $("#date-sort-button").removeClass("reverse-sorting-order");

    title_default_ordering = "ASC";
    date_default_ordering = "DESC";

    /*If already sorted and sorted in reverse, revert to regular sorting order*/
    if ($("#author-sort-button").hasClass("sorted") && $("#author-sort-button").hasClass("reverse-sorting-order")) {

        /*If the order is already reversed, remove the class and set the order back to standard*/
        $("#author-sort-button").removeClass("reverse-sorting-order");
        author_default_ordering = "ASC";

        $("#author-sort-button > i").remove();
        $("#author-sort-button").append("<i class='fa fa-sort-alpha-asc' aria-hidden='true'></i>");

        /*Else if sorted and not in reverse order, reverse the sorting order*/
    } else if ($("#author-sort-button").hasClass("sorted")) {

        $("#author-sort-button").addClass("reverse-sorting-order");
        author_default_ordering = "DESC";

        $("#author-sort-button > i").remove();
        $("#author-sort-button").append("<i class='fa fa-sort-alpha-desc' aria-hidden='true'></i>");

        /*Else the field is being sorted for the first time and set the default ordering*/
    } else {

        $("#author-sort-button").addClass("sorted");
        author_default_ordering = "ASC";

        $("#author-sort-button > i").remove();
        $("#author-sort-button").append("<i class='fa fa-sort-alpha-asc' aria-hidden='true'></i>");

    }

    sort_by = "author";
    ordering = author_default_ordering;
    search_term = "";
    console.log(sort_by);
    console.log(author_default_ordering);

    /*Remove all existing articles*/
    $(".news_feed").empty();

    /*Reset article count and'more_articles' variables*/
    from = 0;
    moreArticles = true;

    /*Load first increment of articles*/
    loadArticlesIncrement(true);

});

/*---------------------------------------Searching-------------------------------------------*/

$("#search-button").on("click", function() {

    /*Reset the other sorting field icons*/
    $("#title-sort-button > i").remove();
    $("#title-sort-button").append("<i class='fa fa-sort' aria-hidden='true'></i>");
    $("#date-sort-button > i").remove();
    $("#date-sort-button").append("<i class='fa fa-sort' aria-hidden='true'></i>");
    $("#author-sort-button > i").remove();
    $("#author-sort-button").append("<i class='fa fa-sort' aria-hidden='true'></i>");



    $('#loaded1, #loaded2, #loaded3, #loaded4').hide();

    sort_by = "searchterm";
    console.log(sort_by);

    search_term = $("#searchbar").val();
    console.log(search_term);

    /*Remove all existing articles*/
    $(".news_feed").empty();

    /*Reset article count and'more_articles' variables*/
    from = 0;
    moreArticles = true;

    /*Load first increment of articles*/
    loadArticlesIncrement(true);

});