/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asia.uap.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Chace
 */
public class User implements Serializable {

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String aboutme;
    private ArrayList<SubjectBean> subject;
    private ArrayList<String> skills;
    private ArrayList<String> awards;
    private ArrayList<String> languages;
    private Boolean isTutor;
    private Boolean imageExists;
    private byte[] profileImage;

    public Boolean getImageExists() {
        return imageExists;
    }

    public void setImageExists(Boolean imageExists) {
        this.imageExists = imageExists;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public Boolean getIsTutor() {
        return isTutor;
    }

    public void setIsTutor(Boolean isTutor) {
        this.isTutor = isTutor;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public ArrayList<SubjectBean> getSubject() {
        return subject;
    }

    public void setSubject(ArrayList<SubjectBean> subject) {
        this.subject = subject;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public ArrayList<String> getAwards() {
        return awards;
    }

    public void setAwards(ArrayList<String> awards) {
        this.awards = awards;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
