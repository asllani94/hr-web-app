package com.obss.social;

import com.obss.Model.Services.AccountServiceImpl;
import com.obss.Model.Entities.Account;
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
 * Implementation of SignInAdapter. This class role is to sign in a user whenever found in local database
 */
@Service
public class CustomSignInAdapter implements SignInAdapter {


    private final AccountServiceImpl accountServiceImpl;

    @Autowired
    public CustomSignInAdapter(AccountServiceImpl accountServiceImpl) {
        this.accountServiceImpl = accountServiceImpl;
    }

    @Override
    public String signIn(String email, Connection<?> connection, NativeWebRequest request) {
        Account user = accountServiceImpl.loadAccountByEmail(email);

        System.out.println("SignInAdapter called");
        if (user!=null){
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                user, null,
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
