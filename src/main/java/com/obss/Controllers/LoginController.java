package com.obss.Controllers;


import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Created by arnold on 7/10/2017.
 */

@Controller
public class LoginController {


    @RequestMapping("/user_login")
    public String userLogin()
    {
        return "user_login";

    }

    @RequestMapping("/admin_login")
    public String adminLogin()
    {
        return "admin_login";

    }


}