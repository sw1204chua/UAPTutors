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
import java.time.LocalDate;
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
@WebServlet(name = "CompleteBooking", urlPatterns = {"/profile/do.completebooking"})
public class CompleteBooking extends HttpServlet {

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
        String uri = "/WEB-INF/result.jsp";
        String successmsg = "Your session is now marked as Complete! Thank you for using UAP Tutors!";
        String err = "Could Not Be Marked As Complete!";
        String href1 = "";
        String href1msg = "";
        String booked_id = request.getParameter("booked_id");
        Boolean error = false;
        if (booked_id == null || booked_id.isEmpty()){
            error = true;
        }
        HttpSession session = request.getSession();       
        User user = (User) session.getAttribute("current");
        ArrayList<ListingBean> listings = new ArrayList();
        ArrayList<User> users = new ArrayList();
        StudentListingBean listing = new StudentListingBean();
        ListingBean tutorlisting = new ListingBean();
        TutorsIO dbOp = new TutorsIO();
        try{
            listing = dbOp.getStudentListing(Integer.parseInt(booked_id));
            users = dbOp.getUsers();
            if(listing.getBooking_date()!=null){
                tutorlisting = dbOp.getListing(listing.getListing_id());
            }
        }catch(SQLException | NumberFormatException ex){
            error = true;
            ex.printStackTrace();
        }
        if (listing == null || users == null || user == null){        
            error = true;
        }
        
        User tutor = new User();
        if (!error){
            tutor = listing.getTutor();
            try{
                dbOp.setListingComplete(listing);
                if(tutorlisting.getRecur().equals("Once")){
                    dbOp.deleteListing(listing.getListing_id());
                }
                /*else if(tutorlisting.getRecur().equals("Weekly")){
                    LocalDate date = LocalDate.parse(listing.getBooking_date());
                    date.plusWeeks(3);
                    if(listing.getDate().equals(date.toString())){
                        dbOp.deleteListing(listing.getListing_id());
                    }
                }
                else if(tutorlisting.getRecur().equals("Monthly")){
                    LocalDate date = LocalDate.parse(listing.getBooking_date());
                    date.plusWeeks(12);
                    if(listing.getDate().equals(date.toString())){
                        dbOp.deleteListing(listing.getListing_id());
                    }
                }*/
            }catch(SQLException ex){
                ex.printStackTrace();
                error = true;
            }    
        }
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
