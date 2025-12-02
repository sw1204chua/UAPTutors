/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap;

import asia.uap.model.SubjectBean;
import asia.uap.model.User;
import asia.uap.model.TutorsIO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
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
@WebServlet(name = "ListingInformation", urlPatterns = {"/profile/do.listinginformation"})
public class ListingInformation extends HttpServlet {
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
        String date = request.getParameter("date");
        String tutorId_string = request.getParameter("tutor");
        
        User tutor = new User();
        Boolean validDate = true;
        Boolean validTutor = true;
        Boolean hasSubject = true;
        Boolean error = false;
        if(date == null || date.isEmpty() || tutorId_string == null || tutorId_string.isEmpty()){
            error = true;
        }
        Integer tutorId = null;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current");
        TutorsIO dbOp = new TutorsIO();
        ArrayList<User> users = new ArrayList();
        Integer random = dbOp.generateRandom();
        try{
            users = dbOp.getUsers();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        if (users == null){
            validTutor = false;
        }    
        if(!error){
        try{
            String[] date_split = date.split("-"); //splits the string date
            int year = Integer.parseInt(date_split[0]); //first number is year
            int month = Integer.parseInt(date_split[1]); //next is month
            int day = Integer.parseInt(date_split[2]); //last is day.
            LocalDate d = LocalDate.of(year,month,day); //Stores LocalDate object of the
        }catch (DateTimeException | NullPointerException ex){
            ex.printStackTrace();
            validTutor = false;

        }
        }
        if (tutorId_string == null){
            validTutor = false;            
        }
        if(validTutor){
            try{
                tutorId = Integer.parseInt(tutorId_string);
            }catch(NumberFormatException ex){
                validTutor = false;
                ex.printStackTrace();
            }
        }
        if (validTutor&&users != null){
            try{
                tutor = dbOp.getUser(tutorId); 
            }catch(SQLException ex){
                ex.printStackTrace();
                error = true;
            }
        }
        if (tutor!=null&&tutor.getFirst_name()==null){
            validTutor = false;
        }
        if (tutor==null||tutor.getSubject()==null){         
            hasSubject = false;                     
        }
        String uri = "/WEB-INF/listinginformation.jsp";
        request.setAttribute("date", date);
        request.setAttribute("tutor", tutor);
        request.setAttribute("validDate", validDate);
        request.setAttribute("validTutor", validTutor);
        request.setAttribute("hasSubject", hasSubject);
        request.setAttribute("random", random);
        RequestDispatcher rd = request.getRequestDispatcher(uri);
        rd.forward(request, response);
        response.setContentType("text/html;charset=UTF-8");
        
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
