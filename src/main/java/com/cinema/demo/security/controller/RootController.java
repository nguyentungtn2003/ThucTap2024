package com.cinema.demo.security.controller;

import com.cinema.demo.entity.UserEntity;
import com.cinema.demo.security.helpers.Helper;
import com.cinema.demo.security.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

//    @ModelAttribute
//    public void addLoggedInUserInformation(Model model, Authentication authentication) {
//        if (authentication == null) {
//            return;
//        }
//        System.out.println("Adding logged in user information to the model");
//        String username = Helper.getEmailOfLoggedInUser(authentication);
//        logger.info("User logged in: {}", username);
//        // database se data ko fetch : get user from db :
//        UserEntity user = userService.getUserByEmail(username);
//        System.out.println(user);
//        System.out.println(user.getName());
//        System.out.println(user.getEmail());
//        model.addAttribute("loggedInUser", user);
//
//    }
@ModelAttribute
public void addLoggedInUserInformation(Model model, Authentication authentication) {
    if (authentication == null) {
        return;
    }

    try {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        UserEntity user = userService.getUserByEmail(username);

        if (user == null) {
            logger.warn("No user found for email: {}", username);
            model.addAttribute("loggedInUser", null);
            return;
        }

        model.addAttribute("loggedInUser", user);

    } catch (IllegalArgumentException e) {
        logger.error("Error fetching logged-in user: {}", e.getMessage());
        model.addAttribute("loggedInUser", null);
    } catch (Exception e) {
        logger.error("Unexpected error: {}", e.getMessage());
        model.addAttribute("loggedInUser", null);
    }
}

}
