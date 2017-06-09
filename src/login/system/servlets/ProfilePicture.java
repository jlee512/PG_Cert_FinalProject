package login.system.servlets;

import login.system.dao.User;
import login.system.dao.UserDAO;
import login.system.db.MySQL;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ycow194 on 8/06/2017.
 */
public class ProfilePicture extends HttpServlet {

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 10 * 1024 * 1024;
    private int maxUploadSize = 10 * 1024 * 1024;
    private File file;


    // Gets the file location the uploaded file is stored


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GETME");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MySQL DB = new MySQL();

//       Store the file save file path
        ServletContext servletContext = getServletContext();
        filePath = servletContext.getRealPath("Multimedia/");

        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("userDetails");






        /*Obtain current user profile picture filepath*/

        /*Extract profile picture filepath number*/

        /*Increment profile picture filepath number by 1 and create save file string*/


        isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            response.sendRedirect("ProfilePage?username=" + user.getUsername() + "&photoUpload=invalid");
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

//         Parse the request to get file items.
            List fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();


            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();


                if (!fi.isFormField()) {

                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();


                    /*~~~~~ Write the file and make sure the file name is unique ~~~~~*/

                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new File(filePath +
                                fileName.substring(fileName.lastIndexOf("\\")));

                        int counter = 0;
                        while (file.exists()) {

                            String extension = FilenameUtils.getExtension(fileName);
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

                            String extension = FilenameUtils.getExtension(fileName);
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
                    /* Update the database and session */
                    UserDAO.updateProfilePicture(DB, "Multimedia/" + fileName, user.getUsername());
                    user.setProfile_picture("Multimedia/" + fileName);

                }
            }
            /* Send the user back to their profile */
            response.sendRedirect("ProfilePage");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


