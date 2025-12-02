/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.account;

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

/**
 *
 * @author 220129
 */

@WebServlet(name = "SaveAccount", urlPatterns = {"/do.register"})
public class SaveAccount extends HttpServlet {

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
        String successmsg = "Registration Successful!";
        String err = "";
        String href1 = "";
        String href1msg = "";
        TutorsIO dbOp = new TutorsIO();
        ArrayList<User> users = new ArrayList();
        try{
            users = dbOp.getUsers();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        //getting parameters
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confpass = request.getParameter("confirm-password");
        Boolean error = false;      
        //error handling
        if (firstname == null || firstname.isEmpty() || lastname == null || lastname.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()){
            error = true;
            err += "Please Fill out Form <br>";
        }
        else if (!confpass.equals(password)){
            error = true;
            err += "Passwords Do Not Match <br>";
        }
        try{
            if(dbOp.emailExists(email)!=0){
                error = true;
                err += "Email Already Taken <br>";
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            error = true;
            err += "SQL Error<br>";
        }
        if (!error){      
            try{
                dbOp.addUser(firstname, lastname, email, password);
            }catch(SQLException ex){
                ex.printStackTrace();
                error = true;
                err += "SQL Error<br>";
            }           
        }
        if(!error){
            request.setAttribute("msg", successmsg);
            href1 = "login.jsp";
            href1msg = "Log in";
        }
        else{
            request.setAttribute("msg", err);
            href1 = "register.jsp";
            href1msg = "Register";
            
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
