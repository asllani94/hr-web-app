package com.obss.Model.Entities.Extras;

import com.obss.Model.Entities.Account;
import com.obss.Model.Entities.Advert;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by arnold on 7/14/2017.
 *
 *This class is a composite primary key for the Application Table
 */
@Embeddable
public  class ApplicationId implements Serializable {


    private Account account;

    private Advert advert;

    public ApplicationId() {

    }
    @ManyToOne
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @ManyToOne
    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }

    @Override
    public boolean equals(Object o) {
        if (this== o) return true;
        if (o ==null|| getClass() != o.getClass()) return false;

        ApplicationId that = (ApplicationId) o;

        if (account !=null?!account.equals(that.account) : that.account !=null) return false;
        if (advert !=null?!advert.equals(that.advert) : that.advert !=null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = (account !=null? account.hashCode() : 0);
        result =31* result + (advert !=null? advert.hashCode() : 0);
        return result;
    }
}