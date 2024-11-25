//package com.cinema.demo.security.config;
//
//import com.cinema.demo.entity.RoleEntity;
//import com.cinema.demo.security.repository.IRoleRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Component
//@RequiredArgsConstructor
//public class DataLoader implements CommandLineRunner {
//
//    private final IRoleRepository IRoleRepository;
//
//    @Override
//    public void run(String... args) {
//        String[] roles = {"ROLE_USER", "ROLE_ADMIN", "ROLE_STAFF"};
//        Arrays.stream(roles).forEach(role -> {
//            IRoleRepository.findByRoleName(role).orElseGet(() -> {
//                RoleEntity roleEntity = new RoleEntity();
//                roleEntity.setRoleName(role);
//                return IRoleRepository.save(roleEntity);
//            });
//        });
//    }
//}
