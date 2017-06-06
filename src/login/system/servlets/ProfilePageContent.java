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


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

         /*Create link to the database*/
        MySQL DB = new MySQL();

        /*When used attempts to post new password*/
        /*Access user detail stored within the session*/

        HttpSession session = request.getSession(true);

        User user = (User) session.getAttribute("userDetails");

        String username = request.getParameter("username");
        String fullname = request.getParameter("fullname");
        String occupation = request.getParameter("occupation");
        String location = request.getParameter("location");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String aboutme = request.getParameter("aboutme");

//        System.out.println(location);
        UserDAO.updateUserDetails(DB, username, email,phone,occupation,location,aboutme,fullname,fullname);

        session.setMaxInactiveInterval(60 * 5);
        session.setAttribute("userDetails",user);

        response.sendRedirect("ProfilePage?username=" + user.getUsername());

    }

}
