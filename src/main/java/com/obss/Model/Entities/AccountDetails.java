package com.obss.Model.Entities;

import javax.persistence.*;

/**
 * Created by arnold on 7/16/2017.
 */
@Entity
public class AccountDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int accountDetailsId;
    private String title;
    private String address;
    private  String education;
    private  String experience;

    public AccountDetails(){

    }
    public int getAccountDetailsId() {
        return accountDetailsId;
    }

    public void setAccountDetailsId(int accountDetailsId) {
        this.accountDetailsId = accountDetailsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }


}
