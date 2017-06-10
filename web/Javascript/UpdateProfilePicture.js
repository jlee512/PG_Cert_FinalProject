/**
 * Created by jlee512 on 10/06/2017.
 */
$(".change-profile-pic-button").click(function () {

    // Check to see whether the class (which will be added to the form) already exists
    if (!$(".photo-settings")[0]) {


    //Append the form and add 'photo-settings' class to it
        $("#profilePictureButtons").append("<form id='pictureForm' enctype='multipart/form-data' method='POST' action='UploadProfilePicture'>" +
            "<button type='button' class='btn btn-sm' style='background-color: #f9a825'  id='chooseDefault'>Choose a Default Picture</button>" +
            "<button type='submit' class='btn btn-sm' style='background-color: #f9a825' id='updatePicture'>Submit</button><div></div>" +
            "<input class='profile-image-upload' name='file' type='file'>" +
            "</form>");
        $("#pictureForm").addClass("photo-settings");

    //If you select choose default a drop down appears of images you can select
        $("#chooseDefault").click(function () {

            //Checks to see whether the class (which is added to the divv contianing the images) already exists
            if (!$(".defaultPhoto_dialog_open")[0]) {

                $("#defaultPictureDiv").append("<div id='defaultPic'  >" +
                    "<label><input type='radio' name='defaultPicture' value='Multimedia/DefaultProfilePictureOptions/kea.jpg'>" +
                    "<img class='fixedDefaultPictureSize' align='middle' src='Multimedia/DefaultProfilePictureOptions/kea.jpg'></label>" +
                    "<label><input type='radio' name='defaultPicture' value='Multimedia/DefaultProfilePictureOptions/kiwi.jpg'>" +
                    "<img class='fixedDefaultPictureSize' align='middle' src='Multimedia/DefaultProfilePictureOptions/kiwi.jpg'></label>" +
                    "<label><input type='radio' name='defaultPicture' value='Multimedia/DefaultProfilePictureOptions/kokako.jpg'>" +
                    "<img class='fixedDefaultPictureSize' align='middle' src='Multimedia/DefaultProfilePictureOptions/kokako.jpg'></label>" +
                    "<label><input type='radio' name='defaultPicture' value='Multimedia/DefaultProfilePictureOptions/manakura.jpg'>" +
                    "<img class='fixedDefaultPictureSize' align='middle' src='Multimedia/DefaultProfilePictureOptions/manakura.jpg'></div></label>");
                $("#defaultPictureDiv").addClass("defaultPhoto_dialog_open");
                $(".profile-image-upload").remove();
            }

            //toggle function for showing the default images
            else {
                $("#pictureForm").append("<input id='file' class='profile-image-upload' name='file' type='file'>");
                $("#defaultPictureDiv").removeClass("defaultPhoto_dialog_open");
                $("#defaultPic").remove();
            }
        })
    }

    //When the image is clicked remove the form AND if the default pictures are showing then remove them too
    else {
        $("#profilePictureButtons").removeClass("photo-settings");
        $("#pictureForm").remove();
        if ($("#defaultPic").length) {
            $("#defaultPictureDiv").removeClass("defaultPhoto_dialog_open");
            $("#defaultPic").remove();
        }
    }
});