package com.obss.Model.Entities;

import com.obss.Model.Entities.Extras.ApplicationId;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by arnold on 7/13/2017.
 */
@Entity
@AssociationOverrides({
        @AssociationOverride(name ="pk.account", joinColumns = @JoinColumn(name ="account_id")),
        @AssociationOverride(name ="pk.advert", joinColumns = @JoinColumn(name ="ad_code"))
})
public class Application  implements Serializable {


    @Id
    private ApplicationId pk=new ApplicationId();
    @Column(name = "status")
    private int status;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "application_date", columnDefinition = "")
    private Timestamp applicationDate;

    public Application() {
    }

    @EmbeddedId
    public ApplicationId getPk() {
        return pk;
    }

    public void setPk(ApplicationId pk) {
        this.pk = pk;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    @Transient
    public Account getAccount(){
        return pk.getAccount();
    }

    public void setAccount(Account account) {
        this.pk.setAccount(account);
    }

    @Transient
    public Advert getAdvert(){
        return pk.getAdvert();
    }

    public void setAdvert(Advert advert){
        this.pk.setAdvert(advert);
    }

    public Timestamp getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Timestamp applicationDate) {
        this.applicationDate = applicationDate;
    }


}
