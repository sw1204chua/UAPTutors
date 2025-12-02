/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.manageprofile;

import asia.uap.model.Comment;
import asia.uap.model.StudentListingBean;
import asia.uap.model.TutorsIO;
import asia.uap.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@WebServlet(name = "SaveComment", urlPatterns = {"/profile/do.savecomment"})
public class SaveComment extends HttpServlet {

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
        TutorsIO dbOp = new TutorsIO();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current");
        Comment comments = new Comment();
        StudentListingBean listing = new StudentListingBean();
        String booked_id = request.getParameter("booked_id");
        String rating = request.getParameter("rating");
        String comment_content = request.getParameter("comment_content");
        String random = request.getParameter("random");
        String err = "Oops! Something went wrong!";
        int currentlistingid = 0;
        Integer num_rating = 0;
        Boolean error = false;

        if (random == null || user == null || rating == null || rating.isEmpty() || comment_content == null || comment_content.isEmpty() || booked_id == null || booked_id.isEmpty()) {
            error = true;
        } else {
            try {
                int booked_id_int = Integer.parseInt(booked_id);
                listing = dbOp.getStudentListing(booked_id_int);
                num_rating = Integer.parseInt(rating);
            } catch (NumberFormatException | SQLException ex) {
                error = true;
                ex.printStackTrace();
            }
        }
        if (listing.getBooking_date() == null || listing.getBooking_date().isEmpty()) {
            error = true;
        }

        if (!error) {
            Boolean repeated = false;
            String sessionchecker = (String) session.getAttribute("checker");
            if (sessionchecker != null && sessionchecker.equals(random)) {
                error = true;
                err ="Already Commented";
                repeated = true;
            } else {
                session.setAttribute("checker", random);
            }
            if (!repeated) {
                try {
                    dbOp.addComment(listing, num_rating, comment_content);
                    session.setAttribute("current", user);
                    dbOp.setHasComment(listing);
                } catch (SQLException ex) {
                    error = true;
                    ex.printStackTrace();
                }
            }
        }
        String uri = "/WEB-INF/result.jsp";
        String successmsg = "Comment Posted!";
        
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
