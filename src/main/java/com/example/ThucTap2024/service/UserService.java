package com.example.ThucTap2024.service;

import com.example.ThucTap2024.dto.UserDto;
import com.example.ThucTap2024.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    UserDto findUserDtoByEmail(String email);

    List<UserDto> findAllUsers();
}