package com.obss.Model.Jpa;

import com.obss.Model.Jpa.Extras.ApplicationId;

import javax.persistence.*;
import java.io.Serializable;

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

    @EmbeddedId
    public ApplicationId getPk() {
        return pk;
    }

    public void setPk(ApplicationId pk) {
        this.pk = pk;
    }



    @Column(name = "status")
    private int status;


    public Application() {
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
    @Transient
    public Advert getAdvert(){
        return pk.getAdvert();
    }


    public void setAccount(Account account){
        this.pk.setAccount(account);
    }
    public void setAdvert(Advert advert){
        this.pk.setAdvert(advert);
    }


}
