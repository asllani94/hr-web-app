package com.obss.Model.Jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by arnold on 7/13/2017.
 */
@Entity
public class Application   {

    @EmbeddedId
    private ApplicationId id;

    @ManyToOne
    @JoinColumn(name = "fk_account", insertable = false, updatable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "fk_advert", insertable = false, updatable = false)
    private Advert advert;


    @Column(name = "status")
    private int status;


    public Application() {
    }

    public Application(Account account, Advert advert, int status) {
        this.id=new ApplicationId(account.getAccountId(),advert.getAdCode());
        this.account = account;
        this.advert = advert;
        this.status = status;
        advert.getApplications().add(this);
        account.getApplications().add(this);
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Application that = (Application) o;
        return Objects.equals(account, that.account) &&
                Objects.equals(advert, that.advert);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, advert);
    }
    */
}
