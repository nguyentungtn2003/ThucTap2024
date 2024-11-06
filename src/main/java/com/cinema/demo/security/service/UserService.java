package com.cinema.demo.security.service;

import com.cinema.demo.entity.UserEntity;
import com.cinema.demo.security.dto.UserDto;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    UserEntity findUserByEmail(String email);

    UserDto findUserDtoByEmail(String email);

    List<UserDto> findAllUsers();
}