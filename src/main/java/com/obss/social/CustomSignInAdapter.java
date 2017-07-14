package com.obss.social;

import com.obss.Model.Dao.AccountDao;
import com.obss.Model.Jpa.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by arnold on 7/10/2017.
 */
@Service
public class CustomSignInAdapter implements SignInAdapter {


    private final AccountDao accountDao;

    @Autowired
    public CustomSignInAdapter(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public String signIn(String email, Connection<?> connection, NativeWebRequest request) {
        Account user= accountDao.loadUserByEmail(email);

        System.out.println("SignInAdapter called");
        if (user!=null){
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                user.getEmail(), null,
                                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
                return null;
            }
            else {

                SecurityContextHolder.getContext().setAuthentication(
                        new AnonymousAuthenticationToken(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Arrays.asList(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))));
                return null;
            }

    }

}
