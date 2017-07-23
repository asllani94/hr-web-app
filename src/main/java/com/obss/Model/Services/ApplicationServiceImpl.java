package com.obss.Model.Services;

import com.obss.Model.Entities.Application;
import com.obss.Model.Entities.Extras.ApplicationStatus;
import com.obss.Model.Repositories.AccountRepository;
import com.obss.Model.Repositories.ApplicationRepository;
import com.obss.Model.Services.Interfaces.ApplicationService;
import com.obss.Utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.ParseException;

/**
 * Created by arnold on 7/21/2017.
 * Application Service class. Uses entity manager to update database table.
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {


    @PersistenceContext
    EntityManager entityManager;


    @Override
    @Transactional
    public void newApplication(int adCode, int accountId) {
        Timestamp applicationDate = null;
        try {
            DateUtil.getCurrentTimestamp();
            ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int status = ApplicationStatus.ON_PROCESS;
        String query = "insert into application values(CURRENT_TIMESTAMP," + status + "," + accountId + "," + adCode + ")";
        entityManager.createNativeQuery(query).executeUpdate();
    }

    @Override
    @Transactional
    public void updateApplicationStatusByAccountId(int adCode, int accountId, int status) {
        String query = "UPDATE application SET status=" + status + " WHERE  account_id=" + accountId + " AND  ad_code=" + adCode;
        entityManager.createNativeQuery(query).executeUpdate();
    }

    @Override
    @Transactional
    public void deleteApplicationByAccountIdAndAdCode(int adCode, int accountId) {
        String query = "DELETE FROM application WHERE ad_code=" + adCode + " AND account_id=" + accountId;
        entityManager.createNativeQuery(query).executeUpdate();
    }


}
