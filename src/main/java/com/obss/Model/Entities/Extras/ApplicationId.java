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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationId that = (ApplicationId) o;

        if (this.advert.getAdCode() != that.advert.getAdCode()) return false;
        if (this.account.getAccountId() != that.account.getAccountId()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = this.advert.getAdCode();
        result = 31 * result + this.account.getAccountId();
        return result;
    }
}