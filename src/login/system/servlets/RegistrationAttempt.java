package login.system.servlets;

import login.system.dao.User;
import login.system.dao.UserDAO;
import login.system.db.MySQL;
import login.system.passwords.Passwords;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by jlee512 on 30/05/2017.
 */
public class RegistrationAttempt extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*If there is an attempt to access a servlet directly, check login status and redirect to login page or content page as is appropriate (method defined in LoginAttempt Servlet)*/
        LoginAttempt.loginStatusRedirection(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*Create link to the database*/
        MySQL DB = new MySQL();

        /*If user wishes to register, form will post to this method*/
        String usernameInput = request.getParameter("username");
        String emailInput = request.getParameter("email");
        String passwordInput = request.getParameter("password");
        String passwordVerificationInput = request.getParameter("passwordVerify");
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

            int registrationStatus = UserDAO.addUserToDB(DB, usernameInput, iterations, salt, hash, emailInput, phoneInput, occupationInput, cityInput, profile_descriptionInput, profile_pictureStandard, firstname, lastname);

            int user_id = registrationStatus;

            /*reset the registrationStatus variable to be 1 if the user is successfully added*/
            if (user_id >= 0) {
                registrationStatus = 1;
            }

            switch (registrationStatus) {
                case 1:
                    System.out.println("User added successfully");
                    User user = new User(usernameInput, hash, salt, iterations, emailInput, phoneInput, occupationInput, cityInput, profile_descriptionInput, profile_pictureStandard, firstname, lastname);
                    user.setUser_id(user_id);

                    /*If successful user additon, automatically login in user for the given session*/
                    HttpSession session = request.getSession(true);
                    session.setMaxInactiveInterval(60 * 5);

                    /*Attached user details and loginStatus to the HttpSession*/
                    session.setAttribute("loginStatus", "active");
                    session.setAttribute("userDetails", user);
                    /*Redirect the response to the Content Serv*/
                    response.sendRedirect("Content?username=" + user.getUsername());

                    break;
                case -2:
                    /*If username already exists, return the user to the registration page and display a descriptive message*/
                    System.out.println("User already exists within the database");
                    response.sendRedirect("Registration?registrationStatus=exists&username=" + usernameInput);
                    break;
                case -3:
                    /*If an invalid username is entered, return the user to the registration page and display a descriptive message*/
                    System.out.println("User could not be added to the database");
                    response.sendRedirect("Registration?registrationStatus=invalid");
                    break;
                case -4:
                    System.out.println("No connection to the database");
                    response.sendRedirect("Registration?registrationStatus=dbConn");
                    break;

            }

        }


    }

    private boolean passwordInputVerification(String passwordInput, String passwordVerificationInput) {
        return passwordInput.equals(passwordVerificationInput);
    }
}
