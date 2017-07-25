package com.obss.Model.Services.Interfaces;

import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * Created by arnold on 7/21/2017.
 */
@Service
public interface ApplicationService {

    public void updateApplicationStatusByAccountId(int adCode, int accountId, int status);

    public void newApplication(int adCode, int accountId);

    public void deleteApplicationByAccountIdAndAdCode(int adCode, int accountId);

    public void rejectCandidateApplications(int accountId);

    public BigInteger getTotalApplications();
}
