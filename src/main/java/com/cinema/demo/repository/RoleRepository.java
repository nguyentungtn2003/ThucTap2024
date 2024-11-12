package com.cinema.demo.repository;

import com.cinema.demo.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRoleName(String roleName);
}