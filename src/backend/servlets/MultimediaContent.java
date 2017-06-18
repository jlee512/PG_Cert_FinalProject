package backend.servlets;

import backend.dao.Multimedia;
import backend.dao.MultimediaDAO;
import backend.dao.User;
import backend.db.MySQL;

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
 * Created by Tammy on 12/06/2017.
 */

/**
 * This servlet accesses all muiltimedia to prepare the Full Multimedia Gallery page.
 */

public class MultimediaContent extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      /*Create a link to the database*/
        MySQL DB = new MySQL();
        HttpSession session = request.getSession(true);


        /*Get the number of multimedia files requested and the author_id*/
        int first_multimedia_file = Integer.parseInt(request.getParameter("from"));
        int multimedia_count = Integer.parseInt(request.getParameter("count"));
        int author_id = ((User) (session.getAttribute("userDetails"))).getUser_id();

        /*Access a given selection of multimedia*/
        List<Multimedia> multimedia = MultimediaDAO.getFirstNMultimediaByArticleDate(DB, first_multimedia_file, multimedia_count);

        /*Return a JSON object with the article information included*/
        response.setContentType("application/json");
        JSONArray multimediaDetails = new JSONArray();

        /*Construct a JSON object representing the multimedia list*/
        constructMultimediaPreviewJSON(multimedia, multimediaDetails);

        multimediaDetails.toJSONString();

        response.getWriter().write(multimediaDetails.toString());
        System.out.println(multimediaDetails);

    }

    /*This method is used to construct a JSON array representing a number of multimedia given a list of Multimedia object instances*/
    public static void constructMultimediaPreviewJSON(List<Multimedia> multimedia, JSONArray multimediaDetails) {
        for (int i = 0; i < multimedia.size(); i++) {
            JSONObject singleMultimedia = new JSONObject();
            singleMultimedia.put("username", multimedia.get(i).getUsername());
            singleMultimedia.put("multimedia_id", multimedia.get(i).getMultimedia_id());
            singleMultimedia.put("article_id", multimedia.get(i).getArticle_id());
            singleMultimedia.put("file_type", multimedia.get(i).getFile_type());
            singleMultimedia.put("file_path", multimedia.get(i).getFile_path());
            singleMultimedia.put("multimedia_title", multimedia.get(i).getMultimedia_title());


            multimediaDetails.add(i, singleMultimedia);
        }
    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
