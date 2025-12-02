/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.model;
import java.time.LocalDate;

import java.io.Serializable;

/**
 *
 * @author Hawy
 */
public class ListingBean implements Serializable {
    private int id;
    private String date;
    private String recur;
    private String subject;
    private Integer price;   
    private String number;
    private User tutor;
    private String mop;
    private Boolean isDeleted;

    private String referencenumber;
    private User student;
    private Boolean isPending;
    private Boolean isConfirmed;
    private Boolean isComplete;
    private Boolean isPaid;
    private Boolean hasComment;
    
   

    public String getRecur() {
        return recur;
    }

    public void setRecur(String recur) {
        this.recur = recur;
    }
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    
    public String getReferencenumber() {
        return referencenumber;
    }

    public void setReferencenumber(String referencenumber) {
        this.referencenumber = referencenumber;
    }
    public Boolean getHasComment() {
        return hasComment;
    }

    public void setHasComment(Boolean hasComment) {
        this.hasComment = hasComment;
    }
    public Boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Boolean isComplete) {
        this.isComplete = isComplete;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
    
    public String getMop() {
        return mop;
    }

    public void setMop(String mop) {
        this.mop = mop;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    
    
    public ListingBean(String date, String subject, Integer price, User tutor, String mop, String number, String recur) {
        this.date = date;
        this.tutor = tutor;
        this.subject = subject;
        this.price = price;
        this.isPending = false;
        this.isConfirmed = false;
        this.mop = mop;
        this.number = number;
        this.recur = recur;
    }
    public ListingBean(){
    
    }

    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public User getTutor() {
        return tutor;
    }

    public void setTutor(User tutor) {
        this.tutor = tutor;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getIsPending() {
        return isPending;
    }

    public void setIsPending(Boolean isPending) {
        this.isPending = isPending;
    }
    public LocalDate getCurrentDate(){
        LocalDate currentDate = LocalDate.now();
        return currentDate;
    }

}
