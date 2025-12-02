/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 *
 * @author Hawy
 */

public class TutorsIO {
    private String driver;
    private String url;
    private String user;
    private String pass;
    private String directory;

    public String getDirectory() {
        return directory;
    }
    
    private boolean loadClass() {
        //Load whatever preloaded driver from SQL properties file.
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    public void loadSQL() {
        //Retrieve from SQL.properties
        ResourceBundle sql = ResourceBundle.getBundle("asia.uap.model.SQL");
        this.url = sql.getString("url");
        this.user = sql.getString("user");
        this.pass = sql.getString("password");
        this.driver = sql.getString("driver");
        this.directory = sql.getString("directory");
    }    
    public TutorsIO (String driver, String url, String user, String pass){   
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.driver = driver;
    }
    public TutorsIO(){
        loadSQL();
    }
    public void addUser(String first_name, String last_name, String email, String password) throws SQLException{
        loadClass();
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String sql = ("INSERT INTO user(first_name, last_name, email, password, about_me, is_tutor) VALUES (?, ?, ?, ?, 'No Information Yet', 0)");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, first_name);
            ps.setString(2, last_name);
            ps.setString(3, email);
            ps.setString(4, encrypt(password));
            ps.executeUpdate();

        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            if (conn != null) conn.close();
        }
    }
    public User getUser(int id) throws SQLException {
        loadClass(); // Load MySQL driver
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);

            // First, get the main user data
            String sql = "SELECT * FROM user WHERE ID = ? AND is_deleted = false";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                int userId = rs.getInt("id");
                user.setId(userId);
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAboutme(rs.getString("about_me"));
                user.setIsTutor(rs.getBoolean("is_tutor"));
                byte[] profileImage = rs.getBytes("profile_image");

                user.setProfileImage(profileImage);




                // Retrieve education for the user
                user.setSubject(getUserSubjects(userId));

                // Retrieve skills for the user
                user.setSkills(getUserSkills(userId));

                // Retrieve awards for the user
                user.setAwards(getUserAwards(userId));

                // Retrieve languages for the user
                user.setLanguages(getUserLanguages(userId));
                return user;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                 if (conn != null) {
                     conn.close();
                 }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
           
        }
        return null;
    }
    public int emailExists(String email) throws SQLException{
        loadClass(); // Load MySQL driver
        int rows = 0;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);

            String sql = "SELECT COUNT(*) FROM user WHERE email = ? AND is_deleted = false";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            
            if (rs.next()) {
                rows = rs.getInt(1); 
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                 if (conn != null) {
                     conn.close();
                 }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
           
        }
        return rows;
    }
    public int emailExistsOnUpdate(String email, int id) throws SQLException{
        loadClass(); // Load MySQL driver
        int rows = 0;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);

            String sql = "SELECT COUNT(*) FROM user WHERE email = ? AND id != ? AND is_deleted = false";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();

            
            if (rs.next()) {
                rows = rs.getInt(1); 
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                 if (conn != null) {
                     conn.close();
                 }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
           
        }
        return rows;
    }
    public ArrayList<User> getUsers() throws SQLException {
        loadClass(); // Load MySQL driver
        Connection conn = null;
        ArrayList<User> userList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pass);

            // First, get the main user data
            String sql = "SELECT * FROM user WHERE is_deleted = false";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                int userId = rs.getInt("id");
                user.setId(userId);
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAboutme(rs.getString("about_me"));
                user.setIsTutor(rs.getBoolean("is_tutor"));
                byte[] profileImage = rs.getBytes("profile_image");

                user.setProfileImage(profileImage);
                

                // Retrieve education for the user
                user.setSubject(getUserSubjects(userId));

                // Retrieve skills for the user
                user.setSkills(getUserSkills(userId));

                // Retrieve awards for the user
                user.setAwards(getUserAwards(userId));

                // Retrieve languages for the user
                user.setLanguages(getUserLanguages(userId));

                userList.add(user);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                 if (conn != null) {
                     conn.close();
                 }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
           
        }
        return userList;
    }
    public ArrayList<User> getTutors() throws SQLException {
        loadClass(); // Load MySQL driver
        Connection conn = null;
        ArrayList<User> userList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pass);

            String sql = "SELECT * FROM user WHERE is_tutor = TRUE AND is_deleted = false";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                int userId = rs.getInt("id");
                user.setId(userId);
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAboutme(rs.getString("about_me"));
                user.setIsTutor(rs.getBoolean("is_tutor"));
                byte[] profileImage = rs.getBytes("profile_image");

                user.setProfileImage(profileImage);
                

                // Retrieve education for the user
                user.setSubject(getUserSubjects(userId));

                // Retrieve skills for the user
                user.setSkills(getUserSkills(userId));

                // Retrieve awards for the user
                user.setAwards(getUserAwards(userId));

                // Retrieve languages for the user
                user.setLanguages(getUserLanguages(userId));

                userList.add(user);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                 if (conn != null) {
                     conn.close();
                 }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
           
        }
        return userList;
    }
    public void updateUser(String firstname, String lastname, String email, String password, Boolean is_tutor, int id) throws SQLException{
        loadClass(); // Load MySQL driver
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String sql = "UPDATE user SET first_name = ?, last_name = ?, email = ?, password = ?, is_tutor = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setBoolean(5, is_tutor);
            ps.setInt(6, id);
            ps.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }    
    }
    public void updateUserAbout(String aboutme, int userId) throws SQLException{
        loadClass(); // Load MySQL driver
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String sql = "UPDATE user SET about_me = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, aboutme);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    public void deleteUserSubject(String subjectname, int userId) throws SQLException{
        loadClass(); // Load MySQL driver
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String sql = "DELETE FROM user_subjects WHERE user_id = ? AND subject_name = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, subjectname);
            ps.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    public Boolean addUserSubjects(String subjectname, String subjectcategory, int userId) throws SQLException{
        loadClass(); // Load MySQL driver
        Connection conn = null;
        int categoryId = getOrCreateCategoryId(subjectcategory);
        if(categoryId == -1){
            return false;
        }
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String userSubjectInsertSQL = "INSERT INTO user_subjects (user_id, category_id, subject_name) VALUES (?, ?, ?)";
            PreparedStatement userSubjectPs = conn.prepareStatement(userSubjectInsertSQL);
            userSubjectPs.setInt(1, userId);
            userSubjectPs.setInt(2, categoryId);
            userSubjectPs.setString(3, subjectname);
            userSubjectPs.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        return true;
    }
    public ArrayList<SubjectBean> getUserSubjects(int userId) throws SQLException {
        loadSQL();
        Connection conn = null;
        ArrayList<SubjectBean> subjectList = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT us.category_id, sc.category_name, us.subject_name FROM user_subjects us INNER JOIN subjectcategories sc ON us.category_id = sc.category_id WHERE us.user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SubjectBean subject = new SubjectBean();
                subject.setCategory_id(rs.getInt("category_id"));
                subject.setCategory_name(rs.getString("category_name"));
                subject.setSubject_name(rs.getString("subject_name"));
                subjectList.add(subject);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        return subjectList;
    }

    public ArrayList<String> getUserSkills(int userId) throws SQLException {
        loadSQL();
        Connection conn = null;
        ArrayList<String> skillsList = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT skill FROM user_skills WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                skillsList.add(rs.getString("skill"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        return skillsList;
    }
    public Boolean addUserSkills(int userId, String skill) throws SQLException{
        loadClass(); // Load MySQL driver
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String userSubjectInsertSQL = "INSERT INTO user_skills (user_id, skill) VALUES (?, ?)";
            PreparedStatement userSubjectPs = conn.prepareStatement(userSubjectInsertSQL);
            userSubjectPs.setInt(1, userId);
            userSubjectPs.setString(2, skill);
            userSubjectPs.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        return true;
    }
    public void deleteUserSkills(String skill, int userId) throws SQLException{
        loadClass(); // Load MySQL driver
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String sql = "DELETE FROM user_skills WHERE user_id = ? AND skill = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, skill);
            ps.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    public ArrayList<String> getUserAwards(int userId) throws SQLException {
        loadSQL();
        Connection conn = null;
        ArrayList<String> awardsList = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT award FROM user_awards WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                awardsList.add(rs.getString("award"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        return awardsList;
    }
    public Boolean addUserAwards(int userId, String award) throws SQLException{
        loadClass(); // Load MySQL driver
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String userSubjectInsertSQL = "INSERT INTO user_awards (user_id, award) VALUES (?, ?)";
            PreparedStatement userSubjectPs = conn.prepareStatement(userSubjectInsertSQL);
            userSubjectPs.setInt(1, userId);
            userSubjectPs.setString(2, award);
            userSubjectPs.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        return true;
    }
    public void deleteUserAwards(String award, int userId) throws SQLException{
        loadClass(); // Load MySQL driver
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String sql = "DELETE FROM user_awards WHERE user_id = ? AND award = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, award);
            ps.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    public ArrayList<String> getUserLanguages(int userId) throws SQLException {
        loadSQL();
        Connection conn = null;
        ArrayList<String> languagesList = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT language FROM user_languages WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                languagesList.add(rs.getString("language"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        return languagesList;
    }
    public Boolean addUserLanguages(int userId, String language) throws SQLException{
        loadClass(); // Load MySQL driver
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String userSubjectInsertSQL = "INSERT INTO user_languages (user_id, language) VALUES (?, ?)";
            PreparedStatement userSubjectPs = conn.prepareStatement(userSubjectInsertSQL);
            userSubjectPs.setInt(1, userId);
            userSubjectPs.setString(2, language);
            userSubjectPs.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        return true;
    }
    public void deleteUserLanguages(String language, int userId) throws SQLException{
        loadClass(); // Load MySQL driver
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String sql = "DELETE FROM user_languages WHERE user_id = ? AND language = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, language);
            ps.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    public ArrayList<String> getSubjectCategories() throws SQLException{
        loadClass(); // Load MySQL driver
        Connection conn = null;
        ArrayList<String> categoryList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pass);

            // First, get the main user data
            String sql = "SELECT * FROM subjectcategories";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categoryList.add(rs.getString("category_name"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                if (conn != null) {
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }           
        }
        return categoryList;
    }
    public ArrayList<String> getExistingCategories() throws SQLException{
        loadClass(); // Load MySQL driver
        Connection conn = null;
        ArrayList<String> categoryList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pass);

            // First, get the main user data
            String sql = "SELECT DISTINCT sc.category_id, sc.category_name\n" +
                        "FROM subjectcategories sc\n" +
                        "JOIN user_subjects us ON sc.category_id = us.category_id;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categoryList.add(rs.getString("category_name"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                if (conn != null) {
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }           
        }
        return categoryList;
    }
    private int getOrCreateCategoryId(String categoryName) throws SQLException {
        // Step 1: Check if category already exists
        loadSQL();
        Connection conn = null;
        int id = -1;
        try {
            conn = DriverManager.getConnection(url, user, pass);
            String categorySelectSQL = "SELECT category_id FROM subjectcategories WHERE category_name = ?";
            PreparedStatement selectPs = conn.prepareStatement(categorySelectSQL);
            selectPs.setString(1, categoryName);
            ResultSet rs = selectPs.executeQuery();
            if (rs.next()) {
                id = rs.getInt("category_id");
                return id;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        // Step 2: Insert new category if it doesn't exist and get its ID
        
        try{
            String categoryInsertSQL = "INSERT INTO subjectcategories (category_name) VALUES (?)";
            PreparedStatement insertPs = conn.prepareStatement(categoryInsertSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            insertPs.setString(1, categoryName);
            insertPs.executeUpdate();
            
            ResultSet generatedKeys = insertPs.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return id;
    }
    
    public void updateProfileImage(int userId, InputStream imageStream) throws SQLException {
        String sql = "UPDATE user SET profile_image = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBlob(1, imageStream);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            System.out.println("Profile image updated successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<ListingBean> getAllListings() throws SQLException {
        ArrayList<ListingBean> listings = new ArrayList<>();
        String query = "SELECT l.listing_id, l.listing_date, l.recur, l.payment_scheme_id, l.subject_name, "
                     + "l.price, l.contact_number, l.tutor_id, ps.method_of_payment "
                     + "FROM listing l "
                     + "JOIN paymentscheme ps ON l.payment_scheme_id = ps.payment_scheme_id "
                     + "WHERE l.is_deleted = false";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, pass);
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ListingBean listing = new ListingBean();
                // Populate ListingBean fields
                listing.setId(rs.getInt("listing_id"));
                listing.setDate(rs.getString("listing_date"));
                listing.setRecur(rs.getString("recur")); 
                listing.setSubject(rs.getString("subject_name"));
                listing.setPrice(rs.getInt("price"));
                listing.setMop(rs.getString("method_of_payment"));
                listing.setNumber(rs.getString("contact_number"));

                // Retrieve tutor details
                User tutor = getUser(rs.getInt("tutor_id"));
                listing.setTutor(tutor);

                // No direct linkage to a specific student here as per schema
                listings.add(listing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    }

    return listings;
    }

    public ListingBean getListing(int listing_id) throws SQLException {
        ListingBean listing = new ListingBean();
        String sql = "SELECT l.listing_id, l.listing_date, l.recur, l.payment_scheme_id, l.subject_name, "
                    + "l.price, l.contact_number, ps.method_of_payment, l.tutor_id "
                    + "FROM listing l "
                    + "JOIN paymentscheme ps ON l.payment_scheme_id = ps.payment_scheme_id "
                    + "WHERE l.listing_id = ?";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, listing_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                listing.setId(rs.getInt("listing_id"));
                listing.setDate(rs.getString("listing_date"));
                listing.setRecur(rs.getString("recur"));
                listing.setSubject(rs.getString("subject_name"));
                listing.setPrice(rs.getInt("price"));
                listing.setMop(rs.getString("method_of_payment"));
                listing.setNumber(rs.getString("contact_number"));
                User tutor = getUser(rs.getInt("tutor_id"));
                listing.setTutor(tutor);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                 if (conn != null) {
                     conn.close();
                 }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
           
        }

        return listing;
    }
    public ArrayList<ListingBean> getUserListings(int tutorId) throws SQLException {
        ArrayList<ListingBean> listings = new ArrayList<>();
        String sql = "SELECT l.listing_id, l.listing_date, l.recur, l.payment_scheme_id, l.subject_name, "
                   + "l.price, l.contact_number, ps.method_of_payment "
                   + "FROM listing l "
                   + "JOIN paymentscheme ps ON l.payment_scheme_id = ps.payment_scheme_id "
                   + "WHERE l.tutor_id = ? AND l.is_deleted = false";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, tutorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ListingBean listing = new ListingBean();

                listing.setId(rs.getInt("listing_id"));
                listing.setDate(rs.getString("listing_date"));
                listing.setRecur(rs.getString("recur")); 
                listing.setSubject(rs.getString("subject_name"));
                listing.setPrice(rs.getInt("price"));
                listing.setMop(rs.getString("method_of_payment"));
                listing.setNumber(rs.getString("contact_number"));
                listing.setIsPending(false);
                listing.setIsConfirmed(false);
                listing.setHasComment(false);
                listing.setIsComplete(false);

                User tutor = getUser(tutorId);
                listing.setTutor(tutor);

                listings.add(listing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return listings;
    }

    public boolean addListing(ListingBean listing) throws SQLException {
        String query = "INSERT IGNORE INTO listing (listing_date, payment_scheme_id, subject_name, "
                     + "price, contact_number, tutor_id, recur) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Setting parameters
            stmt.setString(1, listing.getDate());
            stmt.setInt(2, getPaymentSchemeId(listing.getMop())); // Assuming you have a method to get payment scheme ID
            stmt.setString(3, listing.getSubject());
            stmt.setInt(4, listing.getPrice());
            stmt.setString(5, listing.getNumber());
            stmt.setInt(6, listing.getTutor().getId());
            stmt.setString(7, listing.getRecur());
            // Execute update
            int rowsAffected = stmt.executeUpdate();
            conn.close();
            return rowsAffected > 0; // Return true if the insert was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean setListingPending(ListingBean listing, int student_id) throws SQLException {
        String sql = "UPDATE listing SET is_pending = true where listing_id = ?";
        try {
            addStudentListing(listing.getId(), student_id);
            Connection conn = DriverManager.getConnection(url, user, pass);
       
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, listing.getId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean declineBooking(ListingBean listing) throws SQLException {
        String sql = "UPDATE listing SET is_pending = false where listing_id = ?";
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
       
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, listing.getId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean setListingConfirmed(StudentListingBean listing) throws SQLException {
        String sql = "UPDATE student_listing SET is_confirmed = true, is_pending = false where booked_id = ?";
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
       
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, listing.getBooked_id());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean setListingPaid(StudentListingBean listing, String reference) throws SQLException {
        String sql = "UPDATE student_listing SET is_paid = true, payment_reference = ? where booked_id = ?";
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
       
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, reference);
            ps.setInt(2, listing.getBooked_id());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean setListingNotPaid(StudentListingBean listing, String reason) throws SQLException {
        String sql = "UPDATE student_listing SET is_paid = false, rejected_payment = ? where booked_id = ?";
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
       
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, reason);
            ps.setInt(2, listing.getBooked_id());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean setListingComplete(StudentListingBean listing) throws SQLException {
        String sql = "UPDATE student_listing SET is_complete = true where booked_id = ?";
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
       
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, listing.getBooked_id());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void addStudentListing(int listing_id, int student_id) throws SQLException {
        String sql = "INSERT INTO student_listing(listing_id, student_id) VALUES (?,?)";
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
       
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, listing_id);
            ps.setInt(2, student_id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public void deleteStudentListing(StudentListingBean listing) throws SQLException {
    String sql = "DELETE FROM student_listing WHERE booked_id = ?";
    try (Connection conn = DriverManager.getConnection(url, user, pass);
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, listing.getBooked_id());
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("No matching record found.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public StudentListingBean getStudentListing(int booked_id) throws SQLException{
        StudentListingBean studentListing = new StudentListingBean();
        String sql = "SELECT * FROM student_listing WHERE booked_id = ? AND is_deleted = false";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, booked_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    // Fields from STUDENT_LISTING
                    studentListing.setBooked_id(rs.getInt("booked_id"));
                    studentListing.setBooking_date(rs.getString("booking_date"));
                    studentListing.setIsPending(rs.getBoolean("is_pending"));
                    studentListing.setIsConfirmed(rs.getBoolean("is_confirmed"));
                    studentListing.setIsComplete(rs.getBoolean("is_complete"));
                    studentListing.setIsPaid(rs.getBoolean("is_paid"));
                    studentListing.setHasComment(rs.getBoolean("has_comment"));
                    studentListing.setReferencenumber(rs.getString("payment_reference"));
                    studentListing.setRejected_payment(rs.getString("rejected_payment"));
                    studentListing.setStudent(getUser(rs.getInt("student_id")));
                    // Fields from LISTING
                    ListingBean listing = getListing(rs.getInt("listing_id"));
                    studentListing.setListing_id(listing.getId());
                    studentListing.setDate(listing.getDate()); // From listing
                    studentListing.setSubject(listing.getSubject());
                    studentListing.setPrice(listing.getPrice());
                    studentListing.setNumber(listing.getNumber());   
                    studentListing.setMop(listing.getMop());
                    studentListing.setTutor(listing.getTutor());

                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return studentListing;
    }
    // Helper method to get payment scheme ID by name (mop)
    private int getPaymentSchemeId(String mop) {
        String query = "SELECT payment_scheme_id FROM paymentscheme WHERE method_of_payment = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, mop);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("payment_scheme_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if not found
    }
    public void deleteUser(int id) throws SQLException {    
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
            conn.setAutoCommit(false); // Start transaction
            String sql = "UPDATE user SET is_deleted = TRUE WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            
            String archiveListing = "UPDATE listing l "
                                    + "LEFT JOIN student_listing sl ON l.listing_id = sl.listing_id "
                                    + "SET l.is_deleted = true, sl.is_deleted = true WHERE l.tutor_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(archiveListing);
            ps2.setInt(1, id);

            ps2.executeUpdate();
            
                       
            String archiveStudentListing = "UPDATE student_listing sl "
                                        + "SET sl.is_deleted = true WHERE sl.student_id = ?";
            PreparedStatement ps3 = conn.prepareStatement(archiveStudentListing);
            ps3.setInt(1, id);
            ps3.executeUpdate();
            
            String archiveComment = "UPDATE comments c " +
                                    "JOIN student_listing sl ON c.booked_id = sl.booked_id " +
                                    "SET c.is_deleted = true " +
                                    "WHERE sl.is_deleted = true AND sl.student_id = ?";

            PreparedStatement ps4 = conn.prepareStatement(archiveComment);
            ps4.setInt(1, id);
            ps4.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try{
                    conn.close();
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    public boolean setHasComment(StudentListingBean listing) throws SQLException {
        String sql = "UPDATE student_listing SET has_comment = true where booked_id = ?";
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
       
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, listing.getBooked_id());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Boolean addComment(StudentListingBean listing, int rating, String comment_content) throws SQLException{
        loadClass(); // Load MySQL driver
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String userSubjectInsertSQL = "INSERT INTO comments (booked_id, listing_id, rating, comment_content) VALUES (?, ?, ?, ?)";
            PreparedStatement userSubjectPs = conn.prepareStatement(userSubjectInsertSQL);
            userSubjectPs.setInt(1, listing.getBooked_id());
            userSubjectPs.setInt(2, listing.getListing_id());
            userSubjectPs.setInt(3, rating);
            userSubjectPs.setString(4, comment_content);
            userSubjectPs.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        return true;
    }
    public ArrayList<Comment> getComments(int tutor_id) throws SQLException{
        ArrayList<Comment> comments = new ArrayList<>();
        String query = "SELECT c.* " +
                        "FROM COMMENTS c " +
                        "JOIN LISTING l ON c.listing_id = l.listing_id " +
                        "WHERE l.tutor_id = ? AND c.is_deleted = false";
        try{
            Connection conn = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, tutor_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setBooked_id(rs.getInt("booked_id"));
                StudentListingBean studentlisting = getStudentListing(rs.getInt("booked_id"));
                comment.setStudentlisting(studentlisting);
                comment.setListing_id(rs.getInt("listing_id"));
                comment.setComment_id(rs.getInt("comment_id"));
                comment.setComment_content(rs.getString("comment_content"));
                comment.setRating(rs.getInt("rating"));
                comments.add(comment);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return comments;
    }
    public AdminUser getAdminUser(int id) throws SQLException {
        loadClass(); 
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);


            String sql = "SELECT * FROM adminuser WHERE admin_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                AdminUser a = new AdminUser();
                int userId = rs.getInt("admin_id");
                a.setId(userId);
                a.setEmail(rs.getString("email"));
                a.setPassword(rs.getString("password"));
                

   
                return a;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                 if (conn != null) {
                     conn.close();
                 }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
           
        }
        return null;
    }
    
    public ArrayList<AdminUser> getAdminUsers() throws SQLException {
        loadClass(); // Load MySQL driver
        Connection conn = null;
        ArrayList<AdminUser> AdminList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pass);

            
            String sql = "SELECT * FROM adminuser";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AdminUser a = new AdminUser();
                int adminId = rs.getInt("admin_id");
                a.setId(adminId);
                a.setEmail(rs.getString("email"));
                a.setPassword(rs.getString("password"));

                AdminList.add(a);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                 if (conn != null) {
                     conn.close();
                 }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
           
        }
        return AdminList;
    }
    
    
    public void insertReports(int adminId, String[] reports, String actionType) throws SQLException{
      Connection conn = null;
      PreparedStatement stmt = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
            
            
            String sql = "INSERT INTO pending_actions(admin_id, profile_id, action_type, action_details, status) VALUES (?, ?, ?, ?, 'pending')";

            stmt = conn.prepareStatement(sql);
            for (String profileId : reports) {
                stmt.setInt(1, adminId);
                stmt.setInt(2, Integer.parseInt(profileId));
                stmt.setString(3, actionType);
                stmt.setString(4, "Request to " + actionType + " ID: " + profileId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow exception to be handled in the servlet
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
        
    public AdminRequestBean getRequest(int action_id) throws SQLException {
        loadClass(); // Load MySQL driver
        Connection conn = null;
        AdminRequestBean req = new AdminRequestBean();
        try {
            conn = DriverManager.getConnection(url, user, pass);

            // First, get the main user data
            String sql = "SELECT * FROM pending_actions WHERE action_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, action_id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                
                int userId = rs.getInt("action_id");
                req.setActionId(userId);
                req.setAdminId(rs.getInt("admin_id"));
                req.setProfileId(rs.getInt("profile_id"));
                User user = getUser(rs.getInt("profile_id"));
                req.setUser(user);
                req.setActionType(rs.getString("action_type"));
                req.setStatus(rs.getString("status"));


            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                 if (conn != null) {
                     conn.close();
                 }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
           
        }
        return req;
    }
    public ArrayList<AdminRequestBean> getRequests() throws SQLException {
        loadClass(); // Load MySQL driver
        Connection conn = null;
        ArrayList<AdminRequestBean> reqList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pass);

            // First, get the main user data
            String sql = "SELECT * FROM pending_actions WHERE status = 'pending'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AdminRequestBean req = new AdminRequestBean();
                int userId = rs.getInt("action_id");
                req.setActionId(userId);
                req.setAdminId(rs.getInt("admin_id"));
                req.setProfileId(rs.getInt("profile_id"));
                User user = getUser(rs.getInt("profile_id"));
                req.setUser(user);
                req.setActionType(rs.getString("action_type"));
                req.setStatus(rs.getString("status"));


                reqList.add(req);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                 if (conn != null) {
                     conn.close();
                 }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
           
        }
        return reqList;
    }
    public ArrayList<AdminRequestBean> getRequestsToApprove(int id) throws SQLException {
        loadClass(); // Load MySQL driver
        Connection conn = null;
        ArrayList<AdminRequestBean> reqList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pass);

            // First, get the main user data
            String sql = "SELECT * FROM pending_actions WHERE status = 'pending' AND admin_id != ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AdminRequestBean req = new AdminRequestBean();
                int userId = rs.getInt("action_id");
                req.setActionId(userId);
                req.setAdminId(rs.getInt("admin_id"));
                req.setProfileId(rs.getInt("profile_id"));
                User user = getUser(rs.getInt("profile_id"));
                req.setUser(user);
                req.setActionType(rs.getString("action_type"));
                req.setStatus(rs.getString("status"));


                reqList.add(req);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                 if (conn != null) {
                     conn.close();
                 }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
           
        }
        return reqList;
    }
    public void updateRequestStatus(int  actionId, String status) throws SQLException{
        loadClass(); // Load MySQL driver

        
        String sql = "UPDATE pending_actions SET status = ? WHERE action_id = ?;";
        
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, actionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteComment(String commentid) throws SQLException{
        loadClass(); // Load MySQL driver
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String sql = "UPDATE comments SET is_deleted = TRUE WHERE comment_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, commentid);
   
            ps.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    public void deleteListing(int listingid) throws SQLException{
        loadClass(); // Load MySQL driver
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String sql = "UPDATE listing SET is_deleted = TRUE WHERE listing_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, listingid);  
            ps.executeUpdate();
            String sql2 = "UPDATE student_listing SET is_deleted = TRUE WHERE listing_id = ? AND is_complete = false";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, listingid);  
            ps2.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    public User loginUser(String email, String password) throws SQLException {
        loadClass(); // Load MySQL driver
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
            // First, get the main user data
            String sql = "SELECT * FROM user WHERE email = ? AND password = ? AND is_deleted = false";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, encrypt(password));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                int userId = rs.getInt("id");
                user.setId(userId);
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAboutme(rs.getString("about_me"));
                user.setIsTutor(rs.getBoolean("is_tutor"));
                byte[] profileImage = rs.getBytes("profile_image");

                user.setProfileImage(profileImage);
                // Retrieve education for the user
                user.setSubject(getUserSubjects(userId));

                // Retrieve skills for the user
                user.setSkills(getUserSkills(userId));

                // Retrieve awards for the user
                user.setAwards(getUserAwards(userId));

                // Retrieve languages for the user
                user.setLanguages(getUserLanguages(userId));
                return user;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                 if (conn != null) {
                     conn.close();
                 }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
           
        }
        return null;
    }
    public AdminUser loginAdmin(String email, String password) throws SQLException {
        loadClass(); 
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);


            String sql = "SELECT * FROM adminuser WHERE email = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, encrypt(password));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                AdminUser a = new AdminUser();
                int userId = rs.getInt("admin_id");
                a.setId(userId);
                a.setEmail(rs.getString("email"));
                a.setPassword(rs.getString("password"));
                

   
                return a;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                 if (conn != null) {
                     conn.close();
                 }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
           
        }
        return null;
    }
    public ArrayList<User> searchUsers(String searchQuery) throws SQLException {
    loadClass(); // Load MySQL driver
    Connection conn = null;
    ArrayList<User> searchResults = new ArrayList<>();
    
    try {
        conn = DriverManager.getConnection(url, user, pass);
        String sql = "SELECT * FROM user WHERE LOWER(CONCAT(first_name, ' ', last_name)) LIKE LOWER(?) AND is_deleted = false;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + searchQuery.trim() + "%"); // Match the query against the full name (first + last)
        
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
        User user = new User();
            int userId = rs.getInt("id");
            user.setId(userId);
            user.setFirst_name(rs.getString("first_name"));
            user.setLast_name(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setAboutme(rs.getString("about_me"));
            user.setIsTutor(rs.getBoolean("is_tutor"));
            byte[] profileImage = rs.getBytes("profile_image");

            user.setProfileImage(profileImage);
            // Retrieve education for the user
            user.setSubject(getUserSubjects(userId));

            // Retrieve skills for the user
            user.setSkills(getUserSkills(userId));

            // Retrieve awards for the user
            user.setAwards(getUserAwards(userId));

            // Retrieve languages for the user
            user.setLanguages(getUserLanguages(userId));
            searchResults.add(user);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }    
    return searchResults;
}
    public boolean addStudentListing(int listing_id, String date, int student_id) throws SQLException {
        String query = "INSERT INTO student_listing (listing_id, booking_date, student_id, is_pending) "
                     + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Setting parameters
            ps.setInt(1, listing_id);
            ps.setString(2, date); // Assuming you have a method to get payment scheme ID
            ps.setInt(3, student_id);
            ps.setBoolean(4, true);
            // Execute update
            int rowsAffected = ps.executeUpdate();
            conn.close();
            return rowsAffected > 0; // Return true if the insert was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<StudentListingBean> getStudentListings(int listing_id) throws SQLException {
        ArrayList<StudentListingBean> studentListings = new ArrayList<>();
        String sql = "SELECT * FROM student_listing WHERE listing_id = ? AND is_deleted = false";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, listing_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StudentListingBean studentListing = new StudentListingBean();

                    // Fields from STUDENT_LISTING
                    studentListing.setBooked_id(rs.getInt("booked_id"));
                    studentListing.setBooking_date(rs.getString("booking_date"));
                    studentListing.setIsPending(rs.getBoolean("is_pending"));
                    studentListing.setIsConfirmed(rs.getBoolean("is_confirmed"));
                    studentListing.setIsComplete(rs.getBoolean("is_complete"));
                    studentListing.setIsPaid(rs.getBoolean("is_paid"));
                    studentListing.setHasComment(rs.getBoolean("has_comment"));
                    studentListing.setReferencenumber(rs.getString("payment_reference"));
                    studentListing.setRejected_payment(rs.getString("rejected_payment"));
                    studentListing.setStudent(getUser(rs.getInt("student_id")));
                    // Fields from LISTING
                    ListingBean listing = getListing(listing_id);
                    studentListing.setListing_id(listing_id);
                    studentListing.setDate(listing.getDate()); // From listing
                    studentListing.setSubject(listing.getSubject());
                    studentListing.setPrice(listing.getPrice());
                    studentListing.setNumber(listing.getNumber());   
                    studentListing.setMop(listing.getMop());
                    studentListing.setTutor(listing.getTutor());


                    studentListings.add(studentListing);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return studentListings;
    }
    public ArrayList<StudentListingBean> getStudentListingsByTutor(int tutor_id) throws SQLException {
        ArrayList<StudentListingBean> studentListings = new ArrayList<>();
        String sql = "SELECT sl.* FROM student_listing sl\n" +
                        "INNER JOIN listing l on sl.listing_id = l.listing_id\n" +
                        "WHERE l.tutor_id = ? AND sl.is_deleted = false";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tutor_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StudentListingBean studentListing = new StudentListingBean();

                    // Fields from STUDENT_LISTING
                    studentListing.setBooked_id(rs.getInt("booked_id"));
                    studentListing.setBooking_date(rs.getString("booking_date"));
                    studentListing.setIsPending(rs.getBoolean("is_pending"));
                    studentListing.setIsConfirmed(rs.getBoolean("is_confirmed"));
                    studentListing.setIsComplete(rs.getBoolean("is_complete"));
                    studentListing.setIsPaid(rs.getBoolean("is_paid"));
                    studentListing.setHasComment(rs.getBoolean("has_comment"));
                    studentListing.setReferencenumber(rs.getString("payment_reference"));
                    studentListing.setRejected_payment(rs.getString("rejected_payment"));
                    studentListing.setStudent(getUser(rs.getInt("student_id")));
                    // Fields from LISTING
                    int listing_id = rs.getInt("listing_id");
                    ListingBean listing = getListing(listing_id);
                    studentListing.setListing_id(listing_id);
                    studentListing.setDate(listing.getDate()); // From listing
                    studentListing.setSubject(listing.getSubject());
                    studentListing.setPrice(listing.getPrice());
                    studentListing.setNumber(listing.getNumber());   
                    studentListing.setMop(listing.getMop());
                    studentListing.setTutor(listing.getTutor());


                    studentListings.add(studentListing);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return studentListings;
    }
    public ArrayList<StudentListingBean> getUserSessions(int student_id) throws SQLException {
        ArrayList<StudentListingBean> studentListings = new ArrayList<>();
        String sql = "SELECT * FROM student_listing WHERE student_id = ? AND is_deleted = false";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, student_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StudentListingBean studentListing = new StudentListingBean();

                    // Fields from STUDENT_LISTING
                    studentListing.setBooked_id(rs.getInt("booked_id"));
                    studentListing.setBooking_date(rs.getString("booking_date"));
                    studentListing.setIsPending(rs.getBoolean("is_pending"));
                    studentListing.setIsConfirmed(rs.getBoolean("is_confirmed"));
                    studentListing.setIsComplete(rs.getBoolean("is_complete"));
                    studentListing.setIsPaid(rs.getBoolean("is_paid"));
                    studentListing.setHasComment(rs.getBoolean("has_comment"));
                    studentListing.setReferencenumber(rs.getString("payment_reference"));
                    studentListing.setRejected_payment(rs.getString("rejected_payment"));
                    studentListing.setStudent(getUser(rs.getInt("student_id")));
                    // Fields from LISTING
                    ListingBean listing = getListing(rs.getInt("listing_id"));
                    studentListing.setListing_id(rs.getInt("listing_id"));
                    studentListing.setDate(listing.getDate()); // From listing
                    studentListing.setSubject(listing.getSubject());
                    studentListing.setPrice(listing.getPrice());
                    studentListing.setNumber(listing.getNumber());   
                    studentListing.setMop(listing.getMop());
                    studentListing.setTutor(listing.getTutor());

                    studentListings.add(studentListing);
                    System.out.println("Added User Session");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return studentListings;
    }
    public String encrypt(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");             
            byte[] hashBytes = digest.digest(password.getBytes());          
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: SHA-256 algorithm not available", e);
        }
    }
    public void saveImageFromBlob(Blob imageBlob, File file) throws IOException {
        try (InputStream inputStream = imageBlob.getBinaryStream();
            FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            } 
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    public Blob getImageBlob(int id) throws SQLException {
        Blob imageBlob = null;
        String query = "SELECT profile_image FROM user WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    imageBlob = rs.getBlob("profile_image");
                }
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return imageBlob;
    }
    public Integer generateRandom() {
        Integer random = (int)(Math.random()*100);
        return random;
    }
    public void setTutorFalse(int id) throws SQLException{
        loadClass(); // Load MySQL driver
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, pass);
            String sql = "UPDATE user SET is_tutor = false WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
   
            ps.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
}
