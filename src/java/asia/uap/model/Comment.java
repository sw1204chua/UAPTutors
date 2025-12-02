/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.model;

import java.io.Serializable;

/**
 *
 * @author User
 */
public class Comment implements Serializable{
    private int comment_id;
    private int booked_id;  
    private int listing_id;
    private String comment_content;
    private int rating;
    private boolean is_deleted;
    private StudentListingBean studentlisting;

    public StudentListingBean getStudentlisting() {
        return studentlisting;
    }

    public void setStudentlisting(StudentListingBean studentlisting) {
        this.studentlisting = studentlisting;
    }
    public int getBooked_id() {
        return booked_id;
    }
    
    
    public void setBooked_id(int booked_id) {
        this.booked_id = booked_id;
    }
    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getListing_id() {
        return listing_id;
    }

    public void setListing_id(int listing_id) {
        this.listing_id = listing_id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }
    
}
