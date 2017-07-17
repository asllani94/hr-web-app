package com.obss.social;

/**
 * Created by arnold on 7/10/2017.
 * This class is used to implicity register user if its his first time using web app
 */

import com.obss.Model.Entities.Skill;
import com.obss.Model.Services.AccountServiceImpl;
import com.obss.Model.Entities.Account;
import com.obss.Model.Entities.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomConnectionSignUp implements ConnectionSignUp {

    private final AccountServiceImpl usersDao;


    @Autowired
    public CustomConnectionSignUp(AccountServiceImpl usersDao) {
        this.usersDao = usersDao;
    }


    public String execute(Connection<?> connection) {
       //LinkedIn template= (LinkedIn) connection.getApi();

        System.out.println("ConnectionSignUp called");

        UserProfile profile = connection.fetchUserProfile();
      /*  LinkedInProfileFull full=template.profileOperations().getUserProfileFull();
        List<Company> companies=template.companyOperations().getFollowing();*/

        //Unique linkedin user id
        String email=profile.getEmail();
        Account user=new Account(email,profile.getFirstName(),profile.getLastName(),generateUserPassword());

        //here we get extra info from LinkedIn API (using mock because not approved)
        AccountDetails accountDetails=new AccountDetails();
        accountDetails.setTitle("Software Developer");
        accountDetails.setAddress("Rasimpasa Mah, Kadikoy-Istanbul ");
        accountDetails.setEducation("Istanbul Teknik Universitesi");
        accountDetails.setExperience("3 years of experience");
        user.setAccountDetails(accountDetails);

        Skill skill = new Skill();
        skill.setSkillName("Yazilim Mimari");

        Skill skill_1 = new Skill();
        skill_1.setSkillName("Spring EE");

        user.addSkill(skill);
        user.addSkill(skill_1);

        usersDao.createOrUpdateAccount(user);
        return email;
    }
    private String generateUserPassword(){
        return UUID.randomUUID().toString();

    }

}