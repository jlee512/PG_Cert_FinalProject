package login.system.servlets;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
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
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by Julian on 13/06/2017.
 */
public class GoogleRegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*If there is an attempt to access a servlet directly, check login status and redirect to login page or content page as is appropriate (method defined below)*/
        LoginAttempt.loginStatusRedirection(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Establish a connection to the database*/
        MySQL DB = new MySQL();

        HttpTransport transport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory).setAudience(Collections.singletonList("17619298298-hlb3n0ra5pkquu73jbs8sir2m5i4b4b8.apps.googleusercontent.com")).build();

        // (Receive idTokenString by HTTPS POST)
        String idTokenString = request.getParameter("id_token");


        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(idTokenString);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            /*Setup user parameters*/
            String usernameInput = email;
            String[] names = name.split(" ", 2);
            String firstname = names[0];
            String lastname = names[1];
            /*Generate a random password and hash appropriately to prevent short-circuiting usual registration*/
            String password = UUID.randomUUID().toString().replaceAll("-","");
            String phoneInput = "";
            String occupationInput = "";
            String cityInput = "";
            String profile_descriptionInput = "";

            /*Profile picture upload to be added on a separate page, take default picture (Kokako) initially*/
            String profile_pictureStandard = "Multimedia/kokako.jpg";

            /*(1) Hash the password and convert to a blob,
            (2) Attempt to add user to the DB*/
            byte[] salt = Passwords.getNextSalt();
            int iterations = Passwords.getNextNumIterations();
            byte[] hash = Passwords.hash(password.toCharArray(), salt, iterations);

            int registrationStatus = UserDAO.addUserToDB(DB, usernameInput, iterations, salt, hash, email, phoneInput, occupationInput, cityInput, profile_descriptionInput, profile_pictureStandard, firstname, lastname);

            int user_id = registrationStatus;


        /*reset the registrationStatus variable to be 1 if the user is successfully added*/
        if (user_id >= 0) {
            registrationStatus = 1;
        }

        switch (registrationStatus) {
            case 1:
                System.out.println("User added successfully");
                User user = new User(usernameInput, hash, salt, iterations, email, phoneInput, occupationInput, cityInput, profile_descriptionInput, profile_pictureStandard, firstname, lastname);
                user.setUser_id(user_id);

                    /*If successful user additon, automatically login in user for the given session*/
                HttpSession session = request.getSession(true);
                session.setMaxInactiveInterval(60 * 5);

                    /*Attached user details and loginStatus to the HttpSession*/
                session.setAttribute("loginStatus", "active");
                session.setAttribute("userDetails", user);
                    /*Redirect the response to the Content Serv*/
                    /*Set a session attribute to confirm this is a google sign-in*/
                    session.setAttribute("googleSignIn", true);

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

        } else {

            System.out.println("Invalid ID token.");
            response.sendRedirect("Login?loginStatus=invalidGoogleSignIn");

        }

    }

}