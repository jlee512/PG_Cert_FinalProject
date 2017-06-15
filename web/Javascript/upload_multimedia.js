/**
 * Created by ycow194 on 14/06/2017.
 */
/**
 * Created by ycow194 on 13/06/2017.
 */

var addMultimediaForm = "<div><form action='UploadMultimedia' method='post' enctype='multipart/form-data'>" +
    "<label for='photoOrVideo'>Upload Multimedia</label>" +
    "<input type='file' name='photoOrVideo' title='Valid file types: .png, .jpg, .jpeg, .gif, .mp3, .mp4'>" +
    "<label for='youtubeLink'>Youtube Link</label>" +
    "<input type='text' name='youtubeLink' title='Please upload a valid youtube link' pattern='^(https?\:\/\/)?(www\.youtube\.com|youtu\.?be)\/.+$'>" +
    "<button type='submit' name='uploadArticleId' class='btn btn-sm uploadArticleID' style='background-color: #00acc1;' >Upload Media <i class='fa fa-check' aria-hidden='true'></i></button>" +
    "</form></div>";


$('.panel-body').on('click', '.add_multimedia', (function (e) {

    e.stopPropagation();

    var multimediaForm = $(addMultimediaForm);
    var article_id = $(this).parent().attr("id");


    if ($(this).parent().parent().hasClass("add_multimedia_on")) {

        $(this).next().empty();
        $(this).parent().parent().removeClass("add_multimedia_on");

    } else {

        $(this).parent().parent().addClass("add_multimedia_on");
        console.log(article_id);
        $('.uploadArticleID', multimediaForm).val(article_id);
        $(this).next().append(multimediaForm);
    }

}));


