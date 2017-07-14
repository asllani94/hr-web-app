package com.obss.security;

import com.obss.Model.Dao.AccountDao;
import com.obss.Model.Jpa.Account;
import com.obss.social.CustomSocialUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by arnold on 7/10/2017.
 */
public class MyUserDetailsService  implements UserDetailsService{

    //can be  used when signing in with form login( in our case not used)
    @Autowired
    AccountDao accountDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account us= accountDao.loadUserByEmail(email);

        System.out.println("UserDetailsService called");

        if (us==null)
            throw  new UsernameNotFoundException("couldnt find user iin local db");
        return new CustomSocialUserDetails(us);
    }
}
