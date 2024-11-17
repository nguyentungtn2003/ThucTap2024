package com.cinema.demo.security.repository;

import com.cinema.demo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    Optional<UserEntity> findByEmailAndProviderId(String email, String providerId);
}
