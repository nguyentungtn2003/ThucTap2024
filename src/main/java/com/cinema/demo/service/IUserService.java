package com.cinema.demo.service;

import com.cinema.demo.dto.PasswordChangeDto;
import com.cinema.demo.dto.UpdateUserDto;
import com.cinema.demo.entity.UserEntity;
import com.cinema.demo.dto.UserDto;

import java.util.List;

public interface IUserService {
    void saveUser(UserDto userDto);

    UserEntity findUserByEmail(String email);

    UserDto findUserDtoByEmail(String email);

    List<UserDto> findAllUsers();

    UserEntity findUserById(Long id);


    void updateUser(UpdateUserDto UpdateUserDto);

    void updatePassword(UserEntity userEntity, String newPassword);
}