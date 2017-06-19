/**
 * Created by catherinedechalain on 15/06/17.
 */

/*-------------------------------------------------------*/
/*This JavaScript file is used to check for HTML cross-scripting in form input fields on the client side
 Note: following user-testing, server-side scripting has also been implemented to screen for cross-scripting
 ------------------------------------------------------*/

//Alert users who enter illegal characters into form fields.
$(document).ready(function () {

    //Get all form fields on the page.
    var inputs = document.getElementsByTagName("input");

    //For each input, when field loses focus alert the user if they have entered illegal characters.
    $("input").blur(function () {
        var x = $(this).val();

        var illegalChars = /<[^>]*>/;

        if (illegalChars.test(x)){
            alert("You have entered an illegal character.");
            return false;
        }

        return true;

    })
});

/*---------------------------*/
/*End of JavaScript file*/
/*---------------------------*/