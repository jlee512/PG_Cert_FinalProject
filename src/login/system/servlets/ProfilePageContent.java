package login.system.servlets;

import login.system.dao.Article;
import login.system.dao.ArticleDAO;
import login.system.dao.User;
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
    }

}
