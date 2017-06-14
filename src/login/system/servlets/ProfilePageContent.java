package login.system.servlets;

import login.system.dao.Article;
import login.system.dao.ArticleDAO;
import login.system.dao.User;
import login.system.dao.UserDAO;
import login.system.db.MySQL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by ycow194 on 6/06/2017.
 */
public class ProfilePageContent extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         /*If there is an attempt to access a servlet directly, check login status and redirect to login page or content page as is appropriate (method defined in LoginAttempt Servlet)*/
        LoginAttempt.loginStatusRedirection(request, response);
    }

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

             /*When used attempts to post new password*/
        /*Access user detail stored within the session*/

                User user = (User) session.getAttribute("userDetails");
                String original_username = user.getUsername();


                String username = request.getParameter("username");
                System.out.println(username);

                String firstname = request.getParameter("firstname");
                String lastname = request.getParameter("lastname");
                String occupation = request.getParameter("occupation");
                String location = request.getParameter("location");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String aboutme = request.getParameter("aboutme");


                int userUpdateStatus = UserDAO.updateUserDetails(DB, username, email, phone, occupation, location, aboutme, firstname, lastname, original_username);

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
                    response.sendRedirect("ProfilePage?username=" + user.getUsername() + "&userUpdate=invalideUsername");

                } else {

                    System.out.println("Database connectivity issues");
                    response.sendRedirect("ProfilePage?username=" + user.getUsername() + "&userUpdate=db");

                }

            }
        }
    }

}
