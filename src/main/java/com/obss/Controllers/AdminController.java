package com.obss.Controllers;

import com.obss.Controllers.Forms.AdvertForm;
import com.obss.Model.Entities.Advert;
import com.obss.Model.Services.Interfaces.AccountService;
import com.obss.Model.Services.Interfaces.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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


    @RequestMapping(value = {"/admin/ilan/yeni"})
    public String adminAdvertCreate(Model model) {
        AdvertForm advertForm = new AdvertForm();
        advertForm.setUpdate(false);
        model.addAttribute("advertForm", advertForm);
        return "/ilan/yeni";
    }

    @RequestMapping(value = {"/admin/ilan/{ad_code}/guncelle"})
    public String adminAdvertUpdate(@PathVariable(value = "ad_code") int adCode, Model model) {

        AdvertForm advertForm = new AdvertForm();
        populateForm(advertForm, adCode);
        advertForm.setUpdate(true);
        model.addAttribute("advertForm", advertForm);

        return "/ilan/yeni";
    }

    @RequestMapping(value = {"/admin/ilan/{ad_code}/sil"})
    public String adminAdvertDelete(@PathVariable(value = "ad_code") int adCode) {
        advertService.deleteAdvert(adCode);


        return "/extra/success";
    }


    private void populateForm(AdvertForm advertForm, int adCode) {
        Advert advert = advertService.loadAdvertByAdCode(adCode);
        advertForm.setAdCode(advert.getAdCode());
        advertForm.setAdHead(advert.getAdHeader());
        advertForm.setAdTitle(advert.getAdJobTitle());
        advertForm.setAdAddress(advert.getAdJobLocation());
        advertForm.setAdDescription(advert.getAdDescription());
        advertForm.setAdQualification(advert.getAdQualifications());
        advertForm.setAdActivation(advert.getAdActivationTime().toString());
        advertForm.setAdDeadline(advert.getAdDeadlineTime().toString());
    }

}