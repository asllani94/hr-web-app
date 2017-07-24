package com.obss.Controllers.Forms;

import javax.validation.constraints.NotNull;

/**
 * Created by arnold on 7/23/2017.
 */
public class BlackListForm {
    @NotNull
    private int accountId;
    @NotNull
    private String reason;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}
