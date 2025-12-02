/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.admin;

import asia.uap.model.AdminUser;
import asia.uap.model.SubjectBean;
import asia.uap.model.TutorsIO;
import asia.uap.model.User;
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
@WebServlet(name = "AdminSubjectList", urlPatterns = {"/admin/do.subjectlist"})
public class AdminSubjectList extends HttpServlet {

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
        String user_id = request.getParameter("user");

        Boolean error = false;
        if (user_id == null || user_id.isEmpty()) {
            error = true;
        }
        AdminUser admin = (AdminUser) session.getAttribute("currentadmin");
        User user = null;
        int user_id_int = 0;
        if (!error) {
            try {
                user_id_int = Integer.parseInt(user_id);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                error = true;
            }
        }
        if (!error) {
            try {
                user = dbOp.getUser(user_id_int);
            } catch (SQLException ex) {
                ex.printStackTrace();
                error = true;
            }
        }
        if (user == null) {
            error = true;
        }
        ArrayList<SubjectBean> usersubjects = new ArrayList();
        if (!error) {
            usersubjects = user.getSubject();
        }
        String uri = "/WEB-INF/biolist.jsp";
        String title = "Subjects of Expertise";
        String type = "subject";
        String href1 = "";
        String href1msg = "";
        request.setAttribute("biolist", usersubjects);
        if (!error) {
            href1 = "do.profile?user=" + user.getId();
            href1msg = "Back to Profile";
        }
        request.setAttribute("error", error);
        request.setAttribute("admin", "hello");
        request.setAttribute("title", title);
        request.setAttribute("type", type);
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
