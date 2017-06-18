package backend.servlets;

import backend.dao.Multimedia;
import backend.dao.MultimediaDAO;
import backend.db.MySQL;
import backend.dao.User;
import org.json.simple.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Julian on 14/06/2017.
 */

/**
 * This servlet accesses all muiltimedia for a specific user to prepare the User Multimedia Gallery page.
 */

public class User_Multimedia extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Create a link to the database*/
        MySQL DB = new MySQL();
        HttpSession session = request.getSession(true);


        /*Get the number of articles requested and the author_id*/
        int first_multimedia_file = Integer.parseInt(request.getParameter("from"));
        int multimedia_count = Integer.parseInt(request.getParameter("count"));
        int poster_id = ((User) (session.getAttribute("userDetails"))).getUser_id();

        List<Multimedia> multimedia = MultimediaDAO.getFirstNMultimediaForPosterByDate(DB, first_multimedia_file, multimedia_count, poster_id);

        /*Return a JSON object with the article information included*/
        response.setContentType("application/json");
        JSONArray multimediaDetails = new JSONArray();

        MultimediaContent.constructMultimediaPreviewJSON(multimedia, multimediaDetails);

        // Convert to a JSON string
        multimediaDetails.toJSONString();

        response.getWriter().write(multimediaDetails.toString());
        System.out.println(multimediaDetails);

    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
