package com.example.ThucTap2024.security.repository;

import com.example.ThucTap2024.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImplRoleRepo extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findByName(String name);
}
