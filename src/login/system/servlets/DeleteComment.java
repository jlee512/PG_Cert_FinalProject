package login.system.servlets;

import login.system.db.MySQL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cbla080 on 7/06/2017.
 */
public class DeleteComment extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        MySQL DB = new MySQL();
        int commentID = Integer.parseInt(request.getParameter("commentID"));
        int articleID = Integer.parseInt(request.getParameter("articleID"));
        
    }
}
