package com.obss.Controllers;

/**
 * Created by arnold on 7/10/2017.
 * Controller that manages accounts and users.
 */

import com.obss.Model.Entities.Account;
import com.obss.Model.Entities.AccountDetails;
import com.obss.Model.Entities.Extras.AdvertApplication;
import com.obss.Model.Entities.Extras.ApplicationStatus;
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

    @RequestMapping(value = {"/", "/ilan/all"})
    public String userWelcomePage(RedirectAttributes redirectAttributes) {


        if (isAdmin()) {
            redirectAttributes.addFlashAttribute("successFlash", "IK Uzmani olararak giris yaptiniz");
            return "redirect: /admin/dashboard";
        } else if (isRegistered()) {
            //send redirect attribute
            redirectAttributes.addFlashAttribute("successFlash", "Successfully logged in");
            return "redirect:/ilan/all";
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

        String email = getCurrentUserEmail();
        Account account = accountService.loadAccountByEmail(email);
        if (account == null)
            return "/error?message=not_found";

        AccountDetails details = account.getAccountDetails();
        ArrayList<SkillView> skills = accountService.getAccountSkillsForUI(account);
        model.addAttribute("account", account);
        model.addAttribute("details", details);
        model.addAttribute("skills", skills);
        //  model.addAttribute("isAdmin", true);
        return "/user/profile";


    }


    @RequestMapping(value = "/user/basvurularim")
    public String userApplications(Model model) {
        String email = getCurrentUserEmail();
        ArrayList<AdvertApplication> list = accountService.getUserApplications(email);
        if (list.size() > 0) {
            model.addAttribute("list", list);
            return "/user/basvurularim";
        } else
            return "/notfound";

    }

    @RequestMapping(value = "/user/ilan/{ad_code}/apply")
    public String userApply(@PathVariable("ad_code") int adCode, RedirectAttributes ra) {

        int accountId = getCurrentUserId();
        applicationService.newApplication(adCode, accountId);
        ra.addFlashAttribute("successFlash", "İlana başvurdunuz!");
        return "redirect:/user/basvurularim";
    }

    @RequestMapping(value = "/user/ilan/{ad_code}/cancel")
    public String userCancelApplication(@PathVariable("ad_code") int adCode, RedirectAttributes ra) {

        int accoundId = getCurrentUserId();
        //accountService.applyToAdvert(adCode,email);
        applicationService.deleteApplicationByAccountIdAndAdCode(adCode, accoundId);
        ra.addFlashAttribute("successFlash", "Başvurunuz geri çekildi!");

        return "redirect:/user/basvurularim";


    }

    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority:auth.getAuthorities()){
            if(authority.getAuthority().equals("ROLE_ADMIN"))
                    return true;
        }

        return false;
    }

    private boolean isRegistered() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority : auth.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_USER"))
                return true;
        }

        return false;
    }

    private String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) auth.getPrincipal();
        return account.getEmail();
    }

    private int getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) auth.getPrincipal();
        return account.getAccountId();
    }
}