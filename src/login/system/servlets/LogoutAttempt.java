package login.system.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Julian on 29-May-17.
 */
public class LogoutAttempt extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        if (session.getAttribute("loginStatus") != null) {
            session.removeAttribute("loginStatus");
            session.removeAttribute("userDetails");
        /*Invalidate session and redirect to login page*/
            session.invalidate();
            response.sendRedirect("Login");
        } else {
        /*Else redirect to login page and print message saying that the user is no longer logged in*/
            session.invalidate();
            response.sendRedirect("Login?loginStatus=loggedOut");
        }
    }



}
