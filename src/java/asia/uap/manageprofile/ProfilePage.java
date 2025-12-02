package asia.uap.manageprofile;

import asia.uap.model.Comment;
import asia.uap.model.SubjectBean;
import asia.uap.model.StudentListingBean;
import asia.uap.model.User;
import asia.uap.model.ListingBean;
import asia.uap.model.TutorsIO;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5MB max file size
@WebServlet(name = "ProfilePage", urlPatterns = {"/profile/do.profile"})
public class ProfilePage extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        User user = new User();
        TutorsIO dbOp = new TutorsIO();
        ArrayList<String> categoryList = new ArrayList();
        try {
            categoryList = dbOp.getSubjectCategories();
        } catch (SQLException ex) {
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
        user = (User) session.getAttribute("current");
        if (user == null) {
            error = true;
        } else {
            try {
                user = dbOp.getUser(user.getId());
            } catch (SQLException ex) {
                error = true;
                ex.printStackTrace();
            }
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
        }
        String directory = dbOp.getDirectory();

        log("DIRECTIORY" + directory);
        String fileName = "";

        if (!error) {
            fileName = user.getId() + ".jpg";
            File file = new File(directory, fileName);
            log("DIRECTIORY" + file);
            try {
                if (!file.exists()) {
                    Blob blob = dbOp.getImageBlob(user.getId());
                    if (blob != null) {
                        dbOp.saveImageFromBlob(blob, file);
                        log("file saved");
                    }
                }
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }
        }

        String imageTag = "<img id='profilePic' src='../defaultprofilepic.jpg' alt='Default' class='user-profile-pic'>";
        if (profile_image != null) {

            // Use this base64Image string in your HTML <img> tag
            imageTag = "<img id='profilePic' src='../images/" + fileName + "' alt='Custom' onerror='this.onerror=null; this.src='../defaultprofilepic.jpg'; class='user-profile-pic'>";

        }

        ArrayList<ListingBean> listings = new ArrayList();
        if (!error) {
            try {
                listings = dbOp.getUserListings(user.getId());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        ArrayList<StudentListingBean> studentListings = new ArrayList();
        for (ListingBean l : listings) {
            try {
                studentListings.addAll(dbOp.getStudentListings(l.getId()));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        ArrayList<StudentListingBean> allStudentListings = new ArrayList();
        try {
            allStudentListings = dbOp.getStudentListingsByTutor(user.getId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ArrayList<ListingBean> recurringListings = new ArrayList();
        if (!listings.isEmpty()) {
            for (ListingBean listing : listings) {
                if (listing.getRecur().toLowerCase().equals("once")) {
                    Boolean add = true;
                    for (StudentListingBean l : studentListings) {
                        if (l.getBooking_date().equals(listing.getDate())) {
                            add = false;
                            break;
                        }
                    }
                    if (add) {
                        recurringListings.add(listing);
                    }
                }
                if (listing.getRecur().toLowerCase().equals("weekly")) {
                    // Add the original listing first
                    Boolean add = true;
                    for (StudentListingBean l : studentListings) {
                        if (l.getBooking_date().equals(listing.getDate())) {
                            add = false;
                        }
                    }
                    if (add) {
                        recurringListings.add(listing);
                    }
                    LocalDate oldDate = LocalDate.parse(listing.getDate());
                    // Create weekly recurrences
                    for (int i = 0; i < 3; i++) {
                        add = true;
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
                        for (StudentListingBean l : studentListings) {
                            if (l.getBooking_date().equals(recurringListing.getDate())) {
                                add = false;
                            }
                        }
                        if (add) {
                            recurringListings.add(recurringListing);
                        }
                        oldDate = newRecurrenceDate;
                    }
                }
                if (listing.getRecur().toLowerCase().equals("monthly")) {
                    // Add the original listing first
                    Boolean add = true;
                    for (StudentListingBean l : studentListings) {
                        if (l.getBooking_date().equals(listing.getDate())) {
                            add = false;
                        }
                    }
                    if (add) {

                        recurringListings.add(listing);
                    }
                    LocalDate oldDate = LocalDate.parse(listing.getDate());
                    // Create weekly recurrences
                    for (int i = 0; i < 3; i++) {
                        add = true;
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
                        for (StudentListingBean l : studentListings) {
                            if (l.getBooking_date().equals(recurringListing.getDate())) {
                                add = false;
                            }
                        }
                        if (add) {
                            recurringListings.add(recurringListing);
                        }
                        oldDate = newRecurrenceDate;
                    }
                }
            }
        }

        ArrayList<StudentListingBean> userSessions = new ArrayList();
        try {
            userSessions = dbOp.getUserSessions(user.getId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (!allStudentListings.isEmpty()) {
            userSessions.addAll(allStudentListings);
        }
        ArrayList<Comment> comments = new ArrayList();
        if (!error) {
            try {
                comments = dbOp.getComments(user.getId());
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

        StringBuilder listingData = new StringBuilder("[");

        for (ListingBean listing : recurringListings) {

            if (user != null) {

                // Determine the color based on the listing status
                String color = "#808080";

                // Append event data with color property
                listingData.append("{")
                        .append("\"title\": \"").append(listing.getSubject()).append("\",")
                        .append("\"start\": \"").append(listing.getDate()).append("\",")
                        .append("\"end\": \"").append(listing.getDate()).append("\",")
                        .append("\"color\": \"").append(color).append("\"")
                        .append("},");

            }
        }
        for (StudentListingBean listing : studentListings) {

            if (user != null && !listing.getIsComplete()) {

                // Determine the color based on the listing status
                String color = "#808080";
                if (listing.getIsPending()) {
                    color = "#FFA500";
                }
                if (listing.getIsConfirmed()) {
                    color = "#28A745";
                }

                // Append event data with color property
                listingData.append("{")
                        .append("\"title\": \"").append(listing.getSubject()).append("\",")
                        .append("\"start\": \"").append(listing.getBooking_date()).append("\",")
                        .append("\"end\": \"").append(listing.getBooking_date()).append("\",")
                        .append("\"color\": \"").append(color).append("\"")
                        .append("},");
            }
        }

        if (listingData.length() > 1) {
            listingData.setLength(listingData.length() - 1);
        }

        listingData.append("]");
        double averageRating = 0;
        if (ratingCount > 0) {
            averageRating = totalRating / ratingCount;

        }
        for (Comment comment : comments) {
            if (!error) {
                User student = comment.getStudentlisting().getStudent();
                if (student != null) {
                    fileName = student.getId() + ".jpg";
                    File file = new File(directory, fileName);
                    log("DIRECTIORY" + file);
                    try {
                        if (!file.exists()) {
                            Blob blob = dbOp.getImageBlob(student.getId());
                            if (blob != null) {
                                dbOp.saveImageFromBlob(blob, file);
                                log("file saved");
                            }
                        }
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        }
        Boolean pendingFound = false;
        int pending_count = 0;
        int confirmed_count = 0;
        for (StudentListingBean listing : studentListings) {
            if (listing.getIsPending() && !listing.getIsComplete()) {
                pending_count++;
            }
            if (listing.getIsConfirmed()) {
                confirmed_count++;
            }
        }

        String uri = "/WEB-INF/profilepage.jsp";
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
        request.setAttribute("pending_count", pending_count);
        request.setAttribute("confirmed_count", confirmed_count);
        request.setAttribute("userSessions", userSessions);
        request.setAttribute("listings", listings);
        request.setAttribute("comments", comments);
        request.setAttribute("listingData", listingData);
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
