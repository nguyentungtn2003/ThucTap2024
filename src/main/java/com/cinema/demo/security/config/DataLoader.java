package com.cinema.demo.security.config;

import com.cinema.demo.entity.RoleEntity;
import com.cinema.demo.security.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        String[] roles = {"ROLE_USER", "ROLE_ADMIN", "ROLE_STAFF"};
        Arrays.stream(roles).forEach(role -> {
            roleRepository.findByName(role).orElseGet(() -> {
                RoleEntity roleEntity = new RoleEntity();
                roleEntity.setName(role);
                return roleRepository.save(roleEntity);
            });
        });
    }
}
