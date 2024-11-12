package com.cinema.demo.service.impl;

import com.cinema.demo.dto.UpdateUserDto;
import com.cinema.demo.dto.UserDto;
import com.cinema.demo.entity.RoleEntity;
import com.cinema.demo.entity.UserEntity;
import com.cinema.demo.repository.RoleRepository;
import com.cinema.demo.repository.UserRepository;
import com.cinema.demo.service.IUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IUserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public IUserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
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
    public void updateUser(UpdateUserDto UpdateUserDto) {
        // Tìm người dùng trong cơ sở dữ liệu
        UserEntity userEntity = userRepository.findByEmail(UpdateUserDto.getEmail());

        if (userEntity != null) {
            // Cập nhật thông tin người dùng
            userEntity.setFullName(UpdateUserDto.getFullName());
            userEntity.setAddress(UpdateUserDto.getAddress());
            userEntity.setPhoneNumber(UpdateUserDto.getPhoneNumber());
            userEntity.setSex(UpdateUserDto.getSex());
            userEntity.setDob(UpdateUserDto.getDob());

            userRepository.save(userEntity);  // Lưu lại thông tin đã cập nhật

        }
    }


    @Override
    public void updatePassword(UserEntity userEntity, String newPassword) {
        // Mã hóa mật khẩu mới trước khi lưu
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedNewPassword = passwordEncoder.encode(newPassword);

        // Cập nhật mật khẩu mới
        userEntity.setPassword(encodedNewPassword);
        userRepository.save(userEntity); // Lưu lại thực thể với mật khẩu đã cập nhật
    }


    public boolean resetPassword(String email, String newPassword) {
        // Tìm người dùng theo email
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            // Cập nhật mật khẩu
            user.setPassword(newPassword);  // Mật khẩu đã được mã hóa trước đó
            userRepository.save(user);  // Lưu người dùng với mật khẩu mới
            return true;
        }
        return false;  // Nếu không tìm thấy người dùng
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
