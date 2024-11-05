package com.example.ThucTap2024.repository;

import com.example.ThucTap2024.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}