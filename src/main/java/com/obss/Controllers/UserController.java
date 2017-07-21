package com.obss.Controllers;

/**
 * Created by arnold on 7/10/2017.
 * Controller that manages accounts and users.
 */

import com.obss.Model.Entities.Account;
import com.obss.Model.Entities.AccountDetails;
import com.obss.Model.Entities.Extras.AdvertApplication;
import com.obss.Model.Entities.Extras.SkillView;
import com.obss.Model.Repositories.ApplicationRepository;
import com.obss.Model.Services.AccountServiceImpl;
import com.obss.Model.Services.ApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;


@Controller
public class UserController {

    private final AccountServiceImpl accountService;

    @Autowired
    private ApplicationServiceImpl applicationService;

    @Autowired
    public UserController(AccountServiceImpl accountService) {
        this.accountService = accountService;

    }

    @RequestMapping(value = {"/", "/user/welcome"})
    public String userWelcomePage() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(isAdmin(auth)){
            //Current logged user has admin authority so we need to redirect it
            return "redirect: /admin/dashboard";
        }
        return "redirect:/ilan/all";
    }

    @RequestMapping(value = "/user/{account_id}")
    public String getUserWithId(@PathVariable("account_id") int id, Model model) {
        Account account = accountService.loadAccountByAccountId(id);
        if (account == null)
            return "/error?message=not_found";
        AccountDetails details = account.getAccountDetails();
        ArrayList<SkillView> skills = accountService.getAccountSkillsForUI(account);
        model.addAttribute("account", account);
        model.addAttribute("details", details);
        model.addAttribute("skills", skills);
        model.addAttribute("isAdmin", true);
        return "/user/profile";
    }

    @RequestMapping(value = "/user/profilim")
    public String userProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Account account = accountService.loadAccountByEmail(email);
        if (account == null)
            return "/error?message=not_found";

        AccountDetails details = account.getAccountDetails();
        ArrayList<SkillView> skills = accountService.getAccountSkillsForUI(account);
        model.addAttribute("account", account);
        model.addAttribute("details", details);
        model.addAttribute("skills", skills);
        model.addAttribute("isAdmin", true);
        return "/user/profile";


    }


    @RequestMapping(value = "/user/basvurularim")
    public String userApplications(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        ArrayList<AdvertApplication> list = accountService.getUserApplications(email);

        model.addAttribute("list", list);

        return "/user/basvurularim";

    }

    @RequestMapping(value = "/user/ilan/{ad_code}/apply")
    public String userApply(@PathVariable("ad_code") int adCode, RedirectAttributes ra) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        //accountService.applyToAdvert(adCode,email);
        applicationService.newApplication(adCode, 1);


        ra.addFlashAttribute("successFlash", "Ilana basvuruldu");

        return "redirect:/user/basvurularim";


    }

    private boolean isAdmin(Authentication auth){
        for (GrantedAuthority authority:auth.getAuthorities()){
            if(authority.getAuthority().equals("ROLE_ADMIN"))
                    return true;
        }

        return false;
    }

}