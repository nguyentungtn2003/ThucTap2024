package com.cinema.demo.security.repository;

import com.cinema.demo.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepositorySecurity extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleName(String name);
}
