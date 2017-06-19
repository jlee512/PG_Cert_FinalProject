/**
 * Created by Yuri on 12/06/2017.
 */

/*-------------------------------------------------------*/
/*This JavaScript file is used to display multimedia for the full Multimedia Gallery.
* Multimedia are accessed from the MultimediaContent servlet using AJAX calls*/
/*-------------------------------------------------------*/

// Created a template which will be used for inserting new multimedia HTML.
var multimediaTemplate =

    "<div class='panel panel-default' style='margin: 10px;'>" +
    "<div class='panel-heading multimedia-heading' style='background-color: #00acc1; color: white;'>" +
    "<div class='panel-title'></div>" +
    "</div>" +
    "<div class='panel-body'>" +
    "</div>" +
    "</div>";


/*-------------------------------------------------------*/

/*jQuery function to animate each multimedia header on hover*/
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

$('div.news_feed').on('mouseenter', '.individualMultimediaLink', hoverBackgroundColor);

$('div.news_feed').on('mouseleave', '.individualMultimediaLink', normalBackgroundColor);

/*-------------------------------------------------------*/

/* Setup global variables to store the state of multimedia loading on the page at a given point in time*/
var from = 0;
var count = 6;
var moreMultimedia = true;

/*-------------------------------------------------------*/


/*If a successful AJAX call is made, this function is called to process the results and populate the 'uploadedPhotos' container div and the 'uploadedVideos' container div*/
function successfulMultimediaLoad(msg) {

    var photoContainer = $(".uploadedPhotos");
    var videoContainer = $(".uploadedVideos");

    if (msg.length == 0) {
        /*Hide the loader picture, show the loaded underline and return that their are no further multimedias*/
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

            /*Screen the AJAX messages returned by the file extension*/

            //IMAGE//
            if (multimedia.file_type == ".jpeg" || multimedia.file_type == ".png" || multimedia.file_type == ".jpg") {

                multimediaDiv.find(".panel-body").html("<img src='" + multimedia.file_path + "' class='img-fluid center-block' >");

                photoContainer.append(multimediaDiv)

            }

            //UPLOADED VIDEO
            if (multimedia.file_type == ".mp4" || multimedia.file_type == ".mpeg-4") {

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

/*-------------------------------------------------------*/
/*If the AJAX call is failed, output an error message to the console*/
function failedMultimediaLoad(jqXHR, textStatus, errorThrown) {

    console.log(jqXHR.status);
    console.log(textStatus);
    console.log(errorThrown);

}

/*-------------------------------------------------------*/
/*AJAX call to MultimediaContent servlet to access an increment of multimedia*/
function loadMultimediaIncrement() {

    /*Show the multimedias loader*/
    $('.loader-wrapper').show();

    /*Start an AJAX call to load more multimedia*/
    $.ajax({

        url: 'MultimediaContent',
        type: 'GET',
        data: {from: from, count: count},
        success: function (msg) {
            successfulMultimediaLoad(msg);
        },
        error: failedMultimediaLoad
    });

    /*Increment the current "from" by the count so that next time the function is called, the next set of multimedia is is loaded*/
    from += count;
}

/*-------------------------------------------------------*/
/*On loading, populate the page with the first round of multimedia*/
$(document).ready(function () {

    $('#loaded1, #loaded2, #loaded3, #loaded4').hide();

    /*Add in infinite scrolling to load more multimedia*/
    $(window).scroll(function () {

        /*Function to facilitate infinite scrolling of multimedia*/
        if ($(document).height() - window.innerHeight <= ($(window).scrollTop() + 10) && moreMultimedia) {

            loadMultimediaIncrement();
        }
    });

    /*Load initial six multimedia*/
    loadMultimediaIncrement();
});

/*---------------------------*/
/*End of JavaScript file*/
/*---------------------------*/