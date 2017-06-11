/**
 * Created by jlee512 on 10/06/2017.
 */
$(document).ready(function () {


    $(".change-profile-pic-button").on('click', (function () {

            // Check to see whether the class (which will be added to the form) already exists
            if ($(".profile_pic_dialog_open")[0]) {

                $(".change-profile-pic-button").removeClass("profile_pic_dialog_open");
                $(".change-picture-options").empty();
                $(".change-profile-pic-button").html("<p><i class='fa fa-user-circle-o' aria-hidden='true'></i> Change Profile Picture</p>");

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

            $(".default-profile-pic-button").on('click', (function () {

                    if ($(".default_dialog_open")[0]) {

                        $(".default-picture-choices").removeClass("default_dialog_open");
                        $(".default-picture-choices").empty();

                    } else {

                        $(".default-picture-choices").addClass("default_dialog_open");
                        $(".default-picture-choices").append("<div id='defaultPic'  >" +
                            "<div><div style='width: 30%; display: inline-block;'><img class='img-responsive img-thumbnail' style='width: 100%; margin: none;' align='middle' src='Multimedia/DefaultProfilePictureOptions/kea.jpg'></div>" +
                            "<div style='width: 30%; display: inline-block;'><img class='img-responsive img-thumbnail' style='width: 100%; margin: none;' align='middle' src='Multimedia/DefaultProfilePictureOptions/kiwi.jpg'></div>" +
                            "<div style='width: 30%; display: inline-block;'><img class='img-responsive img-thumbnail' style='width: 100%; margin: none;' align='middle' src='Multimedia/DefaultProfilePictureOptions/kokako.jpg'></div></div>" +
                            "<div><div style='width: 30%; display: inline-block;'><img class='img-responsive img-thumbnail' style='width: 100%; margin: none;' align='middle' src='Multimedia/DefaultProfilePictureOptions/manakura.jpg'></div>" +
                            "<div style='width: 30%; display: inline-block;'><img class='img-responsive img-thumbnail' style='width: 100%; margin: none;' align='middle' src='Multimedia/DefaultProfilePictureOptions/si_robin.png'></div>" +
                            "<div style='width: 30%; display: inline-block;'><img class='img-responsive img-thumbnail' style='width: 100%; margin: none;' align='middle' src='Multimedia/DefaultProfilePictureOptions/tui.jpg'></div></div>");

                    }


                })
            );

        })
    );

});

//     //If you select choose default a drop down appears of images you can select
//         $("#chooseDefault").click(function () {
//
//             //Checks to see whether the class (which is added to the divv contianing the images) already exists
//             if (!$(".defaultPhoto_dialog_open")[0]) {
//
//                 $("#defaultPictureDiv").append("<div id='defaultPic'  >" +
//                     "<label><input type='radio' name='defaultPicture' value='Multimedia/DefaultProfilePictureOptions/kea.jpg'>" +
//                     "<img class='fixedDefaultPictureSize' align='middle' src='Multimedia/DefaultProfilePictureOptions/kea.jpg'></label>" +
//                     "<label><input type='radio' name='defaultPicture' value='Multimedia/DefaultProfilePictureOptions/kiwi.jpg'>" +
//                     "<img class='fixedDefaultPictureSize' align='middle' src='Multimedia/DefaultProfilePictureOptions/kiwi.jpg'></label>" +
//                     "<label><input type='radio' name='defaultPicture' value='Multimedia/DefaultProfilePictureOptions/kokako.jpg'>" +
//                     "<img class='fixedDefaultPictureSize' align='middle' src='Multimedia/DefaultProfilePictureOptions/kokako.jpg'></label>" +
//                     "<label><input type='radio' name='defaultPicture' value='Multimedia/DefaultProfilePictureOptions/manakura.jpg'>" +
//                     "<img class='fixedDefaultPictureSize' align='middle' src='Multimedia/DefaultProfilePictureOptions/manakura.jpg'></div></label>");
//                 $("#defaultPictureDiv").addClass("defaultPhoto_dialog_open");
//                 $(".profile-image-upload").remove();
//             }
//
//             //toggle function for showing the default images
//             else {
//                 $("#pictureForm").append("<input id='file' class='profile-image-upload' name='file' type='file'>");
//                 $("#defaultPictureDiv").removeClass("defaultPhoto_dialog_open");
//                 $("#defaultPic").remove();
//             }
//         })
//     }
//
//     //When the image is clicked remove the form AND if the default pictures are showing then remove them too
//     else {
//         $("#profilePictureButtons").removeClass("photo-settings");
//         $("#pictureForm").remove();
//         if ($("#defaultPic").length) {
//             $("#defaultPictureDiv").removeClass("defaultPhoto_dialog_open");
//             $("#defaultPic").remove();
//         }
//     }
// });