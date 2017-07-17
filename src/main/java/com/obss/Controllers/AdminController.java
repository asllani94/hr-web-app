package com.obss.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by arnold on 7/11/2017.
 * Controller that manages Admin processes
 */
@Controller
public class AdminController {

    @RequestMapping(value = {"/admin/dashboard","/admin"})

    public String userWelcomePage()

    {
        return "/admin/dashboard";
    }
}