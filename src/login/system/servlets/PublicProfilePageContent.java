package login.system.servlets;

import login.system.dao.User;
import login.system.dao.UserDAO;
import login.system.db.MySQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by cbla080 on 12/06/2017.
 */
public class PublicProfilePageContent extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Setup the database*/
        MySQL DB = new MySQL();

        //Don't let users see profiles if they are not logged in.
        HttpSession session = request.getSession(true);
        if (session.getAttribute("loginStatus") != "active") {
            response.sendRedirect("Login");

        } else {

              /*Check if session has timed out*/
            if (!LoginAttempt.sessionExpirationRedirection(request, response)) {

                //Pass the username as a parameter when clicking the link to the profile.
                String username = request.getParameter("username");

                //If the user is trying to navigate to their own page, go to the editable version.
                User currentUser = (User) session.getAttribute("userDetails");
                if (currentUser.getUsername().equals(username)) {
                    response.sendRedirect("ProfilePage?username=" + currentUser.getUsername());
                }

                //Otherwise forward the user information to the JSP.
                else {
                    User user = UserDAO.getUser(DB, username);
                    request.setAttribute("user", user);
                    getServletContext().getRequestDispatcher("/PublicProfilePage").forward(request, response);
                }

            }
        }
    }
}
