package com.obss.social;

import com.obss.Model.Dao.UserDao;
import com.obss.Model.Jpa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by arnold on 7/10/2017.
 */
@Service
public class CustomSignInAdapter implements SignInAdapter {


    private final UserDao userDao;

    @Autowired
    public CustomSignInAdapter(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String signIn(String email, Connection<?> connection, NativeWebRequest request) {
        User user=userDao.loadUserByEmail(email);

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
                        new UsernamePasswordAuthenticationToken(null, null, null));
                return null;
            }

    }

}
