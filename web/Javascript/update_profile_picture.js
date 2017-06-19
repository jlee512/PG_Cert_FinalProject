/**
 * Created by jlee512 on 10/06/2017.
 */

/*-------------------------------------------------------*/
/*This JavaScript file is used to toggle the change profile picture dialog on the user's profile pane.
* If the user opts to edit their photo their are given the choice between uploading a profile picture and a default profile picture*/
/*-------------------------------------------------------*/

$(document).ready(function () {


    $(".change-profile-pic-button").on('click', (function () {

            // Check to see whether the class (which will be added to the form) already exists
            /*If the profile picture dialog is open, close it on the button click*/
            if ($(".profile_pic_dialog_open")[0]) {

                $(".change-profile-pic-button").removeClass("profile_pic_dialog_open");
                $(".change-picture-options").empty();
                $(".change-profile-pic-button").html("<p><i class='fa fa-user-circle-o' aria-hidden='true'></i> Change Profile Picture</p>");


                /*If the profile picture dialog is closed, open it on the button click*/
            } else {
                $(".change-profile-pic-button").addClass("profile_pic_dialog_open");
                $(".change-profile-pic-button").html("<p><i class='fa fa-user-circle' aria-hidden='true'></i> Choose a New Profile Picture</p>");

                $(".change-picture-options").html("<form id='pictureForm' enctype='multipart/form-data' method='POST' action='UploadProfilePicture' style='display: inline-block;'>" +
                    "<button type='button' class='user-profile-pic-button btn btn-sm' style='color: black !important;'>" +
                    "<i class='fa fa-file-image-o' aria-hidden='true'></i>" +
                    " Upload Your Own" +
                    "</button> " +
                    "<input class='profile-image-upload' name='file' type='file' style='display: none;'>" +
                    "<button type='submit' class='submit-user-pic btn btn-sm' id='userChosenPicture' style='display: none;'" +
                    "</button>" +
                    "</form>" +
                    "<button type='button' class='default-profile-pic-button btn btn-sm' style='color: black !important;'>" +
                    "<i class='fa fa-archive' aria-hidden='true'></i>" +
                    " Select a Default" +
                    "</button>" +
                    "<div class='default-picture-choices' style='width: 100%;'></div>");
            }

            $(".user-profile-pic-button").on('click', (function () {

                    $(".profile-image-upload").click();

                })
            );

            $(".profile-image-upload").on('change', (function () {

                    $(".submit-user-pic").click();

                })
            );

            /*Second level toggle which presents the default profile picture options*/
            $(".default-profile-pic-button").on('click', (function () {


                    /*If the default profile picture options are visible and the button is clicked, hide them*/
                    if ($(".default_dialog_open")[0]) {
                    /*If the dialog is on, remove the buttons*/
                        $(".default-picture-choices").removeClass("default_dialog_open");
                        $(".default-picture-choices").empty();

                        /*If the default profile picture options are not visible and the button is clicked, show them*/
                    } else {

                        $(".default-picture-choices").addClass("default_dialog_open");
                        $(".default-picture-choices").append("<div id='defaultPic'><form id='defaultPictureForm' enctype='multipart/form-data' method='POST' action='UploadProfilePicture' style='display: inline-block;'>" +
                            "<div><button class='btn' type='submit' name='defaultPicture' value='Multimedia/DefaultProfilePictureOptions/kea.jpg' style='width: 25%; display: inline-block; padding: 0;'><img class='img-responsive img-thumbnail' style='width: 100%; margin: none;' align='middle' src='Multimedia/DefaultProfilePictureOptions/kea.jpg'></button>" +
                            "<button class='btn' type='submit' name='defaultPicture' value='Multimedia/DefaultProfilePictureOptions/kiwi.jpg' style='width: 25%; display: inline-block; padding: 0;'><img class='img-responsive img-thumbnail' style='width: 100%; margin: none;' align='middle' src='Multimedia/DefaultProfilePictureOptions/kiwi.jpg'></button>" +
                            "<button class='btn' type='submit' name='defaultPicture' value='Multimedia/DefaultProfilePictureOptions/kokako.jpg' style='width: 25%; display: inline-block; padding: 0;'><img class='img-responsive img-thumbnail' style='width: 100%; margin: none;' align='middle' src='Multimedia/DefaultProfilePictureOptions/kokako.jpg'></button></div>" +
                            "<div><button class='btn' type='submit' name='defaultPicture' value='Multimedia/DefaultProfilePictureOptions/manakura.jpg' style='width: 25%; display: inline-block; padding: 0;'><img class='img-responsive img-thumbnail' style='width: 100%; margin: none;' align='middle' src='Multimedia/DefaultProfilePictureOptions/manakura.jpg'></button>" +
                            "<button class='btn' type='submit' name='defaultPicture' value='Multimedia/DefaultProfilePictureOptions/si_robin.png' style='width: 25%; display: inline-block; padding: 0;'><img class='img-responsive img-thumbnail' style='width: 100%; margin: none;' align='middle' src='Multimedia/DefaultProfilePictureOptions/si_robin.png'></button>" +
                            "<button class='btn' type='submit' name='defaultPicture' value='Multimedia/DefaultProfilePictureOptions/tui.jpg' style='width: 25%; display: inline-block; padding: 0;'><img class='img-responsive img-thumbnail' style='width: 100%; margin: none;' align='middle' src='Multimedia/DefaultProfilePictureOptions/tui.jpg'></button></div></form>");

                    }


                })
            );

        })
    );

});

/*---------------------------*/
/*End of JavaScript file*/
/*---------------------------*/