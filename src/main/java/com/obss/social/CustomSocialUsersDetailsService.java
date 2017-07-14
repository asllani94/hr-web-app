package com.obss.social;

import com.obss.Model.Dao.AccountDao;
import com.obss.Model.Jpa.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by arnold on 7/10/2017.
 */
@Service
@Primary
public class CustomSocialUsersDetailsService implements SocialUserDetailsService {

    @Autowired
    private AccountDao accountDao;

    //when signin in with social, this method checks whenever it finds the user locally(user already registered)
    @Override
    public SocialUserDetails loadUserByUserId(String email) throws UsernameNotFoundException, DataAccessException {

        System.out.println("SocialUserDetailsServiceCalled");
        Account user= accountDao.loadUserByEmail(email);
        System.out.println("Found user  "+user.getEmail());


        return new CustomSocialUserDetails(user);
    }

}
