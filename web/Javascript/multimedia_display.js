/**
 * Created by Yuri on 12/06/2017.
 */
// Created a template which will be used for inserting new article HTML.
var multimediaTemplate =

    "<div class='col-sm-3'><div class='panel panel-default' style='margin: 10px;'>" +
    "<div class='panel-heading article-heading' style='background-color: #00acc1; color: white'>" +
    "<h3 class='panel-title'></h3>" +
    "</div>" +
    "<div class='panel-body'>" +
    "</div>" +
    "</div></div>";


/*jQuery function to animate each article header on hover*/

function hoverBackgroundColor() {

    $(this).find('.multimedia-heading').stop().animate({
        backgroundColor: "#ffd54f",
        color: "black"
    }, 'slow');

}

function normalBackgroundColor() {

    $(this).find('.multimedia-heading').stop().animate({
        backgroundColor: "#00acc1",
        color: "white"
    }, 'slow');

}

/*jQuery function to animate headings of articles as they are hovered over*/

$('div.news_feed').on('mouseenter', '.individualMultimediaLink', hoverBackgroundColor);

$('div.news_feed').on('mouseleave', '.individualMultimediaLink', normalBackgroundColor);

/* Setup to/count article variables to store the state of article loading on the page at a given point in time*/
var from = 0;
var count = 4;
var moreMultimedia = true;


function successfulArticleLoad(msg) {

    var multimediaContainer = $(".news_feed");

    console.log(msg.length);
    console.log(count);

    if (msg.length == 0) {
        /*Hide the loader picture, show the loaded underline and return that their are no further articles*/
        $('.loader-wrapper').hide();
        $('#loaded1, #loaded2, #loaded3, #loaded4').show();
        moreMultimedia = false;

    } else {
        for (var i = 0; i < msg.length; i++) {

            var multimedia = msg[i];

            var multimediaDiv = $(multimediaTemplate);

            multimediaDiv.find(".panel-title").text(multimedia.multimedia_title);

            if (multimedia.file_type == ".jpeg" || multimedia.file_type == ".png" || multimedia.file_type == ".jpg") {
                multimediaDiv.find(".panel-body").html("<img width='100px' height='100px' src='" + multimedia.file_path + "' >");
                multimediaDiv.find(".panel-body").css("text-align", "left");
            }

            if (multimedia.file_type == ".mp4") {
                multimediaDiv.find(".panel-body").html("<video controls width='200px' height='200px'><source src='" + multimedia.file_path + "' type='video/mp4'></video>");
                multimediaDiv.find(".panel-body").css("text-align", "left");
            }

            if (multimedia.file_type == ".web") {
                multimediaDiv.find(".panel-body").html(multimedia.file_path);
                multimediaDiv.find(".panel-body").css("text-align", "left");
            }

            /*Append the respective content to the gallery*/
            multimediaContainer.append(multimediaDiv);

            /*Remove the loading icon*/
            $('.loader-wrapper').hide();


            if (msg.length < count) {
                console.log("inside if statement multimedia");
                $('.loader-wrapper').hide();
                $('#loaded1, #loaded2, #loaded3, #loaded4').show();
                moreMultimedia = false;
            }

        }


    }
}

function failedArticleLoad(jqXHR, textStatus, errorThrown) {

    console.log(jqXHR.status);
    console.log(textStatus);
    console.log(errorThrown);

}

function loadMultimediaIncrement() {

    /*Show the articles loader*/
    $('.loader-wrapper').show();

    /*Start an AJAX call to load more articles*/
    $.ajax({

        url: '/MultimediaContent',
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
    $(window).scroll(function () {

        /*Function to facilitate infinite scrolling of articles*/
        if ($(document).height() - window.innerHeight == $(window).scrollTop() & moreMultimedia) {
            loadMultimediaIncrement();
        }
    });

    /*Load initial four articles*/
    loadMultimediaIncrement();
});