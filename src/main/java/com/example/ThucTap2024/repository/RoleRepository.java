package com.example.ThucTap2024.repository;

import com.example.ThucTap2024.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}