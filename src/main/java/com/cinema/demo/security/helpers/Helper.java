package com.cinema.demo.security.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    @Value("${server.baseUrl}")
    private String baseUrl;

//    public static String getEmailOfLoggedInUser(Authentication authentication) {
//
//        // agar email is password se login kiya hai to : email kaise nikalenge
//        if (authentication instanceof OAuth2AuthenticationToken) {
//
//            var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
//            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
//
//            var oauth2User = (OAuth2User) authentication.getPrincipal();
//            String email = "";
//
//            if (clientId.equalsIgnoreCase("google")) {
//
//                // sign with google
//                System.out.println("Getting email from google");
//                email = oauth2User.getAttribute("email").toString();
//
//            } else if (clientId.equalsIgnoreCase("facebook")) {
//
//                // sign with github
//                System.out.println("Getting email from facebook");
//                email = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
//                        : oauth2User.getAttribute("login").toString() + "@gmail.com";
//            }
//
//            // sign with facebook
//            return email;
//
//        } else {
//            System.out.println("Getting data from local database");
//            return authentication.getName();
//        }
//
//    }

    public static String getEmailOfLoggedInUser(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauth2User = (OAuth2User) authentication.getPrincipal();

            // Log OAuth2 attributes for debugging
            System.out.println("OAuth2 attributes: " + oauth2User.getAttributes());

            // Safely handle missing attributes
            String email = null;
            if ("google".equalsIgnoreCase(clientId)) {
                email = (String) oauth2User.getAttribute("email");
            } else if ("facebook".equalsIgnoreCase(clientId)) {
                email = oauth2User.getAttribute("email") != null
                        ? oauth2User.getAttribute("email").toString()
                        : oauth2User.getAttribute("login") + "@gmail.com";
            }

            if (email == null) {
                throw new IllegalArgumentException("Email not provided by OAuth2 provider");
            }

            return email;
        } else {
            // Fallback for standard authentication
            return authentication.getName();
        }
    }


    public String getLinkForEmailVerificatiton(String emailToken) {

        return this.baseUrl + "/auth/verify-email?token=" + emailToken;

    }
}
