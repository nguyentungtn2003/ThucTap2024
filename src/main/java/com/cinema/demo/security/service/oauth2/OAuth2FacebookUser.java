package com.cinema.demo.security.service.oauth2;

import java.util.Map;

public class OAuth2FacebookUser extends OAuth2UserDetails{

    public OAuth2FacebookUser(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return (String) attributes.getOrDefault("name", "Unknown User");
    }

    @Override
    public String getEmail() {
        return (String) attributes.getOrDefault("email", "no-email@domain.com");
    }

}
