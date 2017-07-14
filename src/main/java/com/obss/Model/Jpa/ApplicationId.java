package com.obss.Model.Jpa;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by arnold on 7/14/2017.
 */
@Embeddable
public  class ApplicationId implements Serializable {

    @Column(name = "fk_account")
    protected Integer accountId;

    @Column(name = "fk_advert")
    protected Integer adCode;

    public ApplicationId() {

    }

    public ApplicationId(int accountId, int adCode) {
        this.adCode = adCode;
        this.accountId = accountId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((accountId == null) ? 0 : accountId.hashCode());
        result = prime * result
                + ((adCode == null) ? 0 : adCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        ApplicationId other = ( ApplicationId ) obj;

        if (accountId == null) {
            if (other.accountId != null)
                return false;
        } else if (!accountId.equals(other.accountId))
            return false;

        if (adCode == null) {
            if (other.adCode != null)
                return false;
        } else if (!adCode.equals(other.adCode))
            return false;

        return true;
    }
}