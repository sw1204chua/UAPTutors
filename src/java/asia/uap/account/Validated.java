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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Chace
 */
@WebServlet(name = "Validated", urlPatterns = {"/do.login"})
public class Validated extends HttpServlet {

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
        String successmsg = "Login Successful!";
        String href1 = "";
        String href1msg = "";
        TutorsIO dbOp = new TutorsIO();
        HttpSession session = request.getSession();
        ArrayList<User> users = new ArrayList(); //arraylist to save new users
        try{
            users = dbOp.getUsers();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        String email = request.getParameter("email");//request parameters
        String password = request.getParameter("password");
        String err = "";
        Boolean haserror = false;
        Boolean email_exists = false; //to check if username exists
        if (email == null || email.isEmpty() || password == null || password.isEmpty()){ //error handling
            haserror = true;
            err+= "Please Fill Out Form";
        }
        User user = new User(); //creates new user object
        if (users == null || users.isEmpty()){ //if the attribute retrieved is empty or null, it means no account has been made yet
            haserror = true;
            err+= "No Users Found";
        }
        if(!haserror){
            try{
                log(dbOp.encrypt(password));
                user = dbOp.loginUser(email, password);
            }catch(SQLException ex){
                haserror = true;
                err+= "Invalid Username or Password";
                ex.printStackTrace();
            }
            if (user!=null&&user.getFirst_name()!=null){//checks if the passwords match
                //sets the attributes to be used in home.jsp
                session.setAttribute("current", user);
                session.setAttribute("first_name", user.getFirst_name());
                session.setAttribute("last_name", user.getLast_name());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("password", user.getPassword());
            }
            else {//if passwords dont match redirect with error
                haserror = true;
                err+= "Wrong Username or Password";
            }        
        }
        request.setAttribute("haserror", haserror);
        if(haserror){
            request.setAttribute("msg", err);
            href1 = "login.jsp";
            href1msg = "Login Again";
        }
        else{
            request.setAttribute("msg", successmsg);
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
