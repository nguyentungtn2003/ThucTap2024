package com.cinema.demo.security.service;

import com.cinema.demo.entities.UserEntity;
import com.cinema.demo.security.request.UserDTO;
import com.cinema.demo.security.response.BaseResponse;

public interface UserService {

    BaseResponse registerAccount(UserDTO userDTO);

    UserEntity getUserByEmail(String email);

    UserEntity saveUser(UserEntity user);
}