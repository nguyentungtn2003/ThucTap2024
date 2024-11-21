package com.cinema.demo.security.service.impl;

import com.cinema.demo.security.helpers.Helper;
import com.cinema.demo.security.service.EmailService;
import lombok.RequiredArgsConstructor;
import com.cinema.demo.entity.Provider;
import com.cinema.demo.entity.UserEntity;
import com.cinema.demo.entity.RoleEntity;
import com.cinema.demo.security.exception.BaseException;
import com.cinema.demo.security.repository.RoleRepository;
import com.cinema.demo.security.repository.UserRepository;
import com.cinema.demo.security.request.UserDTO;
import com.cinema.demo.security.response.BaseResponse;
import com.cinema.demo.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Helper helper;


    @Override
    public BaseResponse registerAccount(UserDTO userDTO) {
        BaseResponse response = new BaseResponse();

        //validate data from client
        validateAccount(userDTO);

        UserEntity user = insertUser(userDTO);

        try {
            userRepository.save(user);
            response.setCode(String.valueOf(HttpStatus.CREATED.value()));
            response.setMessage("Register account successfully!!!");
        }catch (Exception e){
            response.setCode(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
            response.setMessage("Service Unavailable");
            //throw new BaseException(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), "Service Unavailable");
        }
        return response;
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        RoleEntity userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new BaseException("500", "Role USER not found"));

        user.setRoles(new HashSet<>());
        user.getRoles().add(userRole);


        user.setEnabled(false);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setProviderId(Provider.local.name());

        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        UserEntity savedUser = userRepository.save(user);
        String emailLink = helper.getLinkForEmailVerificatiton(emailToken);
        emailService.sendEmail(savedUser.getEmail(), "Verify Account : Smart  Contact Manager", emailLink);

        user.setAddress(user.getAddress());
        user.setDob(user.getDob());
        user.setStatus(user.getStatus() != null ? user.getStatus() : "Active");
        user.setSex(user.getSex());
        return savedUser;

    }

    private UserEntity insertUser(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setAddress(userDTO.getAddress());
        user.setDob(userDTO.getDob());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setStatus(userDTO.getStatus() != null ? userDTO.getStatus() : "active");
        if (userDTO.getSex() != null) {
            user.setSex(userDTO.getSex());
        }

        RoleEntity userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new BaseException("500", "Role USER not found"));

        user.setRoles(new HashSet<>());
        user.getRoles().add(userRole);


        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setProviderId(Provider.local.name());

        return user;
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);

    }

    private void validateAccount(UserDTO userDTO){
        if(ObjectUtils.isEmpty(userDTO)){
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Request data not found!");
        }

        try {
            if(!ObjectUtils.isEmpty(userDTO.checkProperties())){
                throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Request data not found!");
            }
        }catch (IllegalAccessException e){
            throw new BaseException(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), "Service Unavailable");
        }

    }
}