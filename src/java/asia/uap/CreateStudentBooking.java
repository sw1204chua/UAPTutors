/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap;

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
@WebServlet(name = "ConfrimBooking", urlPatterns = {"/profile/do.createbooking"})
public class CreateStudentBooking extends HttpServlet {

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
        String successmsg = "Booking Is Now Pending! Please Wait For The Tutor To Confirm";
        String err = "Could Not Be Booked!";
        String href1 = "";
        String href1msg = "";
        String listingid = request.getParameter("listingid");
        String date = request.getParameter("date");
        String random = request.getParameter("random");
        Boolean error = false;
        if (random == null || listingid == null || listingid.isEmpty() || date == null || date.isEmpty()) {
            error = true;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current");
        ArrayList<ListingBean> listings = new ArrayList();
        ArrayList<User> users = new ArrayList();
        TutorsIO dbOp = new TutorsIO();
        ListingBean listing = new ListingBean();
        try {
            listing = dbOp.getListing(Integer.parseInt(listingid));
        } catch (SQLException | NumberFormatException ex) {
            error = true;
            ex.printStackTrace();
        }
        if (listing == null || users == null || user == null) {
            error = true;
        }

        User tutor = new User();
        if (!error) {
            Boolean repeated = false;
            String sessionchecker = (String) session.getAttribute("checker");
            if (sessionchecker != null && sessionchecker.equals(random)) {
                error = true;
                err = "ERROR: Duplicate Booking";
                repeated = true;
            } else {
                session.setAttribute("checker", random);
            }
            tutor = listing.getTutor();
            if (!repeated) {
                try {
                    dbOp.addStudentListing(listing.getId(), date, user.getId());
                } catch (SQLException ex) {
                    error = true;
                    ex.printStackTrace();
                }
            }
        }
        if (!error) {
            request.setAttribute("msg", successmsg);
            href1 = "/UAPTutors/do.searchprofile?user=" + tutor.getId();
            href1msg = "Back To Tutor Profile";
        } else if (tutor != null) {
            request.setAttribute("msg", err);
            href1 = "/UAPTutors/do.searchprofile?user=" + tutor.getId();
            href1msg = "Back To Tutor Profile";
        } else {
            request.setAttribute("msg", err);
        }
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
