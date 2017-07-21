package com.obss;

import com.obss.Model.Entities.*;
import com.obss.Model.Entities.Extras.ApplicationId;
import com.obss.Model.Entities.Extras.ApplicationStatus;
import com.obss.Model.Repositories.AccountRepository;
import com.obss.Model.Repositories.AdvertRepository;
import com.obss.Model.Repositories.ApplicationRepository;
import com.obss.Model.Repositories.SkillRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {


    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    AdvertRepository advertRepository;

    @Autowired
    AccountRepository accountRepository;


    @Test
    @Rollback(false)
    @Transactional
    public void testUpdateApplication() {

        Account account =
                new Account("asllani94@gmail.com", "Arnold", "Asllani", "123456");

        Advert advert = new Advert();
        advert.setAdHeader("Empty");

        advert.setAdDescription("asdfasdf");
        advert.setAdStatus(false);
        advert.setAdActivationTime(new Timestamp(5000));
        advert.setAdDeadlineTime(new Timestamp(5000));

        advertRepository.saveAndFlush(advert);

        account.applyToAdvert(advert);

        accountRepository.saveAndFlush(account);

        applicationRepository.updateApplicationStatus(1, 1, ApplicationStatus.DELETED);


        System.out.println("asfasdfsdf");
    }


}

