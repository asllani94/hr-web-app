package com.obss.Model.Jpa;

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
    @OneToMany(mappedBy = "advert")
    private Set<Application> applications=new HashSet<Application>( );

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
        Advert address = (Advert) o;
        return Objects.equals( adHeader, address.adHeader ) &&
                Objects.equals( adDescription, address.adDescription ) &&
                Objects.equals( adStatus, address.adStatus );
    }

    @Override
    public int hashCode() {
        return Objects.hash( adHeader,adDescription, adStatus );
    }
    */
}
