package com.cinema.demo.security.services;

import java.util.List;
import java.util.Optional;

import com.cinema.demo.entities.UserEntity;

public interface UserService {

    UserEntity saveUser(UserEntity user);

    Optional<UserEntity> getUserById(String id);

    Optional<UserEntity> updateUser(UserEntity user);

    void deleteUser(String id);

    boolean isUserExist(String userId);

    boolean isUserExistByEmail(String email);

    List<UserEntity> getAllUsers();

    UserEntity getUserByEmail(String email);

    // add more methods here related user service[logic]

}
