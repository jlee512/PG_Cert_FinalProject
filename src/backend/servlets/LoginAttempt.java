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

/**
 * Created by jlee512 on 29/05/2017.
 */

/**
 * This servlet is for processing log-in attempts for registered users
 */

public class LoginAttempt extends HttpServlet {

    /*Redirect if the servlet is directly accessed from the browser using a GET request*/
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*If there is an attempt to access a servlet directly, check login status and redirect to login page or content page as is appropriate (method defined below)*/
        loginStatusRedirection(request, response);
    }

    /*POST processing method*/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*Create link to the database*/
        MySQL DB = new MySQL();

        /*Read the form inputs*/
        String usernameAttempt = request.getParameter("username");
        String passwordAttempt = request.getParameter("password");

        /*Check whether any of the form inputs contain HTML code and re-direct accordingly*/
        if (AddAnArticleAttempt.inputContainsHTML(usernameAttempt) || AddAnArticleAttempt.inputContainsHTML(passwordAttempt)) {

            response.sendRedirect("Login?loginStatus=invalidUsername");
            return;

        }

        /*Convert password to char array and hash*/
        User user = UserDAO.getUser(DB, usernameAttempt);
        int verificationStatus = verifyUsernameAndPassword(user, passwordAttempt);

        /*Provide appropriate response depending on user credential verification process (method definition has additional detail)*/
        if (verificationStatus == 1){

            /*If user credentials are validated, create a session*/
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(60 * 60 * 24);

            /*Attach user details and loginStatus to the HttpSession*/
            session.setAttribute("loginStatus", "active");
            session.setAttribute("userDetails",user);
            response.sendRedirect("Content?username=" + user.getUsername());

        } else if (verificationStatus == 2) {
            response.sendRedirect("Login?loginStatus=invalidPassword&username=" + usernameAttempt);
        } else {
            response.sendRedirect("Login?loginStatus=invalidUsername");
        }

    }

    private int verifyUsernameAndPassword(User user, String passwordAttempt) {
        /*Returns an integer corresponding to method status,
        (1) - successful verification
        (2) - username verified but password incorrect
        (3) - username could not be verified
        */

        char[] passwordAttemptArray = passwordAttempt.toCharArray();

        /*Validate User Login using database query*/
        if (user.getUsername() != null || user.getHash() != null || user.getSalt() != null || user.getIterations() != -1) {
            if (Passwords.isExpectedPassword(passwordAttemptArray, user.getSalt(), user.getIterations(), user.getHash())) {
                return 1;
            } else {
                return 2;
            }
        } else {
            return 3;
        }
    }

    /*Method to check a user's login-status and redirect accordingly
    (this method is used across most user-sensitive servlets to confirm their login status)*/
    public static void loginStatusRedirection(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("loginStatus") != "active"){
            response.sendRedirect("Login");
        } else {
            User user = (User) session.getAttribute("userDetails");
            response.sendRedirect("Content?username=" + user.getUsername());
        }
    }

    /*Method developed to confirm that a user's session has not timed-out during a period of inactivity
    (this method is used across most user-sensitive servlets to confirm their login-status)*/
    public static boolean sessionExpirationRedirection(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean redirected = false;
        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute("userDetails");
        if (user == null){
            response.sendRedirect("Login?loginStatus=loggedOut");
            redirected = true;
        }
        return redirected;
    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
