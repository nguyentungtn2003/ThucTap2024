package com.example.ThucTap2024.security.service;

import com.example.ThucTap2024.entity.UserEntity;
import com.example.ThucTap2024.services.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface ImplUserService extends IGeneralService<UserEntity>, UserDetailsService {
    Optional<UserEntity> findByUsername(String username);
}
