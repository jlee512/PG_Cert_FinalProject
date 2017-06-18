package backend.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import backend.dao.User;
import backend.dao.UserDAO;
import backend.db.MySQL;

/*Addition libraries required in this verification method*/
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * Created by jlee512 on 13/06/2017.
 */

/**
 * This servlet processes the posted Google sign-in results when logging-in
 * This servlet was prepared using the methodology detailed by Google at:
 * https://developers.google.com/identity/sign-in/web/sign-in
 */

public class GoogleLoginServlet extends HttpServlet {


    /*Redirect if the servlet is directly accessed from the browser using a GET request*/
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*If there is an attempt to access a servlet directly, check login status and redirect to login page or content page as is appropriate (method defined below)*/
        LoginAttempt.loginStatusRedirection(request, response);
    }

    /*POST processing method*/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Establish a reference to the database*/
        MySQL DB = new MySQL();

        /*Construct HTTP transport and JacksonFactory instances*/
        HttpTransport transport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();

        /*Verify the google token*/
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
            Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            // Check user profile exists based on
            User user = UserDAO.getUserByEmail(DB, email);

            if (user.getUsername() == null) {

                response.sendRedirect("Login?loginStatus=invalidUsername");

            } else {

                int verificationStatus = 1; //Successful status code
                /*If user credentials are validated, create a session with a 5 minute maximum inactivity timeout*/
                HttpSession session = request.getSession(true);
                session.setMaxInactiveInterval(60 * 60 * 24);

            /*Attach user details and loginStatus to the HttpSession*/
                session.setAttribute("loginStatus", "active");
                session.setAttribute("userDetails",user);
                session.setAttribute("googleSignIn", true);
                response.sendRedirect("Content?username=" + user.getUsername());

            }
          /*If the google sign in is invalid, re-direct to the login page and display a descriptive message*/
        } else {

            response.sendRedirect("Login?loginStatus=invalidGoogleSignIn" );

        }
    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
