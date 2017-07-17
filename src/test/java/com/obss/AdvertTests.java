package com.obss;

/**
 * Created by arnold on 7/17/2017.
 */

import com.obss.Model.Entities.*;
import com.obss.Model.Repositories.AdvertRepository;
import com.obss.Model.Repositories.SkillRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AdvertTests {


    @Autowired
    AdvertRepository advertRepository;

    @Autowired
    SkillRepository skillRepository;


    @Test
    @Transactional
    public void testAdvertInsert(){

       Advert advert=new Advert();
       advert.setAdHeader("test header");
       advert.setAdDescription("test description");
       advert.setAdActivationTime(new Timestamp(1000));
       advert.setAdDeadlineTime(new Timestamp(5000));
       advert.setAdStatus(false);


       advertRepository.save(advert);
       Advert retrieved=advertRepository.findOne(advert.getAdCode());
       Assert.assertNotNull(retrieved);
       Assert.assertEquals(advert,retrieved);


    }


    @Test
    @Transactional
    public void testAdvertWithSkill(){
        Advert advert=new Advert();
        advert.setAdHeader("test header");
        advert.setAdDescription("test description");
        advert.setAdActivationTime(new Timestamp(1000));
        advert.setAdDeadlineTime(new Timestamp(5000));
        advert.setAdStatus(false);

        Skill first_skil=new Skill();
        first_skil.setSkillName("MySql");

        Skill second_skil=new Skill();
        second_skil.setSkillName("Java");

        advert.addSkill(first_skil);
        advert.addSkill(second_skil);


        advertRepository.saveAndFlush(advert);
        Advert retrieved=advertRepository.findOne(advert.getAdCode());
        Assert.assertNotNull(retrieved);
        Assert.assertEquals(advert,retrieved);
        Assert.assertTrue(retrieved.getSkills().size()>1);
        for(Skill s: retrieved.getSkills()){
            System.out.println(s.getSkillName());
        }


    }

    @Test
    @Transactional
    public void testUpdateAdvert(){

       Advert advert=new Advert();
       advert.setAdHeader("test header");
       advert.setAdDescription("test description");
       advert.setAdActivationTime(new Timestamp(1000));
       advert.setAdDeadlineTime(new Timestamp(5000));
       advert.setAdStatus(false);


       advertRepository.save(advert);
       Advert retrieved=advertRepository.findOne(advert.getAdCode());

       Assert.assertNotNull(retrieved);
       Assert.assertEquals(advert,retrieved);

       retrieved.setAdHeader("Real Header");
       advertRepository.saveAndFlush(retrieved);


        System.out.println();

    }




    @Test
    @Transactional
    public void testWithExistingSkillList(){
        Skill first_skil=new Skill();
        first_skil.setSkillName("MySql");

        Skill second_skil=new Skill();
        second_skil.setSkillName("Java");

        skillRepository.saveAndFlush(first_skil);
        skillRepository.saveAndFlush(second_skil);

        List<Skill> availableSkills=skillRepository.findAll();

        Assert.assertNotNull(availableSkills);
        Assert.assertTrue(availableSkills.size()>1);

        Advert advert=new Advert();
        advert.setAdHeader("test header");
        advert.setAdDescription("test description");
        advert.setAdActivationTime(new Timestamp(1000));
        advert.setAdDeadlineTime(new Timestamp(5000));
        advert.setAdStatus(false);

        for ( Skill sk:availableSkills) {
            advert.addSkill(sk);
        }

        advertRepository.saveAndFlush(advert);



    }


    @Test
    @Transactional
    public void testDeleteAdvert(){


        Advert advert=new Advert();
        advert.setAdHeader("test header");
        advert.setAdDescription("test description");
        advert.setAdActivationTime(new Timestamp(1000));
        advert.setAdDeadlineTime(new Timestamp(5000));
        advert.setAdStatus(false);

        advertRepository.saveAndFlush(advert);

        int ad_code=advert.getAdCode();

        Assert.assertNotNull(advertRepository.findOne(ad_code));



        //advertRepository.deleteByAdCode(ad_code);
        advertRepository.delete(ad_code);

        Assert.assertNull(advertRepository.findOne(ad_code));




    }

}