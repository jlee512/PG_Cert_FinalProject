package backend.servlets;

import backend.db.MySQL;
import backend.dao.User;
import backend.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static backend.servlets.AddAnArticleAttempt.inputContainsHTML;

/**
 * Created by ycow194 on 6/06/2017.
 */

/**
 * This servlet is used to process a user's request to edit their profile details
 */

public class ProfilePageContent extends HttpServlet {

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

        /*Verify that user is logged in*/
        HttpSession session = request.getSession(true);
        if (session.getAttribute("loginStatus") != "active") {
            response.sendRedirect("Login");

        } else {

              /*Check if session has timed out*/
            if (!LoginAttempt.sessionExpirationRedirection(request, response)) {

                /*Access user detail stored within the session*/
                User user = (User) session.getAttribute("userDetails");
                String original_username = user.getUsername();


                /*Process input form fields*/
                String username = request.getParameter("username");
                String firstname = request.getParameter("firstname");
                String lastname = request.getParameter("lastname");
                String occupation = request.getParameter("occupation");
                String location = request.getParameter("location");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String aboutme = request.getParameter("aboutme");

                /*Confirm if any of the user inputs contains HTML code (and potential cross-scripting)*/
                if (inputContainsHTML(firstname) || inputContainsHTML(lastname) || inputContainsHTML(occupation) || inputContainsHTML(location) || inputContainsHTML(email) || inputContainsHTML(phone) || inputContainsHTML(aboutme)) {

                    response.sendRedirect("ProfilePage?username=" + user.getUsername() + "&userUpdate=invalidUsername");
                    return;
                }

                /*Update the user's profile details*/
                int userUpdateStatus = UserDAO.updateUserDetails(DB, username, email, phone, occupation, location, aboutme, firstname, lastname, original_username);

                /*Depending on the profile update status, carry out corresponding action to inform the user*/
                if (userUpdateStatus == 1) {

                    user.setUsername(username);
                    user.setEmail(email);
                    user.setPhone(phone);
                    user.setOccupation(occupation);
                    user.setCity(location);
                    user.setProfile_description(aboutme);
                    user.setFirstname(firstname);
                    user.setLastname(lastname);

                    session.setMaxInactiveInterval(60 * 5);


                    session.setAttribute("userDetails", user);

                    System.out.println(((User) session.getAttribute("userDetails")).getUsername());

                    response.sendRedirect("ProfilePage?username=" + user.getUsername());
                } else if (userUpdateStatus == 2) {

                    System.out.println("Duplicate username");
                    response.sendRedirect("ProfilePage?username=" + user.getUsername() + "&userUpdate=duplicateUsername");

                } else if (userUpdateStatus == 3) {

                    System.out.println("Invalid username");
                    response.sendRedirect("ProfilePage?username=" + user.getUsername() + "&userUpdate=invalidUsername");

                } else {

                    System.out.println("Database connectivity issues");
                    response.sendRedirect("ProfilePage?username=" + user.getUsername() + "&userUpdate=db");

                }

            }
        }
    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
