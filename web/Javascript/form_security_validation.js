/**
 * Created by catherinedechalain on 15/06/17.
 */
$(document).ready(function () {

    var inputs = document.getElementsByTagName("input");
    console.log(inputs.length);

    $("input").blur(function () {
        var x = $(this).val();
        console.log(x);

        var illegalChars = /<[^>]*>/;

        if (illegalChars.test(x)){
            alert("You have entered an illegal character.")
            return false;
        }

        return true;

    })
});