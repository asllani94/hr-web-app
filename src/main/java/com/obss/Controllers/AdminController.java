package com.obss.Controllers;

import com.obss.Controllers.Forms.AdvertForm;
import com.obss.Controllers.Forms.BlackListForm;
import com.obss.Model.Entities.*;
import com.obss.Model.Entities.Extras.AdvertApplication;
import com.obss.Model.Entities.Extras.ApplicationStatus;
import com.obss.Model.Entities.Extras.SkillView;
import com.obss.Model.Entities.Extras.UserApplication;
import com.obss.Model.Services.ApplicationServiceImpl;
import com.obss.Model.Services.Interfaces.AccountService;
import com.obss.Model.Services.Interfaces.AdvertService;
import com.obss.Model.Services.SkillServiceImpl;
import com.obss.Utils.DateUtil;
import com.obss.Utils.EmailUtil;
import com.sun.deploy.util.BlackList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by arnold on 7/11/2017.
 * Controller that manages Admin processes
 */
@Controller
public class AdminController {

    @Autowired
    AdvertService advertService;

    @Autowired
    AccountService accountService;

    @Autowired
    ApplicationServiceImpl applicationService;

    @Autowired
    private SkillServiceImpl skillService;

    @RequestMapping(value = {"/admin/dashboard","/admin"})
    public String userWelcomePage(Model model) {

        model.addAttribute("totalAccount", accountService.getTotalAccounts());
        model.addAttribute("totalAdvert", advertService.getTotalAdverts());
        return "/admin/dashboard";
    }

    @RequestMapping(value = {"/admin/ilan/ilanlar"})
    public String adminAdvertList(Model model) {
        List<Advert> advertList = advertService.loadAllAdverts();
        model.addAttribute("list", advertList);
        return "/ilan/list";
    }

    @RequestMapping(value = {"/admin/ilan/{ad_code}"})
    public String adminAdvertInfo(@PathVariable(value = "ad_code") int adCode, Model model) {
        List<UserApplication> orderedCandidates = advertService.getCandidateApplications(adCode);
        model.addAttribute("adCode", adCode);
        model.addAttribute("list", orderedCandidates);
        return "/ilan/application_list";
    }


    @RequestMapping(value = {"/admin/ilan/yeni"})
    public String adminAdvertCreate(Model model) {
        AdvertForm advertForm = new AdvertForm();
        advertForm.setUpdate(false);
        model.addAttribute("advertForm", advertForm);
        model.addAttribute("list", skillService.getAllSkills());
        return "/ilan/yeni";
    }

    @RequestMapping(value = {"/admin/ilan/{ad_code}/guncelle"})
    public String adminAdvertUpdate(@PathVariable(value = "ad_code") int adCode, Model model) {
        AdvertForm advertForm = new AdvertForm();
        populateAdvertForm(advertForm, adCode);
        advertForm.setUpdate(true);
        model.addAttribute("advertForm", advertForm);
        model.addAttribute("list", skillService.getAllSkills());
        return "/ilan/yeni";
    }

    @RequestMapping(value = {"/admin/ilan/{ad_code}/sil"})
    public String adminAdvertDelete(@PathVariable(value = "ad_code") int adCode, RedirectAttributes redirectAttributes) {
        advertService.deleteAdvert(adCode);
        redirectAttributes.addFlashAttribute("successFlash", "Ilan silindi!");
        return "redirect:/admin/ilan/ilanlar";
    }

    @RequestMapping(value = {"/admin/ilan/{ad_code}/activate"})
    public String adminAdvertActivate(@PathVariable(value = "ad_code") int adCode, RedirectAttributes redirectAttributes) {
        advertService.activateAdvert(adCode);
        redirectAttributes.addFlashAttribute("successFlash", "Ilan etkinleştirildi!");
        return "redirect:/admin/ilan/ilanlar";
    }

    @RequestMapping(value = {"/admin/ilan/{ad_code}/deactivate"})
    public String adminAdvertDeactivate(@PathVariable(value = "ad_code") int adCode, RedirectAttributes redirectAttributes) {
        advertService.deactivateAdvert(adCode);
        redirectAttributes.addFlashAttribute("successFlash", "Ilan kapatıldı!");
        return "redirect:/admin/ilan/ilanlar";
    }

