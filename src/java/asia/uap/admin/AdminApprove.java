/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.admin;

import asia.uap.model.AdminUser;
import asia.uap.model.TutorsIO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * @author Chace
 */
@WebServlet(name = "AdminApprove", urlPatterns = {"/admin/do.adminapprove"})
public class AdminApprove extends HttpServlet {

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
        Boolean error = false;
        String err = "";
        HttpSession session = request.getSession();
        String[] reports = request.getParameterValues("clicked");
        String random = request.getParameter("random");
        if (random == null || reports == null || reports.length == 0) {
            err += "No Profiles Selected";
            error = true;
        }
        if (!error) {
            Boolean repeated = false;
            String sessionchecker = (String) session.getAttribute("checker");
            if (sessionchecker != null && sessionchecker.equals(random)) {
                error = true;
                err = "ERROR: Duplicate Request";
                repeated = true;
            } else {
                session.setAttribute("checker", random);
            }
            if (!repeated) {
                try {

                    AdminUser admin = (AdminUser) session.getAttribute("currentadmin");
                    int adminId = admin.getId();

                    // Use TutorsIO to store the pending actions
                    TutorsIO io = new TutorsIO();
                    io.insertReports(adminId, reports, "delete profile");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        String uri = "/WEB-INF/result.jsp";
        String successmsg = "Request has been sent for approval!";
        String href1 = "do.AdminTutorList";
        String href1msg = "Go back to list of profiles";
        if (!error) {
            request.setAttribute("msg", successmsg);
        } else {
            request.setAttribute("msg", err);
        }
        request.setAttribute("admin", "hello");
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
