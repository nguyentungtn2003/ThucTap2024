package com.cinema.demo.repository;

import com.cinema.demo.entity.RoleEntity;
import com.cinema.demo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    boolean existsByEmail(String email);

    Page<UserEntity> findByRolesContaining(RoleEntity role, Pageable pageable);

}