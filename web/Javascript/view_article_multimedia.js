/**
 * Created by Julia on 15/06/2017.
 */

/*-------------------------------------------------------*/
/*This JavaScript file is used to view the multimedia as part of an individual article*/
/*-------------------------------------------------------*/

// Created a template which will be used for inserting multimedia into the article
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
var article_id = getArticleID();
var from = 0;
var count = 4;
var moreMultimedia = true;

/*-------------------------------------------------------*/
/*If a successful AJAX call is made, this function is called to process the results and populate the 'uploadedPhotos' container div and the 'uploadedVideos' container div*/
function successfulMultimediaLoad(msg) {

    var photoContainer = $(".uploadedPhotos");
    var videoContainer = $(".uploadedVideos");

    if (msg.length == 0) {
        /*Hide the loader picture, show the loaded underline and return that their are no further multimedia*/
        $('.loader-wrapper').hide();
        $('#loaded1, #loaded2, #loaded3, #loaded4').show();
        moreMultimedia = false;

    } else {
        for (var i = 0; i < msg.length; i++) {

            var multimedia = msg[i];
            var multimediaDiv = $(multimediaTemplate);

            //Set the title using the username and hyperlinking it to their profile
            multimediaDiv.find(".panel-title").append("<div style='width: 65%; display: inline-block; text-overflow: ellipsis; overflow: hidden; white-space: nowrap;'>Published by: <strong><a href='PublicProfile?username=" + multimedia.username + "'style='color: #f9a825;'>" + multimedia.username + "</a></strong></div>");

            /*Filter the msg results by the file extension*/

            //IMAGE//
            if (multimedia.file_type == ".jpeg" || multimedia.file_type == ".png" || multimedia.file_type == ".jpg" || multimedia.file_type == ".gif") {

                multimediaDiv.find(".panel-body").html("<img src='" + multimedia.file_path + "' class='img-fluid center-block' >");

                photoContainer.append(multimediaDiv)

            }

            //UPLOADED VIDEO
            if (multimedia.file_type == ".mp4" || multimedia.file_type == "mpeg-4") {

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
/*AJAX call to ViewArticleMultimedia servlet to access an increment of multimedia*/
function loadMultimediaIncrement() {

    /*Show the multimedia loader*/
    $('.loader-wrapper').show();

    /*Start an AJAX call to load more multimedia*/
    $.ajax({

        url: 'ViewArticleMultimedia',
        type: 'GET',
        data: {article_id: article_id},
        success: function (msg) {
            successfulMultimediaLoad(msg);
        },
        error: failedMultimediaLoad
    });

    /*Increment the current "from" by the count so that next time the function is called, the next set of multimedia is loaded*/
    from += count;
}

/*-------------------------------------------------------*/
/*On loading, populate the page with the first round of multimedia*/
$(document).ready(function () {

    $('#loaded1, #loaded2, #loaded3, #loaded4').hide();

    /*Load initial four multimedia*/
    loadMultimediaIncrement();
});

function getArticleID () {
    /*Create the cookie search text*/
    var cookieName = "article_id=";

    /*Decode the cookie string */
    var decodedCookie = decodeURIComponent(document.cookie);

    /*Split the document.cookie result based on semi colons*/
    var cookieArray = decodedCookie.split(';');

    for (var i = 0; i < cookieArray.length; i++) {
        /*Access cookies one-by-one (crumb-by-crumb XD)*/
        var cookie = cookieArray[i];
        /*Trim any whitespace*/
        cookie = cookie.replace(/ /g, '');
        /*If the cookie is found, return the value of the cookie*/
        if (cookie.indexOf(cookieName) == 0 ) {
            return cookie.substring(cookieName.length, cookie.length);
        }
    }
    /*Return blank if not found*/
    return "";
}

/*---------------------------*/
/*End of JavaScript file*/
/*---------------------------*/