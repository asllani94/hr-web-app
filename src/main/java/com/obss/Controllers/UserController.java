package com.obss.Controllers;

/**
 * Created by arnold on 7/10/2017.
 * Controller that manages accounts and users.
 */

import com.obss.Model.Entities.Account;
import com.obss.Model.Entities.AccountDetails;
import com.obss.Model.Entities.Skill;
import com.obss.Model.Services.AccountServiceImpl;
import com.obss.Model.Services.Interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.orm.hibernate4.SpringSessionContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Set;


@Controller
public class UserController {

    @Autowired
    AccountServiceImpl accountService;

    @RequestMapping(value = {"/", "/user/welcome"})
    public String userWelcomePage() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(isAdmin(auth)){

            //Current logged user has admin authority so we need to redirect it
            return "redirect: /admin/dashboard";
        }

        return "redirect:/user/ilanlar";
    }

    @RequestMapping(value = "/user/{account_id}")
    public String userProfile(@PathVariable("account_id") int id, Model model) {
        Account account = accountService.loadAccountByAccountId(id);
        if (account == null)
            return "/error";

        AccountDetails details = account.getAccountDetails();
        Set<Skill> skills = account.getSkills();
        model.addAttribute("account", account);
        model.addAttribute("details", details);
        model.addAttribute("skills", skills);
        return "/user/profile";


    }

    private boolean isAdmin(Authentication auth){
        for (GrantedAuthority authority:auth.getAuthorities()){
            if(authority.getAuthority().equals("ROLE_ADMIN"))
                    return true;
        }

        return false;
    }

}