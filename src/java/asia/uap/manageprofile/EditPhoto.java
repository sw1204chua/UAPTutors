/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.manageprofile;

import asia.uap.model.TutorsIO;
import asia.uap.model.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Hawy
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5MB max file size
@WebServlet(name = "EditPhoto", urlPatterns = {"/profile/do.editphoto"})
public class EditPhoto extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        TutorsIO dbOp = new TutorsIO();
        String userId = request.getParameter("userid");
        Boolean error = false;

        String contentType = request.getContentType();
        if (contentType == null || !contentType.toLowerCase().startsWith("multipart/form-data")) {
            log("Invalid content type");
            error = true;
        }
        Part filePart = null;

        if (!error) {
            try {
                filePart = request.getPart("profileImage");
            } catch (Exception ex) {
                error = true; 
            }
        }

        if (userId == null || userId.isEmpty()) {
            log("Missing stuff");
            error = true;
        }
        if (filePart == null || filePart.getSize() == 0) {
            log("File not found");
        }
        User user = (User) session.getAttribute("current");
        if (user == null) {
            error = true;
        }
        if (!error) {
            InputStream fileContent = filePart.getInputStream();
            String fileName = filePart.getSubmittedFileName();
            log(fileName);

            try {
                dbOp.updateProfileImage(user.getId(), fileContent);
                session.setAttribute("current", dbOp.getUser(user.getId()));
            } catch (SQLException ex) {
                ex.printStackTrace();
                error = true;
            }
        }
        String directory = dbOp.getDirectory();
        log("DIRECTIORY" + directory);
        String fileName = "";

        if (!error) {

            fileName = user.getId() + ".jpg";
            File file = new File(directory, fileName);
            log("DIRECTIORY" + file);
            try {
                if (file.exists()) {
                    file.delete();
                }
                Blob blob = dbOp.getImageBlob(user.getId());
                if (blob != null) {
                    dbOp.saveImageFromBlob(blob, file);
                    log("file saved");
                }

            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }
        }
        String uri = "/WEB-INF/result.jsp";
        String successmsg = "Your Profile Picture has been updated!";
        String err = "Oops! Something went wrong!";
        String href1 = "";
        String href1msg = "";
        if (!error) {
            request.setAttribute("msg", successmsg);
        } else {
            request.setAttribute("msg", err);
        }
        href1 = "do.profile";
        href1msg = "Back to Profile";
        request.setAttribute("href1", href1);
        request.setAttribute("href1msg", href1msg);
        RequestDispatcher rd = request.getRequestDispatcher(uri);
        rd.forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
