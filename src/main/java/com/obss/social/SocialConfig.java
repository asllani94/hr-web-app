package com.obss.social;

import com.obss.Model.Services.AccountServiceImpl;
import com.obss.Model.Services.AdvertServiceImpl;
import com.obss.Model.Services.SkillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import javax.sql.DataSource;

/**
 * Created by arnold on 7/11/2017.
 * Configuration for Spring Social
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {
    @Autowired
    SkillServiceImpl skillService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AccountServiceImpl myUserAccountServiceImpl;
    @Autowired
    private AdvertServiceImpl advertService;

    @Bean
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }


    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository usersConnectionRepository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());
        usersConnectionRepository.setConnectionSignUp(new CustomConnectionSignUp(myUserAccountServiceImpl, advertService, skillService));
        return  usersConnectionRepository;
    }


}
