package com.obss.social;

/**
 * Created by arnold on 7/10/2017.
 */
import com.obss.Model.Dao.UserDao;
import com.obss.Model.Jpa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.linkedin.api.Company;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfileFull;
import org.springframework.social.linkedin.api.impl.LinkedInTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CustomConnectionSignUp implements ConnectionSignUp {

    private final UserDao usersDao;


    @Autowired
    public CustomConnectionSignUp(UserDao usersDao) {
        this.usersDao = usersDao;
    }


    public String execute(Connection<?> connection) {
       //LinkedIn template= (LinkedIn) connection.getApi();

        System.out.println("ConnectionSignUp called");

        UserProfile profile = connection.fetchUserProfile();
      /*  LinkedInProfileFull full=template.profileOperations().getUserProfileFull();
        List<Company> companies=template.companyOperations().getFollowing();*/

        //Unique linkedin user id
         String userId=connection.getKey().getProviderUserId();
        String email=profile.getEmail();
        User user=new User(userId,email,profile.getFirstName(),profile.getLastName(),generateUserPassword());
        usersDao.createUser(user);
        return email;
    }
    private String generateUserPassword(){
        return UUID.randomUUID().toString();

    }

}