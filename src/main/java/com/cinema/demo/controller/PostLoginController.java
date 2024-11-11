package com.cinema.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PostLoginController {

    private static final Logger logger = LoggerFactory.getLogger(PostLoginController.class);

    @GetMapping("/postLogin")
    public String postLoginRedirect(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Log các vai trò của người dùng để kiểm tra
        logger.info("User roles: {}", authorities);

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if ("ROLE_ADMIN".equals(role)) {
                return "redirect:/admin"; // Chuyển hướng đến trang admin
            } else if ("ROLE_USER".equals(role)) {
                return "redirect:/home1"; // Chuyển hướng đến trang user
            }
        }
        // Trường hợp không có vai trò phù hợp
        logger.warn("No matching role found for user. Redirecting to default page.");
        return "/default";
    }
}

