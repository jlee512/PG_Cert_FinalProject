package login.system.servlets;

import login.system.dao.User;
import login.system.dao.UserDAO;
import login.system.db.MySQL;
import login.system.passwords.Passwords;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Julian on 30-May-17.
 */
public class ChangePasswordAttempt extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*If there is an attempt to access a servlet directly, check login status and redirect to login page or content page as is appropriate (method defined in LoginAttempt Servlet)*/
        LoginAttempt.loginStatusRedirection(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*Create link to the database*/
        MySQL DB = new MySQL();

        /*When used attempts to post new password*/
        /*Access user detail stored within the session*/

        HttpSession session = request.getSession(true);

        User user = (User) session.getAttribute("userDetails");
        String currentPasswordStr = request.getParameter("currentPassword");
        String newPasswordStr = request.getParameter("newPassword");
        String newPasswordStrVerify = request.getParameter("newPasswordVerify");

        /*Need to get user hash from the database to ensure most up-to-date password is referenced for validation purposes*/
        User userDBLookup = UserDAO.getUser(DB, user.getUsername());

        /*Verify that currentPasswordStr matches the users current password*/
        boolean currentPasswordValidity = Passwords.isExpectedPassword(currentPasswordStr.toCharArray(), userDBLookup.getSalt(), userDBLookup.getIterations(), userDBLookup.getHash());

        if (currentPasswordValidity) {
            /*If the current password is valid, check that the new password (entry 1 and 2) are the same*/
            if (!passwordInputVerification(newPasswordStr, newPasswordStrVerify)) {
                response.sendRedirect("ChangePassword?passwordChangeStatus=newPasswordMismatch&username=" + user.getUsername());
            } else {
                /*If the new login.passwords do match, updated the database with the new password hash, salt and iterations*/
                int passwordChangeStatus = UserDAO.updateUserPassword(DB, user, newPasswordStr);

                switch (passwordChangeStatus) {
                    case 1:
                        System.out.println("Password updated successfully");
                        user = UserDAO.getUser(DB, user.getUsername());
                        /*Update the user stored in the session*/
                        session.setAttribute("userDetails", user);

                    /*Redirect the response to the Content Serv*/
                        response.sendRedirect("Content?username=" + user.getUsername());
                        break;
                    case 2:
                    /*Unexpected exception but if there is a database error, send redirect to the Password change page to clarify the issue*/
                        System.out.println("User password update was invalid");
                        response.sendRedirect("ChangePassword?passwordChangeStatus=invalid&username=" + user.getUsername());
                        break;
                    case 3:
                        /*If no connection the database can be established print out a descriptive error message and redirect to the password change page*/
                        System.out.println("No connection to the database");
                        response.sendRedirect("ChangePassword?passwordChangeStatus=dbConn&username=" + user.getUsername());
                        break;
                }

            }
        } else {
            response.sendRedirect("ChangePassword?passwordChangeStatus=incorrect&username=" + user.getUsername());
        }



    }

        private boolean passwordInputVerification(String newPasswordInput, String newPasswordVerificationInput) {
            return newPasswordInput.equals(newPasswordVerificationInput);
        }
}
