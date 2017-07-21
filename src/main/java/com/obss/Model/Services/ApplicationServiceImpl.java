package com.obss.Model.Services;

import com.obss.Model.Entities.Application;
import com.obss.Model.Repositories.AccountRepository;
import com.obss.Model.Repositories.ApplicationRepository;
import com.obss.Model.Services.Interfaces.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by arnold on 7/21/2017.
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {


    @PersistenceContext
    EntityManager entityManager;


    @Transactional
    public void newApplication(int adCode, int accountId) {

        String query = "insert into application values(" + 1 + "," + accountId + "," + adCode + ")";
        entityManager.createNativeQuery(query).executeUpdate();


    }

    @Override
    public void updateApplicationStatusByAccountId(int adCode, int accountId, int status) {

    }

    @Override
    public void updateApplicationStatusByEmail(int adCode, String email, int status) {

    }

}
