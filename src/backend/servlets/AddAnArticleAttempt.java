package backend.servlets;

import backend.dao.ArticleDAO;
import backend.db.MySQL;
import backend.dao.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jlee512 on 8/06/2017.
 */

/**
 * Servlet for adding articles to the database
 */

public class AddAnArticleAttempt extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*If there is an attempt to access a servlet directly, check login status and redirect to login page or content page as is appropriate (method defined at the bottom of this servlet)*/

        LoginAttempt.loginStatusRedirection(request, response);

    }


    /*doPost method captures form submission from user profile page and stores in the database if a valid article is submitted*/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Create a link to the database*/
        MySQL DB = new MySQL();
        HttpSession session = request.getSession(true);

         /*If user is not logged in, redirect them to login page.*/
        if (session.getAttribute("loginStatus") != "active") {

            response.sendRedirect("Login");

        } else {
            /*Check if session has timed out and if it has, redirect to the login page*/
            if (!LoginAttempt.sessionExpirationRedirection(request, response)) {

                /*If the user is still logged in, pull the relevant user details from the current session as well as the posted article title, body and date/timestamp addition to the database*/
                User user = (User) session.getAttribute("userDetails");
                int author_id = user.getUser_id();
                String username = user.getUsername();
                Timestamp timestamp_for_article = new Timestamp(System.currentTimeMillis());

                String article_title_input = request.getParameter("article_title_input");
                String article_body_input = request.getParameter("article_body_input");
                String calendar_date_input = request.getParameter("calendar_input");

                if (inputContainsHTML(article_title_input) || inputContainsHTML(article_body_input)) {
                    response.sendRedirect("ProfilePage?username=" + username + "&articleAdditionStatus=invalid" + "&articleadded=invalid");
                    return;
                }

                /*Check whether a calendar input has been used to supply a publishing date*/
                if (calendar_date_input != null && calendar_date_input.length() > 0) {
                    /*Provide the calendar format so that the string output can be processed by the server*/
                    try {

                        DateFormat formatter;
                        formatter = new SimpleDateFormat("MM/dd/yyyy");

                        /*Parse the date string from the calendar input (jQuery UI)*/
                        Date parsedDate = formatter.parse(calendar_date_input);
                        timestamp_for_article = new Timestamp(parsedDate.getTime());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


                /*Call the ArticleDAO method to add an article to the database and */
                int articleAdditionStatus = ArticleDAO.addArticleToDB(DB, author_id, article_title_input, timestamp_for_article, article_body_input);

                switch (articleAdditionStatus) {
                    case 1:
                    /*If successful article addition, reload the user's profile page and provide corresponding feedback*/
                        response.sendRedirect("ProfilePage?username=" + username + "&articleadded=successful");
                        break;

                    case 2:
                /*If the article is non-unique (not expected to ever occur) return the user to the profile page and display a descriptive message*/
                        response.sendRedirect("ProfilePage?username=" + username + "&articleAdditionStatus=exists" + "&articleadded=duplicate");
                        break;

                    case 3:
                /*If an invalid article is input, return the user to the profile page and display a descriptive message*/
                        response.sendRedirect("ProfilePage?username=" + username + "&articleAdditionStatus=invalid" + "&articleadded=invalid");
                        break;

                    case 4:
//                        No connection to the database - display a corresponding message to the user.
                        response.sendRedirect("ProfilePage?username=" + username + "&articleAdditionStatus=dbConn" + "&articleadded=db");
                        break;

                }

            }
        }
    }


    /**
     * Method developed to check whether input fields contain HTML tags.
     *
     * Used for screening user input, server-side (added after 16 June 2017 presentation based on user feedback.
     */
    public static boolean inputContainsHTML(String input) {

        Pattern pattern = Pattern.compile("(.*<+[^>]+>.*)|(.*<+[^>]+>.*\\s)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {

            return true;

        } else {

            return false;

        }

    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