    @RequestMapping(value = {"/admin/ilan/{ad_code}/user/{account_id}/accept"})
    public String adminAcceptApplication(@PathVariable(value = "ad_code") int adCode,
                                         @PathVariable(value = "account_id") int accountId,
                                         RedirectAttributes redirectAttributes) {

        applicationService.updateApplicationStatusByAccountId(adCode, accountId, ApplicationStatus.ACCEPTED);
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.notifyStatusChange(accountService.getEmailByAccountId(accountId), ApplicationStatus.ACCEPTED, adCode);
        redirectAttributes.addFlashAttribute("successFlash", "Basvuru kabul edildi");

        return "redirect:/admin/ilan/" + adCode;
    }

    @RequestMapping(value = {"/admin/ilan/{ad_code}/user/{account_id}/reject"})
    public String adminRejectApplication(@PathVariable(value = "ad_code") int adCode,
                                         @PathVariable(value = "account_id") int accountId,
                                         RedirectAttributes redirectAttributes) {

        applicationService.updateApplicationStatusByAccountId(adCode, accountId, ApplicationStatus.REJECTED);
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.notifyStatusChange(accountService.getEmailByAccountId(accountId), ApplicationStatus.REJECTED, adCode);
        redirectAttributes.addFlashAttribute("successFlash", "Basvuru red edildi");

        return "redirect:/admin/ilan/" + adCode;
    }

    @RequestMapping(value = {"/admin/user/{account_id}"})
    public String adminViewCandidateProfile(@PathVariable(value = "account_id") int accountId,
                                            Model model) {

        Account account = accountService.loadAccountByAccountId(accountId);
        AccountDetails details = account.getAccountDetails();
        List<SkillView> skills = accountService.getAccountSkillsForUI(account);
        ArrayList<AdvertApplication> list = accountService.getUserApplications(accountId);
        Blacklist blackList = account.getBlacklist();
        boolean isBlackListed = false;

        if (blackList != null)
            isBlackListed = true;

        BlackListForm blackListForm = new BlackListForm();
        blackListForm.setAccountId(accountId);

        model.addAttribute("isBlackListed", isBlackListed);
        model.addAttribute("account", account);
        model.addAttribute("details", details);
        model.addAttribute("skills", skills);
        model.addAttribute("list", list);
        model.addAttribute("blackListForm", blackListForm);
        return "/user/user_admin";
    }


    @RequestMapping(value = "/admin/user/blacklist", method = RequestMethod.POST)
    public String blackListCandidate(@Valid BlackListForm blackListForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorFlash", "Sebep boş olamaz!");
            return "redirect:/admin/user/" + blackListForm.getAccountId();
        }

        Account account = accountService.loadAccountByAccountId(blackListForm.getAccountId());
        Blacklist blacklist = new Blacklist();
        blacklist.setReason(blackListForm.getReason());
        account.setBlacklist(blacklist);
        accountService.createOrUpdateAccount(account);
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.notifyCandidateBlacklisted(account.getEmail(), blackListForm.getReason());
        applicationService.rejectCandidateApplications(blackListForm.getAccountId());

        redirectAttributes.addFlashAttribute("successFlash", "Aday Karalistelendi!");
        return "redirect:/admin/user/" + blackListForm.getAccountId();

    }


    private void populateAdvertForm(AdvertForm advertForm, int adCode) {
        Advert advert = advertService.loadAdvertByAdCode(adCode);
        advertForm.setAdCode(advert.getAdCode());
        advertForm.setAdHead(advert.getAdHeader());
        advertForm.setAdTitle(advert.getAdJobTitle());
        advertForm.setAdAddress(advert.getAdJobLocation());
        advertForm.setAdDescription(advert.getAdDescription());
        advertForm.setAdQualification(advert.getAdQualifications());
        advertForm.setAdActivation(advert.getStringActivationTime());
        advertForm.setAdDeadline(advert.getStringDeadlineTime());
        advertForm.setSkillList(advert.getSkills());
    }

}