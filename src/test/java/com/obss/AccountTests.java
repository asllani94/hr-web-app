package com.obss;

import com.obss.Model.Jpa.*;
import com.obss.Model.Jpa.Extras.ApplicationStatus;
import com.obss.Model.Rep.AccountRepository;
import com.obss.Model.Rep.AdvertRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountTests {


    @Autowired
    AccountRepository accountRepository;


    @Autowired
    AdvertRepository advertRepository;


    @Test
    @Transactional
    public void testAccountInsert(){
        Account account=
                new Account("asllani94@gmail.com","Arnold","Asllani","123456");
        accountRepository.saveAndFlush(account);

        Account retrieved=accountRepository.findByEmail(account.getEmail());

        Assert.assertNotNull(retrieved);
        Assert.assertEquals(account,retrieved);


    }

    @Test
    @Transactional
    public void testAccountInsertWithBlackList(){
        Account account=
                new Account("asllani94@gmail.com","Arnold","Asllani","123456");
        Blacklist bl=new Blacklist();
        bl.setReason("Blacklisted for bad behaviour");
        account.setBlacklist(bl);
        accountRepository.save(account);

        Account retrieved=accountRepository.findByEmail(account.getEmail());

        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(retrieved.getBlacklist());
        Assert.assertEquals(account,retrieved);
        Assert.assertEquals(bl,retrieved.getBlacklist());

        System.out.println(retrieved.getBlacklist().getReason());


    }


    @Test
    @Transactional
    public void testAccountInsertWithSkillList(){
        Account account=
                new Account("asllani94@gmail.com","Arnold","Asllani","123456");
        Blacklist bl=new Blacklist();
        bl.setReason("Blacklisted for bad behaviour");
        account.setBlacklist(bl);


        Skill first_skil=new Skill();
        first_skil.setSkillName("MySql");

        Skill second_skil=new Skill();
        second_skil.setSkillName("Java");

        account.addSkill(first_skil);
        account.addSkill(second_skil);

        accountRepository.save(account);


        Account retrieved=accountRepository.findByEmail(account.getEmail());

        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(retrieved.getBlacklist());
        Assert.assertEquals(account,retrieved);
        Assert.assertEquals(bl,retrieved.getBlacklist());
        Assert.assertTrue(retrieved.getSkills().size()>1);

        for (Skill skill:retrieved.getSkills()){
            System.out.println(skill.getSkillName());
        }



    }


    @Test
    @Transactional
    public void testAccountInsertWithAccountDetails(){
        Account account=
                new Account("asllani94@gmail.com","Arnold","Asllani","123456");
        Blacklist bl=new Blacklist();
        bl.setReason("Blacklisted for bad behaviour");
        account.setBlacklist(bl);

        Skill first_skil=new Skill();
        first_skil.setSkillName("MySql");

        Skill second_skil=new Skill();
        second_skil.setSkillName("Java");

        account.addSkill(first_skil);
        account.addSkill(second_skil);

        AccountDetails accountDetails=new AccountDetails();
        accountDetails.setTitle("Expert");
        accountDetails.setEducation("ITU");
        accountDetails.setExperience("No experience");
        accountDetails.setAddress("TURKEy");

        account.setAccountDetails(accountDetails);

        accountRepository.save(account);


        Account retrieved=accountRepository.findByEmail(account.getEmail());

        Assert.assertNotNull(retrieved);
        Assert.assertNotNull(retrieved.getBlacklist());
        Assert.assertEquals(account,retrieved);
        Assert.assertEquals(bl,retrieved.getBlacklist());
        Assert.assertTrue(retrieved.getSkills().size()>1);
        Assert.assertNotNull(retrieved.getAccountDetails());
        Assert.assertEquals(accountDetails,retrieved.getAccountDetails());


    }


    @Test
    @Transactional
    public void  testWithApplication(){
        Account account=
                new Account("asllani94@gmail.com","Arnold","Asllani","123456");

        Advert advert=new Advert();
        advert.setAdHeader("Empty");

        advert.setAdDescription("asdfasdf");
        advert.setAdStatus(false);
        advert.setAdActivationTime(new Timestamp(5000));
        advert.setAdDeadlineTime(new Timestamp(5000));

        Advert advert1=new Advert();
        advert1.setAdHeader("Not Empty");

        advert1.setAdDescription("gagahah");
        advert1.setAdStatus(false);
        advert1.setAdActivationTime(new Timestamp(5000));
        advert1.setAdDeadlineTime(new Timestamp(5000));
        advertRepository.saveAndFlush(advert);

        account.applyToAdvert(advert);

        accountRepository.saveAndFlush(account);



        Account retrieved=accountRepository.findByEmail("asllani94@gmail.com");


        Assert.assertNotNull(retrieved);
        Assert.assertTrue(account.getApplications().size()>=1);
        Assert.assertEquals(account,retrieved);


        for(Application app: retrieved.getApplications()){
            app.setStatus(ApplicationStatus.REJECTED);
        }

        accountRepository.saveAndFlush(retrieved);


    }


    @Test
    @Transactional
    public  void removeBlackListFromAccount(){
        Account account=
                new Account("asllani94@gmail.com","Arnold","Asllani","123456");
        Blacklist blacklist=new Blacklist();
        blacklist.setReason("Bad Behaviour");
        account.setBlacklist(blacklist);
        accountRepository.saveAndFlush(account);


        Account retrieved=accountRepository.findByEmail("asllani94@gmail.com");
        retrieved.setBlacklist(null);

        accountRepository.saveAndFlush(retrieved);

        Account updated=accountRepository.findByEmail("asllani94@gmail.com");

        Assert.assertNotNull(updated);
        Assert.assertNull(updated.getBlacklist());


    }

}
