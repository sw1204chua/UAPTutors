/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.admin;

import asia.uap.model.AdminRequestBean;
import asia.uap.model.TutorsIO;
import asia.uap.model.User;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Chace
 */
@WebServlet(name = "DeleteUser", urlPatterns = {"/admin/do.delete"})
public class DeleteUser extends HttpServlet {

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
        HttpSession session = request.getSession();
        User loggedinuser = (User)session.getAttribute("current");
        String[] delete = request.getParameterValues("approval");
        String choice = request.getParameter("choice");
        ArrayList<Integer> wdelete = new ArrayList();
        TutorsIO io = new TutorsIO();
        AdminRequestBean adminrequest = new AdminRequestBean();
        Boolean err = false;
        if (choice==null||choice.isEmpty() || delete == null || delete.length==0){
            err = true;
        }
        if(!err){     
            for (int i = 0; i < delete.length; i++) {
                try{
                    wdelete.add(Integer.parseInt(delete[i]));
                }catch(NumberFormatException ex){
                    err = true;
                }       
            }           
        }
        if(!err){   
            try {      
                // Attempt to delete each user
                for (int id : wdelete) {
                    adminrequest = io.getRequest(id);                     
                    if(choice.equals("Approve")){
                        io.updateRequestStatus(adminrequest.getActionId(), "approved");
                        io.deleteUser(adminrequest.getProfileId());
                        if(loggedinuser!=null&&loggedinuser.getId()==adminrequest.getProfileId()){
                            session.setAttribute("current", null);
                        }
                    }
                    else if (choice.equals("Deny")){
                        io.updateRequestStatus(adminrequest.getActionId(), "denied");
                    }
                    else{
                        err = true;
                    }
                    
                }        
            } catch (SQLException ex) {
                err = true;
                ex.printStackTrace();
            }
        }
        String uri = "/WEB-INF/result.jsp";
        String successmsg = "";
        if (!err&&choice.equals("Approve")) {
                successmsg = "User has been deleted</h1>";
            } 
            else if (!err&&choice.equals("Deny")){
                 successmsg ="Request has been denied";
            }
            else {
                successmsg = "ERROR: NO SELECTED USER OR DATABASE ERROR";
            }       
        String href1 = "";
        String href1msg = "";
        request.setAttribute("msg", successmsg);
        href1 = "do.adminrequest";
        href1msg = "Back To Requests";   
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

