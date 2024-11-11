package com.cinema.demo.security.controller;

import jakarta.validation.Valid;
import com.cinema.demo.dto.UserDto;
import com.cinema.demo.entity.UserEntity;
import com.cinema.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {
    private IUserService IUserService;
    //private MovieService movieService;

    @Autowired
    public void setUserService(IUserService IUserService) {
        this.IUserService = IUserService;
    }

    // handler method to handle home page request
    @GetMapping("/home")
    public String home() {
//        List<MovieEntity> movies = movieService.getAllMovies();
//        model.addAttribute("movies", movies);
        return "home1";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration form submit request
//    @PostMapping("/register/save")
//    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
//                               BindingResult result,
//                               Model model) {
//        UserEntity existingUser = userService.findUserByEmail(userDto.getEmail());
//
//        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
//            result.rejectValue("email", null,
//                    "There is already an account registered with the same email");
//        }
//
//        if (result.hasErrors()) {
//            model.addAttribute("user", userDto);
//            return "/register";
//        }
//
//        userService.saveUser(userDto);
//        return "redirect:/register?success";
//    }
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        // Kiểm tra xem email đã tồn tại chưa
        UserEntity existingUser = IUserService.findUserByEmail(userDto.getEmail());
        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, "There is already an account registered with the same email");
            System.out.println("Existing user found with email: " + userDto.getEmail());
        }

        // Kiểm tra xem có lỗi validate không
        if (result.hasErrors()) {
            System.out.println("Validation errors found: " + result.getAllErrors());
            model.addAttribute("user", userDto);
            return "/register";
        }

        try {
            IUserService.saveUser(userDto);
            System.out.println("User saved successfully: " + userDto.getEmail());
        } catch (Exception e) {
            System.out.println("Error occurred while saving user: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("user", userDto);
            return "/register";
        }

        return "redirect:/register?success";
    }


    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = IUserService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "login";
    }


}