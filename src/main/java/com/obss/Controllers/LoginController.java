package com.obss.Controllers;


import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SocialUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by arnold on 7/10/2017.
 * Controller responsible for login and signup
 */

@Controller
public class LoginController {


    @RequestMapping("/login")
    public String userLogin(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String referer = request.getHeader("Referer");

        boolean hasError = request.getParameterMap().containsKey("error");

        if (referer != null && referer.equals("http://localhost:8080/admin-login") && hasError) {
            redirectAttributes.addAttribute("error", "Invalid login details");
            return "redirect:/admin-login";
        } else if (referer != null && hasError)
            redirectAttributes.addAttribute("error", "Linkedin ile giris basarisiz oldu");


        model.addAttribute("isAdminLogin", false);
        model.addAttribute("isUserLogin", true);
        return "login";
    }

    @RequestMapping("/admin-login")
    public String adminLogin(Model model) {
        model.addAttribute("isAdminLogin", true);
        model.addAttribute("isUserLogin", false);
        return "login";
    }

    @RequestMapping("/access_denied")
    public String accessDenied() {
        return "/access_denied";
    }

    @RequestMapping("/logout")
    public String userLogout() {
        performLogout();
        return "redirect:/login";
    }


    private void performLogout() {
        SecurityContextHolder.getContext().setAuthentication(
                new AnonymousAuthenticationToken(UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        Arrays.asList(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))));
    }






}