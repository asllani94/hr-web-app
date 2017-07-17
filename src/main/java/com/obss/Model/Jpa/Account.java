package com.obss.Model.Jpa;

import com.obss.Model.Jpa.Extras.ApplicationId;
import com.obss.Model.Jpa.Extras.ApplicationStatus;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by arnold on 7/13/2017.
 */
@Entity
public class Account   {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( updatable = false, nullable = false)
    private int accountId;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_detail_id")
    private AccountDetails accountDetails;

    @JoinColumn(name = "blacklist_id")
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Blacklist blacklist;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "account_skills", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills=new HashSet<Skill>();


    @OneToMany(fetch = FetchType.LAZY, mappedBy ="pk.account", cascade =
            {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Set<Application> adverts=new HashSet<Application>();


    public Account(){

    }

    public Account(String email, String firstName, String lastName, String password) {
        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
        this.password=password;
    }


    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public Set<Application> getApplications() {
        return adverts;
    }

    public void setApplications(Set<Application> applications) {
        this.adverts = applications;
    }

    public void applyToAdvert(Advert advert){
      Application application=new Application();
        ApplicationId key=new ApplicationId();
        key.setAccount(this);
        key.setAdvert(advert);
      application.setPk(key);
      application.setStatus(ApplicationStatus.ON_PROCESS);
      if(adverts==null)
          adverts=new HashSet<>();

      this.adverts.add(application);
      advert.getApplications().add(application);

    }

    public void removeApplication (Application application){

        if(this.adverts!=null){
            application.getAdvert().removeApplication(application);
            adverts.remove(application);

        }

    }



    public AccountDetails getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(AccountDetails accountDetails) {
        this.accountDetails = accountDetails;
    }



    public Blacklist getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Blacklist blacklist) {
        this.blacklist = blacklist;
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
