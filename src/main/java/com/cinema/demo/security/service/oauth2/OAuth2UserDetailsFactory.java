package com.cinema.demo.security.service.oauth2;

import com.cinema.demo.entity.Provider;
import com.cinema.demo.security.exception.BaseException;

import java.util.Map;

public class OAuth2UserDetailsFactory {

    public static OAuth2UserDetails getOAuth2UserDetails(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equals(Provider.google.name())) {
            return new OAuth2GoogleUser(attributes);
        } else if (registrationId.equals(Provider.facebook.name())) {
            return new OAuth2FacebookUser(attributes);
        } else {
            throw new BaseException("400", "Sorry! Login with " + registrationId + " is not supported");
        }
    }
}
