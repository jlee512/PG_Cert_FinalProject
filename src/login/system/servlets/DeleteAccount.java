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
 * Created by ycow194 on 7/06/2017.
 */
public class DeleteAccount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Create link to the database*/
        MySQL DB = new MySQL();


        /*Access username stored within the session*/
        HttpSession session = request.getSession(true);

        if (session.getAttribute("loginStatus") != null) {
            UserDAO.deleteUserAccount(DB, ((User) session.getAttribute("userDetails")).getUsername());
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
