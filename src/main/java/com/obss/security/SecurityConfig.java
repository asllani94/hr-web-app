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
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //authentication provider for admins LDAP server
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
                .antMatchers("/admin/**","/admin/").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/user/**","/user/","/").access("hasRole('ROLE_USER')")
                .antMatchers("/dist/**","/bootstrap/**","/plugins/**").permitAll()
                .and()
                .formLogin().successHandler(new AppSuccessHandler())
                .loginPage("/user_login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/user_login?param.error=bad_credentials")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/favicon.ico", "/static-resources/**").permitAll()
                .and()
                .apply(new SpringSocialConfigurer());


    }
/*

    @Configuration
    @Order(1)
    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
        public AdminSecurityConfig() {
            super();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/admin/**")
                    .authorizeRequests()
                    .anyRequest()
                    .hasRole("ADMIN")
                    .and()
                    .formLogin()
                    .loginPage("/login_admin")
                    .loginProcessingUrl("/admin_login/authenticate")
                    .failureUrl("/admin_login?error=loginError")
                    .defaultSuccessUrl("/admin/dashboard")
                    .and()
                    .logout()
                    .logoutUrl("/admin_logout")
                    .logoutSuccessUrl("/protectedLinks")
                    .deleteCookies("JSESSIONID")
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/403")
                    .and()
                    .csrf().disable()
                    .exceptionHandling()
                    .defaultAuthenticationEntryPointFor(
                            new LoginUrlAuthenticationEntryPoint("/admin_login"),
                            new AntPathRequestMatcher("/admin/**"));
        }
    }

    @Configuration
    public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {

        public UserSecurityConfig() {
            super();
        }

        protected void configure(HttpSecurity http) throws Exception {
            LoginUrlAuthenticationEntryPoint authenticationEntryPoint = new LoginUrlAuthenticationEntryPoint("/user_login");
            http
                    .authorizeRequests()
                        .antMatchers("/auth","/auth/**","/user_login").permitAll()
                        .antMatchers("/user/**").hasAnyRole("USER")
                    .and()
                    .exceptionHandling()
                         .authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                    .apply(new SpringSocialConfigurer().postLoginUrl("/user/welocme"))
                    .and()
                    .logout()
                        .logoutSuccessUrl("/user_login");

        }




    }

*/

}
