/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap;

import asia.uap.model.SubjectBean;
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
@WebServlet(name = "CreateListing", urlPatterns = {"/profile/do.createlisting"})
public class CreateListing extends HttpServlet {

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
        String successmsg = "Listing Created!";
        String err = "Listing Could Not Be Created!";
        String href1 = "";
        String href1msg = "";
        String date = (String) request.getParameter("date");
        String recur = request.getParameter("repeat");
        String subject = (String) request.getParameter("subject");
        String string_price = (String) request.getParameter("price");
        String tutor = (String) request.getParameter("tutor");
        String paymentmethod = request.getParameter("paymentmethod");
        String number = request.getParameter("number");
        String tutorId = request.getParameter("tutorId");
        String random = request.getParameter("random");

        int tutorId_int = 0;
        Integer price = 0;
        Boolean error = false;
        Boolean validPrice = true;
        Boolean fieldError = false;
        Boolean sqlError = false;
        if (date == null || subject == null || string_price == null || tutor == null || tutorId == null || paymentmethod == null || number == null || recur == null) {
            error = true;
            fieldError = true;
        }
        try {
            tutorId_int = Integer.parseInt(tutorId);
        } catch (NumberFormatException ex) {
            error = true;
            fieldError = true;
            ex.printStackTrace();
        }
        try {
            price = Integer.parseInt(string_price);
        } catch (NumberFormatException ex) {
            error = true;
            validPrice = false;
            ex.printStackTrace();
        }

        User user = new User();
        User tutorprofile = new User();
        String firstname = "";
        String lastname = "";
        String email = "";
        String about = "";
        ArrayList<SubjectBean> subjects = new ArrayList();
        ArrayList<String> skills = new ArrayList();
        ArrayList<String> awards = new ArrayList();
        ArrayList<String> languages = new ArrayList();
        Boolean is_tutor = false;
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("current");
        ArrayList<User> users = new ArrayList();
        TutorsIO dbOp = new TutorsIO();
        try {
            users = dbOp.getUsers();
        } catch (SQLException | NullPointerException ex) {
            error = true;
            sqlError = true;
            ex.printStackTrace();
        }
        if (users == null) {
            users = new ArrayList();
        }
        try {
            tutorprofile = dbOp.getUser(tutorId_int);
        } catch (SQLException | NullPointerException ex) {
            error = true;
            sqlError = true;
            ex.printStackTrace();
        }
        if (!error) {
            ListingBean listing = new ListingBean(date, subject, price, tutorprofile, paymentmethod, number, recur);
            Boolean repeated = false;
            String sessionchecker = (String) session.getAttribute("checker");            
            if (sessionchecker != null && sessionchecker.equals(random)) {
                error = true;
                err = "ERROR: Duplicate Listing";
                repeated = true;
            } else {
                session.setAttribute("checker", random);
            }
            if (!repeated) {
                try {
                    dbOp.addListing(listing);
                } catch (SQLException | NullPointerException ex) {
                    error = true;
                    ex.printStackTrace();
                }
            }
        }

        if (!error) {
            request.setAttribute("msg", successmsg);

        } else {
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
