package com.obss.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.social.security.SpringSocialConfigurer;

import java.util.Arrays;

/**
 * Created by arnold on 7/10/2017.
 * Configuration  for Spring Security
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {


    //authentication provider for admins LDAP server
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=users")
                .groupSearchBase("ou=groups")
                .contextSource(contextSource())
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");

    }


    @Bean
    public DefaultSpringSecurityContextSource contextSource() {
        return  new DefaultSpringSecurityContextSource(Arrays.asList( "ldap://localhost:8389/"), "dc=springframework,dc=org");
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/admin/**", "/admin/").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/user/**", "/user/", "/").access("hasRole('ROLE_USER')")
                .antMatchers("/ilan/yeni").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/dist/**", "/bootstrap/**", "/plugins/**", "/", "/ilan/**").permitAll()
                .and()
                .formLogin().successHandler(new AppSuccessHandler())
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/favicon.ico", "/static-resources/**").permitAll()
                .and()
                .apply(new SpringSocialConfigurer())
                .and()
                .exceptionHandling().accessDeniedPage("/access_denied");


    }


}
