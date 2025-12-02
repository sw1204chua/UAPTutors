package asia.uap.search;

import asia.uap.model.Comment;
import asia.uap.model.ListingBean;
import asia.uap.model.StudentListingBean;
import asia.uap.model.SubjectBean;
import asia.uap.model.TutorsIO;
import asia.uap.model.User;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
@WebServlet(name = "SearchProfile", urlPatterns = {"/do.searchprofile"})
public class ProfileSearch extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String userid = request.getParameter("user");
        Boolean error = false;
        if (userid == null || userid.isEmpty()) {
            error = true;
        }
        User user = new User();
        User userprofile = new User();
        int id = 0;
        String firstname = "";
        String lastname = "";
        String email = "";
        String about = "";
        StringBuilder listingData = new StringBuilder("[");
        double averageRating = 0;
        double totalRating = 0;
        double ratingCount = 0;
        String imageTag = "<img id='profilePic' src='defaultprofilepic.jpg' alt='Profile Picture' class='profile-pic'>";
        ArrayList<ListingBean> listings = new ArrayList();
        ArrayList<SubjectBean> usersubjects = new ArrayList();
        ArrayList<Comment> comments = new ArrayList();
        ArrayList<String> skills = new ArrayList();
        ArrayList<String> awards = new ArrayList();
        ArrayList<String> languages = new ArrayList();
        byte[] profile_image = {};
        Boolean is_tutor = false;
        ArrayList<User> users = new ArrayList<>();
        TutorsIO dbOp = new TutorsIO();
        try {
            users = dbOp.getUsers();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        HttpSession session = request.getSession();
        user = (User) session.getAttribute("current");
        Boolean profileFound = false;
        try {
            userprofile = dbOp.getUser(Integer.parseInt(userid));
            profileFound = true;
        } catch (SQLException | NumberFormatException ex) {
            profileFound = false;
            ex.printStackTrace();
        }

        if (userprofile != null) {
            id = userprofile.getId();
            firstname = userprofile.getFirst_name();
            lastname = userprofile.getLast_name();
            email = userprofile.getEmail();
            about = userprofile.getAboutme();
            usersubjects = userprofile.getSubject();
            skills = userprofile.getSkills();
            awards = userprofile.getAwards();
            languages = userprofile.getLanguages();
            is_tutor = userprofile.getIsTutor();
            profile_image = userprofile.getProfileImage();
            profileFound = true;

        } else {
            error = true;
        }
        if (!error) {

            if (profile_image != null) {
                // Use this base64Image string in your HTML <img> tag
                imageTag = "<img id='profilePic' src='images/" + id + ".jpg' alt='Custom' class='profile-pic'>";
            }

            try {
                listings = dbOp.getUserListings(id);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            ArrayList<ListingBean> recurringListings = new ArrayList();
            if (!listings.isEmpty()) {
                for (ListingBean listing : listings) {
                    if (listing.getRecur().toLowerCase().equals("once")) {
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
                            recurringListing.setId(listing.getId());
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
                    if (listing.getRecur().toLowerCase().equals("monthly")) {
                        // Add the original listing first
                        recurringListings.add(listing);
                        LocalDate oldDate = LocalDate.parse(listing.getDate());
                        // Create weekly recurrences
                        for (int i = 0; i < 3; i++) {

                            LocalDate newRecurrenceDate = oldDate.plusWeeks(4); // Add one month

                            // Create a new ListingBean for each recurrence
                            ListingBean recurringListing = new ListingBean();
                            recurringListing.setId(listing.getId());
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
            if (!error) {

                for (ListingBean listing : listings) {
                    try {
                        studentListings.addAll(dbOp.getStudentListings(listing.getId()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    comments = dbOp.getComments(userprofile.getId());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if (!comments.isEmpty()) {
                    for (Comment comment : comments) {
                        totalRating += comment.getRating();
                        ratingCount += 1;
                    }
                }
            }

            for (ListingBean listing : recurringListings) {
                Boolean add = true;
                String color = "#808080";
                User listingtutor = listing.getTutor();
                for (StudentListingBean l : studentListings) {
                    if (l.getListing_id() == listing.getId() && l.getBooking_date().equals(listing.getDate()) && l.getIsPending()) {
                        add = false;
                    }
                    if (l.getListing_id() == listing.getId() && l.getBooking_date().equals(listing.getDate()) && l.getIsConfirmed()) {
                        add = false;
                    }
                    if (l.getListing_id() == listing.getId() && l.getBooking_date().equals(listing.getDate()) && l.getIsComplete()) {
                        add = false;
                    }
                }

                if (add) {
                    listingData.append("{")
                            .append("\"title\": \"" + listing.getId() + "-").append(listing.getSubject()).append("\",")
                            .append("\"start\": \"").append(listing.getDate()).append("\",")
                            .append("\"end\": \"").append(listing.getDate()).append("\",")
                            .append("\"color\": \"").append(color).append("\"")
                            .append("},");

                }

            }
            if (listingData.length() > 1) {
                listingData.setLength(listingData.length() - 1);
            }

            listingData.append("]");

            averageRating = (ratingCount > 0) ? totalRating / ratingCount : 0;
            Iterator<ListingBean> iter = listings.iterator();
            while (iter.hasNext()) {
                ListingBean originalListing = iter.next();
                boolean allBooked = true;

                for (ListingBean recurringListing : recurringListings) {
                    if (recurringListing.getId() == originalListing.getId()) {
                        boolean isBooked = false;

                        for (StudentListingBean studentListing : studentListings) {
                            if (studentListing.getListing_id() == recurringListing.getId()
                                    && studentListing.getBooking_date().equals(recurringListing.getDate())
                                    && (studentListing.getIsPending() || studentListing.getIsConfirmed())) {
                                isBooked = true;
                                break;
                            }
                        }

                        if (!isBooked) {
                            allBooked = false;
                            break;
                        }
                    }
                }

                if (allBooked) {
                    iter.remove();
                }
            }
        }
        String uri = "/WEB-INF/profilesearch.jsp";
        request.setAttribute("user", user);
        request.setAttribute("profileFound", profileFound);
        request.setAttribute("imageTag", imageTag);
        request.setAttribute("firstname", firstname);
        request.setAttribute("lastname", lastname);
        request.setAttribute("email", email);
        request.setAttribute("is_tutor", is_tutor);
        request.setAttribute("about", about);
        request.setAttribute("averageRating", averageRating);
        request.setAttribute("ratingCount", ratingCount);
        request.setAttribute("userprofile", userprofile);
        request.setAttribute("about", about);
        request.setAttribute("usersubjects", usersubjects);
        request.setAttribute("skills", skills);
        request.setAttribute("awards", awards);
        request.setAttribute("languages", languages);
        request.setAttribute("listings", listings);
        request.setAttribute("comments", comments);
        request.setAttribute("listingData", listingData);
        request.setAttribute("id", id);
        request.setAttribute("error", error);
        RequestDispatcher rd = request.getRequestDispatcher(uri);
        rd.forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
