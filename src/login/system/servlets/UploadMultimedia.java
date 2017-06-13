package login.system.servlets;

import login.system.dao.Multimedia;
import login.system.dao.MultimediaDAO;
import login.system.dao.User;
import login.system.dao.UserDAO;
import login.system.db.MySQL;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ycow194 on 13/06/2017.
 */
public class UploadMultimedia extends HttpServlet {

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 10 * 1024 * 1024;
    private int maxUploadSize = 10 * 1024 * 1024;
    private File file;


    // Gets the file location the uploaded file is stored


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get to redirect");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MySQL DB = new MySQL();

        ServletContext servletContext = getServletContext();
        filePath = servletContext.getRealPath("Multimedia/");

        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("userDetails");

        isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            response.sendRedirect("ProfilePage?username=" + user.getUsername() + "&multimediaUpload=invalid");
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();

        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxFileSize);

        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("c:\\TEMP"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // maximum file size to be uploaded.
        upload.setSizeMax(maxUploadSize);

        try {

            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            // Initialize fileName and ArticleID
            String fileName = "";
            int ArticleID = -1;
            String extension = "";

            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();


                if (!fi.isFormField()) {

                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    System.out.println(fieldName);
                    fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();


                    /*~~~~~ Write the file and make sure the file name is unique ~~~~~*/

                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new File(filePath +
                                fileName.substring(fileName.lastIndexOf("\\")));

                        int counter = 0;
                        while (file.exists()) {
                            extension = FilenameUtils.getExtension(fileName);
                            fileName = FilenameUtils.removeExtension(fileName);
                            fileName = fileName.substring(0, (fileName.length())) + counter + "." + extension;
                            counter++;
                            file = new File(filePath +
                                    fileName.substring(fileName.lastIndexOf("\\")));
                        }
                    } else {
                        file = new File(filePath +
                                fileName.substring(fileName.lastIndexOf("\\") + 1));

                        int counter = 0;
                        while (file.exists()) {
                            extension = FilenameUtils.getExtension(fileName);
                            fileName = FilenameUtils.removeExtension(fileName);
                            fileName = fileName.substring(0, (fileName.length())) + counter + "." + extension;
                            counter++;
                            file = new File(filePath +
                                    fileName.substring(fileName.lastIndexOf("\\") + 1));
                        }
                    }


                    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

                    /* Write the file */
                    fi.write(file);

                    // Deal with DAO methods and youtube links
                } else {

                    /*Gettting the article ID*/
                    if (fi.getFieldName().equals("uploadArticleId")) {
                        ArticleID = Integer.parseInt(fi.getString());
                        System.out.println("Article ID is " + ArticleID);

                        //Placed this method here otherwise it doesnt seem to pick up the updated ArticleID and defaults to = 0
                    }


                    MultimediaDAO.addMultimediaToDB(DB, ArticleID, extension, "Multimedia/" + fileName, "multimedia_title");
                    // Placed here so the article ID is available


                    /*Getting Youtube link info*/
                    if (fi.getFieldName().equals("youtubeLink") && fi.getFieldName() != null) {
                        String conversionLink = fi.getString().replace("watch?v=", "embed/");



                        System.out.println(conversionLink);
                        String youtubeLink = "<iframe width='420' height='315' src='" + conversionLink + "'></iframe>";
                        System.out.println(youtubeLink);
                        MultimediaDAO.addMultimediaToDB(DB, 2, ".web", youtubeLink, "Youtube Video");
                        // DEAL WITH THE YOUTUBE IFRAME HERE CREATE NEW DAO METHOD
                    }
                }


//                List

            }

            // Update session? DAO method which puts multimedia in articles


            /* Send the user back to their profile */
            response.sendRedirect("ProfilePage");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
