package backend.servlets;

import backend.dao.Multimedia;
import backend.dao.MultimediaDAO;
import backend.dao.User;
import backend.db.MySQL;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ycow194 on 13/06/2017.
 */

/**
 * This servlet has been developed to process multimedia uploads (file and YouTube video links)
 */

public class UploadMultimedia extends HttpServlet {

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 30 * 1024 * 1024;
    private int maxUploadSize = 30 * 1024 * 1024;
    private File file;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MySQL DB = new MySQL();

        ServletContext servletContext = getServletContext();
        filePath = servletContext.getRealPath("Multimedia/");

        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("userDetails");

        isMultipart = ServletFileUpload.isMultipartContent(request);

        /*Confirm the upload is multi-part type*/
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
            int articleID = -1;


            List<Multimedia> multimedia_to_upload = new ArrayList<>();
            Multimedia multimedia = null;

            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();

                // Creates a new multimedia object for the file upload and youtube link fields
                //If valid Multimedia objects are created (i.e. the fields are not null or blank) add the multimedia to a list and process the list
                if (fi.getFieldName().equals("photoOrVideo") || fi.getFieldName().equals("youtubeLink")) {

                    multimedia = new Multimedia();

                }

                // If the input is not a form field, it is the file upload.
                // Carry out file upload process
                if (!fi.isFormField() && !(fi.getName() == null || fi.getName().isEmpty() || fi.getSize() == 0)) {

                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();
                    String filePathforMultimediaObject = "";

                    // If the file is too large, redirect back and send an error in the URL
                    if (fi.getSize() >= maxFileSize) {

                        response.sendRedirect("ProfilePage?multimediaAdditionStatus=file_too_large");

                    }

                    /*~~~~~ Write the file and generate a unique filename as required ~~~~~*/

                    if (fileName.lastIndexOf("\\") >= 0) {
                        filePathforMultimediaObject = filePath + fileName.substring(fileName.lastIndexOf("\\"));
                        file = new File(filePathforMultimediaObject);

                        int counter = 0;

                        // These methods make sure all files uploaded have a unique name
                        while (file.exists()) {

                            String extension = FilenameUtils.getExtension(fileName);

                            fileName = FilenameUtils.removeExtension(fileName);
                            fileName = fileName.replaceAll("[0-9]+$", "");
                            fileName = fileName.substring(0, (fileName.length())) + counter + "." + extension;
                            counter++;
                            filePathforMultimediaObject = filePath +
                                    fileName.substring(fileName.lastIndexOf("\\"));
                            file = new File(filePathforMultimediaObject);
                        }

                    } else {
                        filePathforMultimediaObject = filePath +
                                fileName.substring(fileName.lastIndexOf("\\") + 1);
                        file = new File(filePathforMultimediaObject);

                        int counter = 0;
                        while (file.exists()) {
                            String extension = FilenameUtils.getExtension(fileName);
                            fileName = FilenameUtils.removeExtension(fileName);
                            fileName = fileName.replaceAll("[0-9]+$", "");

                            fileName = fileName.substring(0, (fileName.length())) + counter + "." + extension;
                            counter++;
                            filePathforMultimediaObject = filePath + fileName.substring(fileName.lastIndexOf("\\") + 1);
                            file = new File(filePathforMultimediaObject);
                        }
                    }
                    // Gets the file extension
                    String extension = FilenameUtils.getExtension(fileName).toLowerCase();

                    // Checks the file extension to make sure it's a valid file type
                    if (extension.equals("png") || extension.equals("jpeg") || extension.equals("jpg") || extension.equals("mp3") || extension.equals("mp4") || extension.equals("gif") || extension.equals("mpeg-4")) {

                        // Sets the file multimedia object variables and adds to multimedia arraylist
                        multimedia.setFile_type("." + extension);
                        multimedia.setFile_path("Multimedia/" + fileName);
                        multimedia.setMultimedia_title(fileName);
                        multimedia_to_upload.add(multimedia);
                        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

                    /* Write the file */
                        fi.write(file);

                    }


                /* Else if the upload is a form field it is either the article_id (associated with the subnit button)
                * or the YouTube link*/
                } else {


                    /*Gettting the article ID*/
                    if (fi.getFieldName().equals("uploadArticleId")) {

                        articleID = Integer.parseInt(fi.getString());

                    }


                    // YouTube link - convert watch link into the embed link and insert into Iframe tags and store within the database as the 'filepath'
                    if (fi.getFieldName().equals("youtubeLink") && fi.getString().length() != 0 && !AddAnArticleAttempt.inputContainsHTML(fi.getString())) {

                        // Convert youtube link to be "embed"
                        String conversionLink = fi.getString().replace("watch?v=", "embed/");

                        // Make the entire iframe with link the filepath variable in the DAO method
                        String youtubeLink = "<iframe allowfullscreen='allowfullscreen' src='" + conversionLink + "'></iframe>";

                        // Sets the youtube variables and adds to multimedia arraylist
                        multimedia.setFile_type(".web");
                        multimedia.setFile_path(youtubeLink);
                        multimedia.setMultimedia_title("youtube_video");
                        multimedia_to_upload.add(multimedia);

                    }
                }
            }

            int upload_status = -1;

            // Goes through the valid Multimedia objects added to the multimedia list and sets the article id variable and adds to database
            for (Multimedia multimedia1 : multimedia_to_upload) {

                multimedia1.setArticle_id(articleID);
                upload_status = MultimediaDAO.addMultimediaToDB(DB, multimedia1.getArticle_id(), multimedia1.getFile_type(), multimedia1.getFile_path(), multimedia1.getMultimedia_title());

            }

            int num_uploads = multimedia_to_upload.size();

            if (num_uploads > 0 && upload_status == 1) {
                /*  Send the user back to the profile page and display a message with the upload status*/
                response.sendRedirect("ProfilePage?multimediaAdditionStatus=success&num_uploads=" + num_uploads);

            } else {

                /* Send the user back to their profile */
                response.sendRedirect("ProfilePage?multimediaAdditionStatus=invalid_upload");

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
