package com.obss.Model.Services.Interfaces;

/**
 * Created by arnold on 7/21/2017.
 */
public interface ApplicationService {

    public void updateApplicationStatusByAccountId(int adCode, int accountId, int status);

    public void updateApplicationStatusByEmail(int adCode, String email, int status);
}
