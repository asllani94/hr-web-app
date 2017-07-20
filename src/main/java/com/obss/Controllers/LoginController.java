package com.obss.Controllers;


import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SocialUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by arnold on 7/10/2017.
 * Controller responsible for login and signup
 */

@Controller
public class LoginController {


    @RequestMapping("/login")
    public String userLogin() {
        return "login";
    }

    @RequestMapping("/logout")
    public String userLogout() {
        performLogout();
        return "redirect:/login";
    }


    private void performLogout() {
        SecurityContextHolder.getContext().setAuthentication(
                new AnonymousAuthenticationToken(UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        Arrays.asList(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))));
    }






}