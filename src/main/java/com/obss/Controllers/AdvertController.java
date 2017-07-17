package com.obss.Controllers;

import com.obss.Model.Services.AdvertServiceImpl;
import com.obss.Model.Entities.Advert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by arnold on 7/12/2017.
 * Controller responsible for adverts,  create,delete,update and list all adverts
 */

@Controller
public class AdvertController {

    @Autowired
    private AdvertServiceImpl advertService;

    @RequestMapping(value = "/user/ilanlar", method = RequestMethod.GET)
    public String list(Model model) {

        List<Advert> adList = advertService.loadAllActiveAdverts();
        model.addAttribute("ad_list",adList);
        return "ilan/ilanlar";

    }

}
