package com.obss.social;

/**
 * Created by arnold on 7/10/2017.
 */
import com.obss.Model.Dao.AccountDao;
import com.obss.Model.Jpa.Account;
import com.obss.Model.Jpa.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomConnectionSignUp implements ConnectionSignUp {

    private final AccountDao usersDao;


    @Autowired
    public CustomConnectionSignUp(AccountDao usersDao) {
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

        usersDao.createUser(user);
        return email;
    }
    private String generateUserPassword(){
        return UUID.randomUUID().toString();

    }

}