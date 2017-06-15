<%--
  Created by IntelliJ IDEA.
  User: ycow194
  Date: 2/06/2017
  Time: 1:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Includes all necessary styling for the head of jsp/html pages--%>

<%--Font awesome--%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Latest html skins from mdbootstrap.com-->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.3.0/css/mdb.min.css">

<%--Responsiveness for all screen sizes--%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.12.4.js"></script>
<script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<%-- Random Background image--%>
<script>
    $(document).ready(function () {



        // Represents the number of photos
        var upperLimit = 7;
        // Randomise background photo
        var randomNum = Math.floor((Math.random() * upperLimit) + 1);


        // Edits the backGroundImage class to add a random image each time the webpage is loaded
        $(".backGroundImage").css("background-image", "url('Multimedia/BackgroundImages/" + randomNum + ".jpg')");//<--changed path


    });
</script>


<style>
    #submit {
        padding: 2%;
    }

    .backGroundImage {
        /*background-image: url("Multimedia/BackgroundImages/2.jpg");*/
        height: 100%;
        background-position: center;
        background-repeat: no-repeat;
        background-size: cover;
        background-color: #def4f7;
    }

    .setOpacity {
        background-color: #FFF8FE;
        opacity: 0.88;

    }

    .btn-primary {

        font-size: 1.2em;
        font-style: normal;

    }

    body {

        background-color: #fbfff9;

    }

</style>