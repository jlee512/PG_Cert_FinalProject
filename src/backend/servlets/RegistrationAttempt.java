package backend.servlets;

import backend.db.MySQL;
import backend.dao.User;
import backend.dao.UserDAO;
import backend.passwords.Passwords;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static backend.servlets.AddAnArticleAttempt.inputContainsHTML;

/**
 * Created by jlee512 on 30/05/2017.
 */

/**
 * This servlet has been developed to process new user registrations (non-google-sign-in)
 */

public class RegistrationAttempt extends HttpServlet {


    /*If there is an attempt to access a servlet directly, check login status and redirect to login page or content page as is appropriate (method defined in LoginAttempt Servlet)*/
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LoginAttempt.loginStatusRedirection(request, response);
    }

    /*POST processing method*/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Create link to the database*/
        MySQL DB = new MySQL();

        /*If user attempts to register, the form will post to this method
        * Process input form fields*/
        String usernameInput = request.getParameter("username");
        String emailInput = request.getParameter("email");
        String passwordInput = request.getParameter("password");
        String passwordVerificationInput = request.getParameter("passwordVerify");

        /*Confirm whether any of the input form fields have HTML code input and redirect accordingly*/
        if (inputContainsHTML(usernameInput) || inputContainsHTML(emailInput) || inputContainsHTML(passwordInput) || inputContainsHTML(passwordVerificationInput)) {

            response.sendRedirect("Registration?registrationStatus=invalid");
            return;

        }

        /*Initialise empty user field inputs for currently unavailable expanded user profile details*/
        String phoneInput = "";
        String occupationInput = "";
        String cityInput = "";
        String profile_descriptionInput = "";
        String firstname = "";
        String lastname = "";

        /*Profile picture upload to be added on a separate page, take default picture (Kokako) initially*/
        String profile_pictureStandard = "Multimedia/kokako.jpg";

        /*With user input, check username uniqueness*/
        if (!passwordInputVerification(passwordInput, passwordVerificationInput)) {
            response.sendRedirect("Registration?registrationStatus=passwordMismatch");
        } else {
            /*If password inputs match, progress to user addition process
            (1) Hash the password and convert to a blob,
             (2) Attempt to add user to the DB*/
            byte[] salt = Passwords.getNextSalt();
            int iterations = Passwords.getNextNumIterations();
            byte[] hash = Passwords.hash(passwordInput.toCharArray(), salt, iterations);

            String recaptcha_response = request.getParameter("g-recaptcha-response");

            /*If no recaptcha entry, redirect to the registration page*/
            boolean verify = VerifyRecaptcha.verify(recaptcha_response);
            if (recaptcha_response == null || recaptcha_response.length() == 0) {

                response.sendRedirect("Registration?registrationStatus=recaptchaNull");
                return;
            }

            /*Register the user if they are 'human'*/
            int registrationStatus = UserDAO.addUserToDB(DB, usernameInput, iterations, salt, hash, emailInput, phoneInput, occupationInput, cityInput, profile_descriptionInput, profile_pictureStandard, firstname, lastname);

            int user_id = registrationStatus;

            /*Reset the registrationStatus variable to be 1 if the user is successfully added*/
            if (user_id >= 0) {
                registrationStatus = 1;
            }

            switch (registrationStatus) {
                case 1:
                    User user = new User(usernameInput, hash, salt, iterations, emailInput, phoneInput, occupationInput, cityInput, profile_descriptionInput, profile_pictureStandard, firstname, lastname);
                    user.setUser_id(user_id);

                    /*If successful user additon, automatically login in user for the given session*/
                    HttpSession session = request.getSession(true);
                    session.setMaxInactiveInterval(60 * 5);

                    /*Attached user details and loginStatus to the HttpSession*/
                    session.setAttribute("loginStatus", "active");
                    session.setAttribute("userDetails", user);
                    /*Redirect the response to the Content Servlet*/
                    response.sendRedirect("Content?username=" + user.getUsername());

                    break;
                case -2:
                    /*If username already exists, return the user to the registration page and display a descriptive message*/
                    response.sendRedirect("Registration?registrationStatus=exists&username=" + usernameInput);
                    break;
                case -3:
                    /*If an invalid username is entered, return the user to the registration page and display a descriptive message*/
                    response.sendRedirect("Registration?registrationStatus=invalid");
                    break;
                case -4:
                    /*Database connection issues*/
                    response.sendRedirect("Registration?registrationStatus=dbConn");
                    break;

            }

        }


    }

    private boolean passwordInputVerification(String passwordInput, String passwordVerificationInput) {
        return passwordInput.equals(passwordVerificationInput);
    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
