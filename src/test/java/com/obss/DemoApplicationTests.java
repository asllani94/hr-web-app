package com.obss;

import com.obss.Model.Jpa.Account;
import com.obss.Model.Jpa.Advert;
import com.obss.Model.Jpa.Application;
import com.obss.Model.Jpa.ApplicationStatus;
import com.obss.Model.Rep.AccountRepository;
import com.obss.Model.Rep.AdvertRepository;
import com.obss.Model.Rep.ApplicationRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {


    @Autowired
    AdvertRepository advertRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @PersistenceContext
    EntityManager entityManager;


    @Before
    @Rollback(false)
    @Transactional
    public void testAccountwithAdvertOnJoinApplication(){
        Account account=new Account("asllani94@gmail.com","Arnold","Asllani","123456");
        Account account1=new Account("asllani@mai.com","Rita","Ora","ckemi23");

        Advert advert= new Advert();
        advert.setAdHeader("JAVA DEVELOPER");
        advert.setAdDescription("WE ARE LOOKING FOR EXPERIENCED JAVA DEVELOPER");
        advert.setAdActivationTime(new Timestamp(new Date().getTime()));
        advert.setAdDeadlineTime(new Timestamp(new Date().getTime()));
        advert.setAdStatus(false);

        Advert advert1= new Advert();
        advert1.setAdHeader("C++ DEVELOPER");
        advert1.setAdDescription("WE ARE LOOKING FOR EXPERIENCED C++ DEVELOPER");
        advert1.setAdActivationTime(new Timestamp(new Date().getTime()));
        advert1.setAdDeadlineTime(new Timestamp(new Date().getTime()));
        advert1.setAdStatus(false);

        accountRepository.save(account);
        accountRepository.save(account1);

        advertRepository.save(advert);
        advertRepository.save(advert1);

    }


    @Test
    @Rollback(false)
    @Transactional
    public  void applyWithAccountEmailAndAdCode(){
    String email="asllani94@gmail.com";
    int adCode=1;
    int application_status= ApplicationStatus.ON_PROCESS;

    Application application=new Application(accountRepository.findByEmail(email),advertRepository.findOne(adCode),application_status);
    applicationRepository.save(application);

    Assert.assertEquals(application.getAccount().getEmail(),email);
    Assert.assertEquals(application.getAdvert().getAdCode(),adCode);
    Assert.assertTrue(application.getStatus()==application_status);

        Advert ado=advertRepository.findOne(1);

        for (Application app:ado.getApplications()){
            System.out.println(app.getAccount().getFirstName());
            System.out.println(app.getAccount().getLastName());
            System.out.println(app.getAccount().getEmail());
        }



    }


}
