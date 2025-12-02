/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.manageprofile;

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
@WebServlet(name = "DeleteSkill", urlPatterns = {"/profile/do.deleteskill"})
public class DeleteSkill extends HttpServlet {

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
        String skill = request.getParameter("item");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current");
        Boolean error = false;
        if (skill == null || skill.isEmpty() || user == null){
            error = true;
        }
        if (!error){
            try{
                dbOp.deleteUserSkills(skill, user.getId());
                user.setSkills(dbOp.getUserSkills(user.getId()));
                session.setAttribute("current", user);
            }catch(SQLException ex){
                ex.printStackTrace();
                error = true;
            }
        }
        String uri = "/WEB-INF/result.jsp";
        String successmsg = "Skill Successfully Deleted!";
        String err = "Oops! Something went wrong!";
        String href1 = "";
        String href1msg = "";
        if(!error){
            request.setAttribute("msg", successmsg);               
        }
        else{
            request.setAttribute("msg", err); 
        }
        href1 = "do.profile";
        href1msg = "Back to Profile";
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
