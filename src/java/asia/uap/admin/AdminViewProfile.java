/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.admin;

import asia.uap.model.Comment;
import asia.uap.model.ListingBean;
import asia.uap.model.StudentListingBean;
import asia.uap.model.SubjectBean;
import asia.uap.model.TutorsIO;
import asia.uap.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
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
@WebServlet(name = "AdminViewProfile", urlPatterns = {"/admin/do.profile"})
public class AdminViewProfile extends HttpServlet {

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
        String user_id = request.getParameter("user");
        User user = new User();
        TutorsIO dbOp = new TutorsIO();
        ArrayList<String> categoryList = new ArrayList();
        try{
            categoryList = dbOp.getSubjectCategories();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        String firstname = "";
        String lastname = "";
        String email = "";
        String password = "";
        String about = "";
        ArrayList<SubjectBean> usersubjects = new ArrayList();
        ArrayList<String> skills = new ArrayList();
        ArrayList<String> awards = new ArrayList();
        ArrayList<String> languages = new ArrayList();
        byte[] profile_image = {};

        double ratingCount = 0;
        int totalRating = 0;
        Boolean is_tutor = false;
        Boolean error = false;
        HttpSession session = request.getSession();
        if(user_id == null || user_id.isEmpty()){
            error = true;
        }
        int user_id_int = 0;
        if(!error){
            try{
            user_id_int = Integer.parseInt(user_id);
            }catch(NumberFormatException ex){
                error = true;
                ex.printStackTrace();
            }
        }
        if(!error){
            try{
            user = dbOp.getUser(user_id_int);
            }catch(SQLException ex){
                ex.printStackTrace();
                error = true;
            } 
        }
        
        if (user == null) {
            error = true;
        } else {
            firstname = user.getFirst_name();
            lastname = user.getLast_name();
            email = user.getEmail();
            password = user.getPassword();
            about = user.getAboutme();
            usersubjects = user.getSubject();
            skills = user.getSkills();
            awards = user.getAwards();
            languages = user.getLanguages();
            is_tutor = user.getIsTutor();
            profile_image = user.getProfileImage();
            session.setAttribute("editing", user);
        }
        String imageTag = "<img id='profilePic' src='defaultprofilepic.jpg' alt='Profile Picture' class='user-profile-pic'>";
        if (profile_image != null) {
            String base64Image = Base64.getEncoder().encodeToString(profile_image);
            // Use this base64Image string in your HTML <img> tag
            imageTag = "<img id='profilePic' src='data:image/png;base64," + base64Image + "' alt='Profile Picture' class='user-profile-pic'>";
        }
        
        ArrayList<ListingBean> listings = new ArrayList();
        if(!error){
            try{
                listings = dbOp.getUserListings(user.getId());
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        ArrayList<ListingBean> recurringListings = new ArrayList();
        if(!listings.isEmpty()){
            for (ListingBean listing : listings){
                if (listing.getRecur().toLowerCase().equals("once")){
                    recurringListings.add(listing);
                }
                if (listing.getRecur().toLowerCase().equals("weekly")) {
                    // Add the original listing first
                    recurringListings.add(listing);
                    LocalDate oldDate = LocalDate.parse(listing.getDate());
                    // Create weekly recurrences
                        for (int i = 0; i < 3; i++) {

                            LocalDate newRecurrenceDate = oldDate.plusWeeks(1); // Add one week

                            // Create a new ListingBean for each recurrence
                            ListingBean recurringListing = new ListingBean();
                            recurringListing.setDate(newRecurrenceDate.toString());
                            recurringListing.setSubject(listing.getSubject());
                            recurringListing.setPrice(listing.getPrice());
                            recurringListing.setMop(listing.getMop());
                            recurringListing.setNumber(listing.getNumber());
                            recurringListing.setTutor(listing.getTutor());
                            recurringListing.setIsPending(listing.getIsPending());
                            recurringListing.setIsConfirmed(listing.getIsConfirmed());
                            recurringListing.setIsPaid(listing.getIsPaid());
                            recurringListing.setIsComplete(listing.getIsComplete());
                            recurringListing.setHasComment(listing.getHasComment());
                            // Add the new recurrence to the list
                            recurringListings.add(recurringListing);
                            oldDate = newRecurrenceDate;
                        }
                    }
                if (listing.getRecur().toLowerCase().equals("monthly")){
                    // Add the original listing first
                    recurringListings.add(listing);
                    LocalDate oldDate = LocalDate.parse(listing.getDate());
                    // Create weekly recurrences
                    for (int i = 0; i < 3; i++) {

                        LocalDate newRecurrenceDate = oldDate.plusWeeks(4); // Add one month

                        // Create a new ListingBean for each recurrence
                        ListingBean recurringListing = new ListingBean();
                        recurringListing.setDate(newRecurrenceDate.toString());
                        recurringListing.setSubject(listing.getSubject());
                        recurringListing.setPrice(listing.getPrice());
                        recurringListing.setMop(listing.getMop());
                        recurringListing.setNumber(listing.getNumber());
                        recurringListing.setTutor(listing.getTutor());
                        recurringListing.setIsPending(listing.getIsPending());
                        recurringListing.setIsConfirmed(listing.getIsConfirmed());
                        recurringListing.setIsPaid(listing.getIsPaid());
                        recurringListing.setIsComplete(listing.getIsComplete());
                        recurringListing.setHasComment(listing.getHasComment());
                        // Add the new recurrence to the list
                        recurringListings.add(recurringListing);
                        oldDate = newRecurrenceDate;
                    }
                } 
            }
        }
        ArrayList<StudentListingBean> studentListings = new ArrayList();
        for(ListingBean l : listings){
            try{
                studentListings.addAll(dbOp.getStudentListings(l.getId()));
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        ArrayList<StudentListingBean> userSessions = new ArrayList();
        try{
            userSessions = dbOp.getUserSessions(user.getId());
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        if(!studentListings.isEmpty()){
            userSessions.addAll(studentListings);
        }
        ArrayList<Comment> comments = new ArrayList();
        if(!error){    
            try{
                comments = dbOp.getComments(user.getId());
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            if(!comments.isEmpty()){
                for (Comment comment: comments){
                    totalRating += comment.getRating();
                    ratingCount += 1;
                }
            }
        }
               
        double averageRating = 0;
        if (ratingCount > 0){
            averageRating = totalRating/ratingCount;
            
        }
        String uri = "/WEB-INF/adminviewprofile.jsp";
        request.setAttribute("user", user);
        request.setAttribute("error", error);
        request.setAttribute("imageTag", imageTag);
        request.setAttribute("firstname", firstname);
        request.setAttribute("lastname", lastname);
        request.setAttribute("email", email);
        request.setAttribute("is_tutor", is_tutor);
        request.setAttribute("about", about);
        request.setAttribute("averageRating", averageRating);
        request.setAttribute("ratingCount", ratingCount);
        request.setAttribute("password", password);
        request.setAttribute("about", about);
        request.setAttribute("usersubjects", usersubjects);
        request.setAttribute("skills", skills);
        request.setAttribute("awards", awards);
        request.setAttribute("languages", languages);
        request.setAttribute("studentListings", studentListings);
        request.setAttribute("userSessions", userSessions);
        request.setAttribute("listings", listings);
        request.setAttribute("comments", comments);
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
