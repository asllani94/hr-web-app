package com.obss.Controllers;

/**
 * Created by arnold on 7/10/2017.
 * Controller that manages accounts and users.
 */

import com.obss.Model.Entities.Account;
import com.obss.Model.Entities.AccountDetails;
import com.obss.Model.Entities.Extras.ApplicationDetails;
import com.obss.Model.Entities.Extras.UiSkill;
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

import java.util.ArrayList;
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
    public String getUserWithId(@PathVariable("account_id") int id, Model model) {
        Account account = accountService.loadAccountByAccountId(id);
        if (account == null)
            return "/error";

        AccountDetails details = account.getAccountDetails();
        ArrayList<UiSkill> skills = accountService.getAccountSkillsForUI(account);
        model.addAttribute("account", account);
        model.addAttribute("details", details);
        model.addAttribute("skills", skills);
        return "/user/profile";


    }

    @RequestMapping(value = "/user/profilim")
    public String userProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Account account = accountService.loadAccountByEmail(email);
        if (account == null)
            return "/error";

        AccountDetails details = account.getAccountDetails();
        ArrayList<UiSkill> skills = accountService.getAccountSkillsForUI(account);
        model.addAttribute("account", account);
        model.addAttribute("details", details);
        model.addAttribute("skills", skills);
        return "/user/profile";


    }


    @RequestMapping(value = "/user/basvurularim")
    public String userApplications(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        ArrayList<ApplicationDetails> list = accountService.getUserApplications(email);

        model.addAttribute("list", list);

        return "/user/basvurularim";

    }

    private boolean isAdmin(Authentication auth){
        for (GrantedAuthority authority:auth.getAuthorities()){
            if(authority.getAuthority().equals("ROLE_ADMIN"))
                    return true;
        }

        return false;
    }

}