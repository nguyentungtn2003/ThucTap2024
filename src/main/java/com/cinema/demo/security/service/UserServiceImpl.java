package com.cinema.demo.security.service;

import com.cinema.demo.security.dto.UserDto;
import com.cinema.demo.entity.RoleEntity;
import com.cinema.demo.entity.UserEntity;
import com.cinema.demo.security.repository.RoleRepository;
import com.cinema.demo.security.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setDob(userDto.getDob());
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setStatus(userDto.getStatus() != null ? userDto.getStatus() : "active");

        if (userDto.getSex() != null) {
            user.setSex(userDto.getSex());
        }
        //encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        RoleEntity role = roleRepository.findByRoleName("ROLE_USER");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    private RoleEntity checkRoleExist() {
        RoleEntity role = new RoleEntity();
        role.setRoleName("ROLE_USER");
        return roleRepository.save(role);
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDto findUserDtoByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            return convertEntityToDto(user);
        }
        return null;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean updateUser(UserDto userDto) {
        // Tìm người dùng trong cơ sở dữ liệu
        UserEntity userEntity = userRepository.findByEmail(userDto.getEmail());

        if (userEntity != null) {
            // Cập nhật thông tin người dùng
            userEntity.setFullName(userDto.getFullName());
            userEntity.setAddress(userDto.getAddress());
            userEntity.setPhoneNumber(userDto.getPhoneNumber());
            userEntity.setSex(userDto.getSex());
            userEntity.setDob(userDto.getDob());

            userRepository.save(userEntity);  // Lưu lại thông tin đã cập nhật
            return true;  // Trả về true nếu cập nhật thành công
        } else {
            return false;  // Trả về false nếu không tìm thấy người dùng
        }
    }
    private UserDto convertEntityToDto(UserEntity user) {
        UserDto userDto = new UserDto();

        userDto.setId((long) user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setAddress(user.getAddress());
        userDto.setDob(user.getDob());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setStatus(user.getStatus());
        userDto.setFullName(user.getFullName());
        userDto.setSex(user.getSex());

        List<String> roleNames = new ArrayList<>();
        for (RoleEntity role : user.getRoles()) {
            roleNames.add(role.getRoleName());
        }
        userDto.setRoles(roleNames);

        return userDto;
    }

}
