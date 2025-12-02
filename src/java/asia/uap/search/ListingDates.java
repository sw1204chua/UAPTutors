/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.search;

import asia.uap.model.ListingBean;
import asia.uap.model.StudentListingBean;
import asia.uap.model.TutorsIO;
import asia.uap.model.User;
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
@WebServlet(name = "ListingDates", urlPatterns = {"/profile/do.listingdates"})
public class ListingDates extends HttpServlet {

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
        String listingid = request.getParameter("listingid");
        Boolean error = false;
        if (listingid == null) {
            error = true;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current");

        TutorsIO dbOp = new TutorsIO();
        ArrayList<StudentListingBean> studentListings = new ArrayList();
        ArrayList<ListingBean> listings = new ArrayList();
        ArrayList<User> users = new ArrayList();
        ListingBean listing = new ListingBean();
        try {
            listing = dbOp.getListing(Integer.parseInt(listingid));
            studentListings = dbOp.getStudentListings(Integer.parseInt(listingid));
            users = dbOp.getUsers();
        } catch (SQLException | NumberFormatException ex) {
            error = true;
            ex.printStackTrace();
        }
        if (listing == null || users == null || user == null) {
            error = true;
        }
        ArrayList<ListingBean> recurringListings = new ArrayList();
        User tutor = new User();
        Boolean listing_found = false;
        if (!error) {
            tutor = listing.getTutor();

            if (listing.getRecur().toLowerCase().equals("once")) {
                Boolean add = true;
                for (StudentListingBean l : studentListings) {
                    if (l.getBooking_date().equals(listing.getDate())) {
                        add = false;
                        break;
                    }
                }
                if (add) {

                    listing_found = true;
                    recurringListings.add(listing);
                }
            }
            if (listing.getRecur().toLowerCase().equals("weekly")) {
                // Add the original listing first
                Boolean add = true;
                for (StudentListingBean l : studentListings) {

                    if (l.getBooking_date().equals(listing.getDate())) {
                        add = false;
                    }
                }
                if (add) {

                    listing_found = true;
                    recurringListings.add(listing);
                }
                LocalDate oldDate = LocalDate.parse(listing.getDate());
                // Create weekly recurrences
                for (int i = 0; i < 3; i++) {
                    add = true;
                    LocalDate newRecurrenceDate = oldDate.plusWeeks(1); // Add one week

                    // Create a new ListingBean for each recurrence
                    ListingBean recurringListing = new ListingBean();
                    recurringListing.setId(listing.getId());
                    recurringListing.setDate(newRecurrenceDate.toString());
                    recurringListing.setSubject(listing.getSubject());
                    recurringListing.setPrice(listing.getPrice());
                    recurringListing.setMop(listing.getMop());
                    recurringListing.setNumber(listing.getNumber());
                    recurringListing.setTutor(listing.getTutor());
                    recurringListing.setIsPending(listing.getIsPending());
                    recurringListing.setIsConfirmed(listing.getIsConfirmed());
                    recurringListing.setIsPaid(listing.getIsPaid());
                    recurringListing.setIsComplete(listing.getIsComplete());
                    recurringListing.setHasComment(listing.getHasComment());
                    // Add the new recurrence to the list
                    for (StudentListingBean l : studentListings) {
                        if (l.getBooking_date().equals(recurringListing.getDate())) {
                            add = false;
                        }
                    }
                    if (add) {
                        listing_found = true;
                        recurringListings.add(recurringListing);
                    }
                    oldDate = newRecurrenceDate;
                }
            }
            if (listing.getRecur().toLowerCase().equals("monthly")) {
                // Add the original listing first
                Boolean add = true;
                for (StudentListingBean l : studentListings) {

                    if (l.getBooking_date().equals(listing.getDate())) {
                        add = false;
                    }
                }
                if (add) {
                    listing_found = true;
                    recurringListings.add(listing);
                }
                LocalDate oldDate = LocalDate.parse(listing.getDate());
                // Create weekly recurrences
                for (int i = 0; i < 3; i++) {
                    add = true;
                    LocalDate newRecurrenceDate = oldDate.plusWeeks(4); // Add one month
                    // Create a new ListingBean for each recurrence
                    ListingBean recurringListing = new ListingBean();
                    recurringListing.setId(listing.getId());
                    recurringListing.setDate(newRecurrenceDate.toString());
                    recurringListing.setSubject(listing.getSubject());
                    recurringListing.setPrice(listing.getPrice());
                    recurringListing.setMop(listing.getMop());
                    recurringListing.setNumber(listing.getNumber());
                    recurringListing.setTutor(listing.getTutor());
                    recurringListing.setIsPending(listing.getIsPending());
                    recurringListing.setIsConfirmed(listing.getIsConfirmed());
                    recurringListing.setIsPaid(listing.getIsPaid());
                    recurringListing.setIsComplete(listing.getIsComplete());
                    recurringListing.setHasComment(listing.getHasComment());
                    // Add the new recurrence to the list
                    for (StudentListingBean l : studentListings) {

                        if (l.getBooking_date().equals(recurringListing.getDate())) {
                            add = false;
                        }
                    }
                    if (add) {
                        listing_found = true;
                        recurringListings.add(recurringListing);
                    }
                    oldDate = newRecurrenceDate;
                }
            }
        }
        String uri = "/WEB-INF/listingdates.jsp";
        if (listing.getTutor() != null) {

            request.setAttribute("tutor", listing.getTutor());
        } else {
            log("tutor null");
        }

        request.setAttribute("recurringListings", recurringListings);
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
