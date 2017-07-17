package com.obss.Controllers;

import com.obss.Model.Jpa.Advert;
import com.obss.Model.Rep.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by arnold on 7/12/2017.
 */

@Controller
public class AdvertController {
    @Autowired
    AdvertRepository advertRepository;

    @RequestMapping(value = "/user/ilanlar", method = RequestMethod.GET)
    public String list(Model model) {

        List<Advert> adList=advertRepository.findAll();
        model.addAttribute("ad_list",adList);
        return "user/ilanlar";

    }

}
