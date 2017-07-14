package com.obss.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by arnold on 7/12/2017.
 */

@Controller
public class AdvertController {
    @RequestMapping(value = "/user/ilanlar", method = RequestMethod.GET)
    public String list() {

        return "user/ilanlar";

    }

}
