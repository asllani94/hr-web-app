package com.obss.Controllers;


import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Created by arnold on 7/10/2017.
 * Controller responsible for login and signup
 */

@Controller
public class LoginController {


    @RequestMapping("/login")
    public String userLogin()
    {
        return "login";

    }




}