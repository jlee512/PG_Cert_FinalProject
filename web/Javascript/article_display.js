/**
 * Created by jlee512 on 5/06/2017.
 */

// Created a template which will be used for inserting new article HTML.
var articleTemplate =
    "<div class='panel panel-default pgcertArticle'>" +
    "<div class='panel-heading'></div>" +
    "<div class='panel-body'>" +
    "<p><strong class='author-name'></strong></p>" +
    "<p class='main-content'></p>" +
    "<div class='text-center bg-info'>Show full content</div>" +
    "</div>" +
    "</div>";


// A template which will be used for inserting new Users HTML.
var userTemplate =
    "<div class='panel panel-default pgcertUser'>" +
    "<div class='panel-heading'>User name</div>" +
    "<div class='panel-body'>" +
    "<p><strong>First Name:</strong> <span>the name</span></p>" +
    "<p><strong>Last Name:</strong> <span>the name</span></p>" +
    "<p><strong>Occupation:</strong> <span>the occupation</span></p>" +
    "<ul class='likesList'>" +
    "</ul>" +
    "</div>" +
    "</div>";


$("document").ready(function () {

    $.ajax({

        url: '/MainContentAccess',
        dataType: 'json',
        type: 'GET',
        dataType: 'text',
        success: function (msg) {

            console.log("Test");
            console.log(msg);

            // var container = $(".articleContainer");
            //
            // console.log(msg.length);
            //
            // for (var i = 0; i < msg.length; i++) {
            //
            //     console.log("test " + i);
            //
            //     var article = msg[i];
            //
            //     console.log(article);
            //
            //     var articleDiv = $(articleTemplate);
            //     articleDiv.find(".panel-heading").text(article.article_title);
            //     var contentDiv = articleDiv.find(".main-content");
            //     contentDiv.text(article.article_body);
            //     var authorField = articleDiv.find(".author-name");
            //
            //     console.log(article.author_firstname);
            //     console.log(article.author_lastname);
            //     console.log(article.author_username);

        },
    });

});

function onLoadArticlesFailure(msg) {

    console.log("failed to load articles");

}