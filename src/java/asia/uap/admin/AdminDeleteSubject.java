/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.admin;

import asia.uap.model.TutorsIO;
import asia.uap.model.User;
import java.io.IOException;
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
 * @author Hawy
 */
@WebServlet(name = "AdminDeleteSubject", urlPatterns = {"/admin/do.deletesubject"})
public class AdminDeleteSubject extends HttpServlet {

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
        User user = (User) session.getAttribute("editing");
        TutorsIO dbOp = new TutorsIO(); 
        String subjectname = request.getParameter("subject_name");
        Boolean error = false;
        if (user == null || subjectname == null || subjectname.isEmpty()){
            error = true;
        }
        if (!error){
            try{
                dbOp.deleteUserSubject(subjectname, user.getId()); 
                user = dbOp.getUser(user.getId());
            }catch(SQLException ex){
                ex.printStackTrace();
                error = true;
            }
            if(!error&&(user.getSubject()==null||user.getSubject().isEmpty())){
                try{
                    dbOp.setTutorFalse(user.getId());
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
                
            }
        }
        String uri = "/WEB-INF/result.jsp";
        String successmsg = "Subject Successfully Deleted!";
        String err = "Oops! Something went wrong!";
        String href1 = "";
        String href1msg = "";
        if(!error){
            request.setAttribute("msg", successmsg);
                href1 = "do.profile?user="+user.getId();
                href1msg = "Back to Profile";
        }
        else{
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
