package com.obss.Model.Jpa;

import com.obss.Model.Jpa.Extras.ApplicationStatus;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by arnold on 7/13/2017.
 */
@Entity
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ad_code", updatable = false, nullable = false)
    private int adCode;
    private String adHeader;
    private String adDescription;
    private Timestamp adActivationTime;
    private Timestamp adDeadlineTime;
    private boolean adStatus;



    @OneToMany(fetch = FetchType.LAZY, mappedBy ="pk.advert", cascade =
            {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Set<Application> accounts=new HashSet<Application>( );





    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "advert_skills", joinColumns = @JoinColumn(name = "ad_code"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills=new HashSet<>();



    public  Advert(){}


    public int getAdCode() {
        return adCode;
    }

    public void setAdCode(int adCode) {
        this.adCode = adCode;
    }

    public String getAdHeader() {
        return adHeader;
    }

    public void setAdHeader(String adHeader) {
        this.adHeader = adHeader;
    }

    public String getAdDescription() {
        return adDescription;
    }

    public void setAdDescription(String adDescription) {
        this.adDescription = adDescription;
    }

    public Timestamp getAdActivationTime() {
        return adActivationTime;
    }

    public void setAdActivationTime(Timestamp adActivationTime) {
        this.adActivationTime = adActivationTime;
    }

    public Timestamp getAdDeadlineTime() {
        return adDeadlineTime;
    }

    public void setAdDeadlineTime(Timestamp adDeadlineTime) {
        this.adDeadlineTime = adDeadlineTime;
    }

    public boolean isAdStatus() {
        return adStatus;
    }

    public void setAdStatus(boolean adStatus) {
        this.adStatus = adStatus;
    }

    public Set<Application> getApplications() {
        return accounts;
    }

    public void setApplications(Set<Application> applications) {
        this.accounts = applications;
    }

    public void addAccount (Account account){
     Application application= new Application();
     application.setAccount(account);
     application.setAdvert(this);
     application.setStatus(ApplicationStatus.ON_PROCESS);
     if (accounts==null)
         accounts=new HashSet<>();

     this.accounts.add(application);
     account.getApplications().add(application);


    }

    public void removeApplication (Application application){
        if(this.accounts!=null){
            accounts.remove(application);
        }

    }



    public Set<Skill> getSkills() {
        if (this.skills == null) {
            this.skills = new HashSet<>();
        }
        return this.skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills=skills;
    }

    public void addSkill(Skill skill){
        if(this.skills==null) {
            skills = new HashSet<>();
            skills.add(skill);
        }
        else
            skills.add(skill);

    }


    public  void removeSkill(Skill skill){
        if(skills!=null)
            skills.remove(skill);
    }


}
