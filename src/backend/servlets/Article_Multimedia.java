package backend.servlets;

import backend.dao.Multimedia;
import backend.dao.MultimediaDAO;
import backend.db.MySQL;
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
 * The Article_Multimedia Class returns a JSON for a specific article_id representing the multimedia associated with that article
 */
public class Article_Multimedia extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Create a link to the database*/
        MySQL DB = new MySQL();
        HttpSession session = request.getSession(true);


        /*Get the article_id*/
        int article_id = Integer.parseInt(request.getParameter("article_id"));

        /*Access the multimedia for a given article using the MultimediaDAO*/
        List<Multimedia> multimedia = MultimediaDAO.getAllMultimediaForArticle(DB, article_id);

        /*Return a JSON object with the article information included (to be accessed and read via an AJAX call*/
        response.setContentType("application/json");
        JSONArray multimediaDetails = new JSONArray();

        MultimediaContent.constructMultimediaPreviewJSON(multimedia, multimediaDetails);

        multimediaDetails.toJSONString();

        response.getWriter().write(multimediaDetails.toString());

    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
