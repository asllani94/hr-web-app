package com.obss.Model.Jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by arnold on 7/13/2017.
 */
@Entity
public class Account   {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id", updatable = false, nullable = false)
    private int accountId;
    private String email;
    private String firstName;
    private String lastName;
    private String password;


    @OneToMany(mappedBy = "account")
    private Set<Application> applications=new HashSet<Application>();

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
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    /*

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Account person = (Account) o;
        return Objects.equals( email, person.email );
    }
    @Override
    public int hashCode() {
        return Objects.hash( email );
    }
    */
}
