package com.cinema.demo.security.controller;

import lombok.RequiredArgsConstructor;
import com.cinema.demo.security.request.UserDTO;
import com.cinema.demo.security.response.BaseResponse;
import com.cinema.demo.security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @PostMapping("/register-account")
    public ResponseEntity<BaseResponse> registerAccount(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registerAccount(userDTO));
    }
}
