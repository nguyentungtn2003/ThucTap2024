package com.cinema.demo.security.service.oauth2.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class OAuth2UserDetailsCustom implements OAuth2User, UserDetails {

    private Long id;

    private String username;

    private String password;

    private List<GrantedAuthority> authorities;

    private Map<String, Object> attributes;

    private boolean isEnabled;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    public OAuth2UserDetailsCustom(Long id, String username, String password, List<GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
