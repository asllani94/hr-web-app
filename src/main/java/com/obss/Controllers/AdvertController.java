package com.obss.Controllers;

import com.obss.Controllers.Forms.AdvertForm;
import com.obss.Model.Entities.Extras.SkillView;
import com.obss.Model.Services.AccountServiceImpl;
import com.obss.Model.Services.AdvertServiceImpl;
import com.obss.Model.Entities.Advert;
import com.obss.Model.Services.ApplicationServiceImpl;
import com.obss.Model.Services.SkillServiceImpl;
import com.obss.Utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnold on 7/12/2017.
 * Controller responsible for adverts,  create,delete,update and list all adverts
 */


@Controller
public class AdvertController {

    @Autowired
    private AdvertServiceImpl advertService;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private ApplicationServiceImpl applicationService;

    @RequestMapping(value = "/ilan/all", method = RequestMethod.GET)
    public String list(Model model) {
        List<Advert> adList = advertService.loadAllActiveAdverts();
        model.addAttribute("ad_list",adList);
        return "ilan/ilanlar";
    }

    @RequestMapping(value = "/ilan/{ad_code}")
    public String getAdvertByCode(@PathVariable("ad_code") int adCode, Model model) {
        Advert advert = advertService.loadAdvertByAdCode(adCode);

        if (advert == null)
            return "/error?message=not_found";

        boolean hasApplied = false;

        boolean isRegistered = this.isRegistered();

        if (isRegistered)
            hasApplied = accountService.hasAlreadyApplied(adCode, this.getAuthenticatedEmail());

        List<SkillView> list = advertService.getAdvertSkillsForUI(advert);
        model.addAttribute("advert", advert);
        model.addAttribute("skills", list);
        model.addAttribute("isRegistered", isRegistered);
        model.addAttribute("hasApplied", hasApplied);
        return "/ilan/single_ilan";
    }

    @RequestMapping(value = "/ilan/{ad_code}/apply")
    public String applyToAdvert(@PathVariable("ad_code") int adCode, RedirectAttributes redirectAttributes) {
        Advert advert = advertService.loadAdvertByAdCode(adCode);

        if (advert == null)
            return "/error?message=not_found";

        if (!isRegistered())
            redirectAttributes.addAttribute("loginMessage", "Ilana basvurmak icin LinkedIn hesabi ile giris yapin");

        int accoundId = accountService.getUserIdByEmail(getAuthenticatedEmail());
        applicationService.newApplication(adCode, accoundId);
        redirectAttributes.addFlashAttribute("successFlash", "Ilana Basvuruldu");
        return "redirect:/user/basvurularim";
    }





    @RequestMapping(value = "/ilan/yeni", method = RequestMethod.POST)
    public String createNewAdvert(@Valid AdvertForm advertForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorFlash", "Alanlar boş olamaz!");
            return "redirect:/admin/ilan/yeni";
        }

        if (!DateUtil.isValidDate(advertForm.getAdActivation()) && !DateUtil.isValidDate(advertForm.getAdDeadline())) {
            redirectAttributes.addFlashAttribute("errorFlash", "Yanlış tarih biçimi!!");
            return "redirect:/admin/ilan/yeni";
        }

        if (advertForm.getAdCode() > 0) {
            advertService.updateAdvert(advertForm);
            redirectAttributes.addFlashAttribute("successFlash", "Ilan guncellendi!");
            return "redirect:/admin/ilan/ilanlar";
        } else {
            advertService.createAdvert(advertForm.buildAdvert());
            redirectAttributes.addFlashAttribute("successFlash", "Ilan oluşturuldu!");
            return "redirect:/admin/ilan/ilanlar";
        }


    }

    private boolean isRegistered() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority : auth.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_USER"))
                return true;
        }

        return false;
    }

    private String getAuthenticatedEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}
