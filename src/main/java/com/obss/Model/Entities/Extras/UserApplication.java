package com.obss.Model.Entities.Extras;

import com.obss.Model.Entities.Skill;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * Created by arnold on 7/21/2017.
 */
public class UserApplication {

    private int accountId;
    private String fullName;
    private String imageUrl;
    private int status;
    private Timestamp applicationDate;
    private Set<Skill> skills;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Timestamp applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

}
