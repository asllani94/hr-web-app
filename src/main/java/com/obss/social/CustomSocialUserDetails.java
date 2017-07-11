package com.obss.social;

import com.obss.Model.Jpa.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by arnold on 7/11/2017.
 */
public class CustomSocialUserDetails implements SocialUserDetails {

    private  User user;
    private static final long serialVersionUID = -5246117266247684905L;

    private List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

    public CustomSocialUserDetails(User user){
        this.user = user;
        list=getGrantedAuthorities("ROLE_USER");

    }
    @Override
    public String getUserId() {
        return user.getUserId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.list;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private List<GrantedAuthority> getGrantedAuthorities(String privilege) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(privilege));

        return authorities;
    }
}
