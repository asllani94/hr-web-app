package com.obss.security;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by arnold on 7/11/2017.
 */
public class AppAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public AppAuthenticationEntryPoint(final String loginFormUrl) {
        super(loginFormUrl);
    }

    /**
     * Performs the redirect (or forward) to the login form URL.
     */
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        // redirect to login page. Use https if forceHttps true
        String redirectUrl = buildRedirectUrlToLoginPage(request, response, authException);
        redirectStrategy.sendRedirect(request, response, redirectUrl);
    }

}