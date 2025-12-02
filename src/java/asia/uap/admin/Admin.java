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
 * @author Chace
 */
@WebServlet(name = "Admin", urlPatterns = {"/do.admin"})
public class Admin extends HttpServlet {

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
        TutorsIO dbOp = new TutorsIO();
        HttpSession session = request.getSession();
        ArrayList<AdminUser> a = new ArrayList();
        try{
            a = dbOp.getAdminUsers();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        String email = request.getParameter("email");//request parameterrs
        String password = request.getParameter("password");
        String err = "";
        Boolean haserror = false;
        Boolean email_exists = false; //to check if username exists
        if (email == null || email.isEmpty() || password == null || password.isEmpty()){ //error handling
            response.sendRedirect("adminlogin.jsp?error=ERROR: PLEASE FILL OUT FORM");

            return;
        }
        AdminUser admin = new AdminUser(); //creates new user object
        if (a == null || a.isEmpty()){ //if the attribute retrieved is empty or null, it means no account has been made yet

            haserror = true;
            err+= "No Users Found";
        }
        else{ 
            try{
                admin = dbOp.loginAdmin(email, password);
            }catch(SQLException ex){
                ex.printStackTrace();
                haserror = true;
                err+= "Invalid Username or Password";
            }
            if (admin!=null){//checks if the passwords match
                //sets the attributes to be used in home.jsp
                session.setAttribute("currentadmin", admin);
                session.setAttribute("email", admin.getEmail());
                session.setAttribute("password", admin.getPassword());
  
            }
            else {//if passwords dont match redirect with error
                session.setAttribute("currentadmin", null);
                haserror = true;
                err+= "Wrong Username or Password";
           
            }
        }
        String uri = "/WEB-INF/result.jsp";
        String successmsg = "Logged In Successfully!";
        String href1 = "";
        String href1msg = "";
        if(!haserror){
            request.setAttribute("msg", successmsg);
            href1 = "admin/index.jsp";
            href1msg = "Continue";
        }
        else{
            request.setAttribute("msg", err); 
            href1 = "adminlogin.jsp";
            href1msg = "Go back to Login";
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
