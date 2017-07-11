package com.obss.Model.Jpa;

import javax.persistence.*;

/**
 * Created by arnold on 7/10/2017.
 */
@Entity()
public class User {


    @Id
    private String userId;
    @Column(unique = true)
    private String email;
    private   String password;
     private   String firstName;
    private String lastName;

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




    public User(String useId,String email,String firstName,String lastName,String password) {
        this.email=email;
        this.password=password;
        this.userId=useId;
        this.firstName=firstName;
        this.lastName=lastName;
    }

    public User(){

    }

    public String getUserId() {
        return userId;
    }


    public String getEmail() {
        return this.email;
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
    public void setUserId(String userId) {
        this.userId = userId;
    }


}
