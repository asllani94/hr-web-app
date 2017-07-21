package com.obss.Controllers;

import com.obss.Controllers.Forms.AdvertForm;
import com.obss.Model.Entities.Extras.SkillView;
import com.obss.Model.Services.AdvertServiceImpl;
import com.obss.Model.Entities.Advert;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<SkillView> list = advertService.getAdvertSkillsForUI(advert);
        model.addAttribute("advert", advert);
        model.addAttribute("skills", list);
        return "/ilan/single_ilan";
    }


    @RequestMapping(value = "/ilan/yeni", method = RequestMethod.POST)
    public String createNewAdvert(@Valid AdvertForm advertForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorFlash", "Form cant have empty field!!");
            return "redirect:/admin/ilan/yeni";
        }

        if (advertForm.getAdCode() > 0) {

            advertService.updateAdvert(advertForm);
            redirectAttributes.addFlashAttribute("successFlash", "Ilan guncelledi!");
            return "redirect:/admin/ilan/ilanlar";
        } else {
            advertService.createAdvert(advertForm.buildAdvert());
            redirectAttributes.addFlashAttribute("successFlash", "Ilan olusturdu!");
            return "redirect:/admin/ilan/ilanlar";
        }


    }
}
