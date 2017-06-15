/**
 * Created by Yuri on 12/06/2017.
 */
// Created a template which will be used for inserting new article HTML.
var multimediaTemplate =

    "<div class='panel panel-default' style='margin: 10px;'>" +
    "<div class='panel-heading article-heading' style='background-color: #00acc1; color: white;'>" +
    "<div class='panel-title'></div>" +
    "</div>" +
    "<div class='panel-body'>" +
    "</div>" +
    "</div>";


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
var count = 6;
var moreMultimedia = true;


function successfulArticleLoad(msg) {

    var photoContainer = $(".uploadedPhotos");
    var videoContainer = $(".uploadedVideos");

    if (msg.length == 0) {
        /*Hide the loader picture, show the loaded underline and return that their are no further articles*/
        $('.loader-wrapper').hide();
        $('#loaded1, #loaded2, #loaded3, #loaded4').show();
        moreMultimedia = false;

    } else {
        for (var i = 0; i < msg.length; i++) {

            var multimedia = msg[i];
            var multimediaDiv = $(multimediaTemplate);

            //Set the title using the username and hyperlinking it to their profile
            multimediaDiv.find(".panel-title").append("<div style='width: 65%; display: inline-block; text-overflow: ellipsis; overflow: hidden; white-space: nowrap;'>Published by: <strong><a href='PublicProfile?username=" + multimedia.username + "'style='color: #f9a825;'>" + multimedia.username + "</a></strong></div>");

            multimediaDiv.find(".panel-title").append("<a href='ViewArticle?article_id=" + multimedia.article_id + "'style='color: white;'><div class='view_comments pull-right' style='display: inline-block; padding: 5px 10px 5px 10px; background-color: #64dd17; border-radius: 2px;'><strong>" + "<i class='fa fa-eye' aria-hidden='true'></i></strong></div></a>");


            //IMAGE//
            if (multimedia.file_type == ".jpeg" || multimedia.file_type == ".png" || multimedia.file_type == ".jpg") {

                multimediaDiv.find(".panel-body").html("<img src='" + multimedia.file_path + "' class='img-fluid center-block' >");

                photoContainer.append(multimediaDiv)

            }

            //UPLOADED VIDEO
            if (multimedia.file_type == ".mp4" || multimedia.file_type == ".mov" || multimedia.file_type == ".m4v" || multimedia.file_type == ".mpeg-4") {

                multimediaDiv.find(".panel-body").html("<div align='center' class='embed-responsive embed-responsive-16by9'><video class='embed-responsive-item' controls ><source src='" + multimedia.file_path + "' type='video/mp4'></video></div>");
                multimediaDiv.find(".panel-body").css("text-align", "left");

                videoContainer.append(multimediaDiv);

            }

            //UPLOADED MP3
            if (multimedia.file_type == ".mp3") {

                multimediaDiv.find(".panel-body").html("<audio controls><source src='" + multimedia.file_path + "' type='audio/ogg'></audio>");
                multimediaDiv.find(".panel-body").css("text-align", "left");

                videoContainer.append(multimediaDiv);
            }

            //YOUTUBE LINK
            if (multimedia.file_type == ".web") {

                multimediaDiv.find(".panel-body").html("<div align='center' class='embed-responsive embed-responsive-16by9'>" + multimedia.file_path + "</div>");
                multimediaDiv.find(".panel-body").css("text-align", "left");

                videoContainer.append(multimediaDiv);
            }
        }

        /*Remove the loading icon*/
        $('.loader-wrapper').hide();

        if (msg.length < count) {
            $('.loader-wrapper').hide();
            $('#loaded1, #loaded2, #loaded3, #loaded4').show();
            moreMultimedia = false;
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

        url: 'MultimediaContent',
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
        if ($(document).height() - window.innerHeight <= ($(window).scrollTop() + 10) && moreMultimedia) {

            loadMultimediaIncrement();
        }
    });

    /*Load initial four articles*/
    loadMultimediaIncrement();
});