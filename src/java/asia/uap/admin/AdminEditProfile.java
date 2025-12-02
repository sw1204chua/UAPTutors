/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.admin;

import asia.uap.model.AdminUser;
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
@WebServlet(name = "AdminEditProfile", urlPatterns = {"/admin/do.editprofile"})
public class AdminEditProfile extends HttpServlet {

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
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String is_tutor = request.getParameter("is_tutor");
        String user_id = request.getParameter("id");
        Boolean is_tutor_bool = false;
        Boolean error = false;
        String err = "";
        HttpSession session = request.getSession();       
        
        if (user_id==null || user_id.isEmpty()||firstname == null || firstname.isEmpty() || lastname == null || lastname.isEmpty() || email == null || email.isEmpty() ||is_tutor == null || is_tutor.isEmpty()){
            error = true;
            err += "Empty Fields<br>";
        }
        if(is_tutor!=null&&(is_tutor.equals("true")||is_tutor.equals("false"))){
            is_tutor_bool = Boolean.valueOf(is_tutor);
        }
        else{
            err += "Invalid Tutor Value";
            error = true;
        }
        AdminUser admin = (AdminUser) session.getAttribute("currentadmin");
        User user = (User) session.getAttribute("editing");
        int user_id_int = 0;       
        if(user==null){
            error = true;
        }      
        if(!error&&is_tutor_bool){
            if(user.getSubject()==null||user.getSubject().isEmpty()){
                err+="Please Add a Subject of Expertise before becoming a tutor";
                error = true;
            }
        }
        if(!error){
            try{       
                if (dbOp.emailExistsOnUpdate(email, user.getId())!=0){
                    error = true;
                    err +="Email Taken<br>";
 
                }                       
                if (!error){
                    if(password == null || password.isEmpty()){
                        password = user.getPassword();
                    }
                    dbOp.updateUser(firstname,lastname,email,password,is_tutor_bool, user.getId());
                }               
            }catch(SQLException ex){
                error = true;
                ex.printStackTrace();
            }
        }
        String uri = "/WEB-INF/result.jsp";
        String successmsg = "Information has been updated!";
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
