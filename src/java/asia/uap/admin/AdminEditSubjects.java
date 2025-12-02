/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.admin;

import asia.uap.model.TutorsIO;
import asia.uap.model.User;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "AdminEditSubjects", urlPatterns = {"/admin/do.editsubject"})
public class AdminEditSubjects extends HttpServlet {

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
        TutorsIO dbOp = new TutorsIO();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("editing");
        String user_id = request.getParameter("id");
        String category_name = request.getParameter("category_name");  
        String subjectname = request.getParameter("subject_name");
        Boolean error = false;
        if (category_name == null || category_name.isEmpty() || subjectname == null || subjectname.isEmpty()){
            error = true;
        }
        String uri = "/WEB-INF/bioedit.jsp";
        String title = subjectname;
        String type = "subject";
        String href1 = "";
        String href1msg = "";
        request.setAttribute("admin", "hello");
        request.setAttribute("item", subjectname);       
        request.setAttribute("item2", category_name); 
        href1 = "do.subjectlist?user="+user.getId();
        href1msg = "Back to Subject List";
        request.setAttribute("error", error);
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
