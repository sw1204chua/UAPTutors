/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap;

import asia.uap.model.StudentListingBean;
import asia.uap.model.User;
import asia.uap.model.ListingBean;
import asia.uap.model.TutorsIO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Hawy
 */
@WebServlet(name = "ApproveBooking", urlPatterns = {"/profile/do.approvebooking"})
public class ApproveBooking extends HttpServlet {

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
        
        String userid = request.getParameter("user");
        String booked_id = request.getParameter("booked_id");
        Boolean error = false;
        if (userid == null || booked_id == null){
            error = true;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current");
        TutorsIO dbOp = new TutorsIO();
        ArrayList<ListingBean> listings = new ArrayList();
        ArrayList<User> users = new ArrayList();
        StudentListingBean listing = new StudentListingBean();
        try{
            listing = dbOp.getStudentListing(Integer.parseInt(booked_id));
            users = dbOp.getUsers();
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            error = true;
        }
        if (listing == null || users == null || user == null){
            error = true;
        }  
        User tutor = new User();
        if (!error){
            tutor = listing.getTutor();
            try{
                dbOp.setListingConfirmed(listing);
            }catch(SQLException ex){
                ex.printStackTrace();
                error = true;
            }
        }
        String uri = "/WEB-INF/result.jsp";
        String successmsg = "Booking Approved!";
        String err = "Booking Could Not Be Approved!";
        String href1 = "";
        String href1msg = "";
        if(!error){
            request.setAttribute("msg", successmsg);        
        }
        else{
            request.setAttribute("msg", err);           
        }
        href1 = "do.profile";
        href1msg = "Back To Your Profile";
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
