package com.obss.Controllers;

/**
 * Created by arnold on 7/10/2017.
 */
import org.springframework.orm.hibernate4.SpringSessionContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserController {

    @RequestMapping(value = {"/", "/user/welcome"})

    public String userWelcomePage()
    {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(isAdmin(auth)){

            //Current logged user has admin authority so we need to redirect it
            return "redirect:/admin/dashboard";
        }


        return "/user/welcome";
    }

    private boolean isAdmin(Authentication auth){

        for (GrantedAuthority authority:auth.getAuthorities()){
            if(authority.getAuthority().equals("ROLE_ADMIN"))
                    return true;
        }

        return false;
    }

}