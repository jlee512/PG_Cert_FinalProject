/**
 * Created by ycow194 on 13/06/2017.
 */

var addMultimediaForm = "<div><form action='UploadMultimedia' method='post'>" +
    "<label for='photoOrVideo'>Upload Multimedia</label>" +
    "<input type='file' name='photoOrVideo'>" +
    "<label for='youtubeLink'>Youtube Link</label>" +
    "<input type='text' name='youtubeLink'>" +
    "<button type='submit' class='btn btn-sm'>Upload Media</button>" +
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
        $('.add_multimedia', multimediaForm).val(article_id);
        $(this).next().append(multimediaForm);
    }

}));


