/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.search;

import asia.uap.model.SubjectBean;
import asia.uap.model.TutorsIO;
import asia.uap.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@WebServlet(name = "SearchResults", urlPatterns = {"/do.searchresult"})
public class SearchResults extends HttpServlet {

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
        User user = new User();
        ArrayList<String> categories = new ArrayList();
        TutorsIO dbOp = new TutorsIO();
        try {
            categories = dbOp.getExistingCategories();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        HttpSession session = request.getSession();
        if (session != null) {
            user = (User) session.getAttribute("current");
        }
        String searchQuery = request.getParameter("inputFieldName");
        List<User> searchResults = new ArrayList<>();
        if (searchQuery == null) {
            searchQuery = request.getParameter("searchquery");
            if(searchQuery == null){
                searchQuery = "";
            }
        }
        String[] usertype = request.getParameterValues("usertype");   
        String[] categoryfilter = request.getParameterValues("subject");
        List<String> usertypelist = new ArrayList();
        List<String> categoryfilterlist = new ArrayList();
        Boolean has_usertype_filter = false;
        Boolean has_subject_filter = false;
        if (usertype == null || usertype.length==0) {
            has_usertype_filter = false;
        } else {
            has_usertype_filter = true;
            usertypelist = Arrays.asList(usertype);
        }
        if (categoryfilter == null || categoryfilter.length==0) {
            has_subject_filter = false;
        } else {
            categoryfilterlist = Arrays.asList(categoryfilter);
            has_subject_filter = true;
        }
        ArrayList<User> users = new ArrayList();
        try {
            // Use the searchUsers method for name-based search
            searchResults = dbOp.searchUsers(searchQuery);

            // Apply additional filters for userType and subject
            searchResults = searchResults.stream()
                    .filter(u -> {
                        boolean matchesUserType = (usertype == null)
                                || ((u.getIsTutor() && Arrays.asList(usertype).contains("Tutor"))
                                || (!u.getIsTutor() && Arrays.asList(usertype).contains("Student")));

                        boolean matchesCategory = (categoryfilter == null)
                                || (u.getSubject() != null && u.getSubject().stream()
                                .anyMatch(subject -> Arrays.asList(categoryfilter).contains(subject.getCategory_name())));

                        return matchesUserType && matchesCategory;
                    })
                    .collect(Collectors.toCollection(ArrayList::new));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        String uri = "/WEB-INF/searchresults.jsp";
        request.setAttribute("categories", categories);
        request.setAttribute("searchResults", searchResults);
        request.setAttribute("usertype", usertypelist);
        request.setAttribute("categoryfilter", categoryfilterlist);
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("user", user);
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
