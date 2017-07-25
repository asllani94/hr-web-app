package com.obss.social;

/**
 * Created by arnold on 7/10/2017.
 * This class is used to implicity register user if its his first time using web app
 */

import com.obss.Model.Entities.Skill;
import com.obss.Model.Services.AccountServiceImpl;
import com.obss.Model.Entities.Account;
import com.obss.Model.Entities.AccountDetails;
import com.obss.Model.Services.AdvertServiceImpl;
import com.obss.Model.Services.SkillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomConnectionSignUp implements ConnectionSignUp {

    private final AccountServiceImpl accountService;

    private final AdvertServiceImpl advertService;

    @Autowired
    private final SkillServiceImpl skillService;


    @Autowired
    public CustomConnectionSignUp(AccountServiceImpl accountService, AdvertServiceImpl advertService, SkillServiceImpl skillService) {
        this.accountService = accountService;
        this.advertService = advertService;
        this.skillService = skillService;
    }


    public String execute(Connection<?> connection) {

       //LinkedIn template= (LinkedIn) connection.getApi();
        System.out.println("ConnectionSignUp called");
        UserProfile profile = connection.fetchUserProfile();
        String imageUrl = connection.getImageUrl();

        /*  LinkedInProfileFull full=template.profileOperations().getUserProfileFull();
        List<Company> companies=template.companyOperations().getFollowing();*/

        //Unique linkedin user id
        String email=profile.getEmail();
        Account user=new Account(email,profile.getFirstName(),profile.getLastName(),generateUserPassword());
        user.setImageUrl(imageUrl);

        //here we get extra info from LinkedIn API (using mock because app yet not approved)
        AccountDetails accountDetails=new AccountDetails();
        accountDetails.setTitle("Software Developer");
        accountDetails.setAddress("Rasimpasa Mah, Kadikoy-Istanbul ");
        accountDetails.setEducation("Istanbul Teknik Universitesi");
        accountDetails.setExperience("3 years of experience");
        user.setAccountDetails(accountDetails);
        generateRandomSkill(user);

        return email;
    }
    private String generateUserPassword(){
        return UUID.randomUUID().toString();

    }

    //only for testing
    private void generateRandomSkill(Account account) {
        int SKILL_SIZE = 5;
        for (int i = 0; i < SKILL_SIZE; i++) {
            int randomSkillId = (int) (Math.random() * (33 - 1)) + 1;
            Skill skill = skillService.findSkill(randomSkillId);
            account.addSkill(skill);
        }
        accountService.createOrUpdateAccount(account);

    }

}