package com.cinema.demo.security.repsitories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.demo.entities.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, String> {
    // extra methods db relatedoperations
    // custom query methods
    // custom finder methods

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    Optional<UserEntity> findByEmailToken(String id);

}