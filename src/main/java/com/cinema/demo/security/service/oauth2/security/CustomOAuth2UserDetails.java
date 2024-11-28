package com.cinema.demo.security.service.oauth2.security;

import com.cinema.demo.entity.RoleEntity;
import com.cinema.demo.entity.UserEntity;
import com.cinema.demo.security.exception.BaseException;
import com.cinema.demo.security.repository.RoleRepositorySecurity;
import com.cinema.demo.security.repository.UserRepositorySecurity;
import com.cinema.demo.security.service.oauth2.OAuth2UserDetails;
import com.cinema.demo.security.service.oauth2.OAuth2UserDetailsFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserDetails extends DefaultOAuth2UserService {

    private final UserRepositorySecurity userRepositorySecurity;

    private final RoleRepositorySecurity roleRepositorySecurity;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return checkingOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException e){
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User checkingOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserDetails oAuth2UserDetails =
                OAuth2UserDetailsFactory.getOAuth2UserDetails(
                        oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                        oAuth2User.getAttributes()
                );
        if (ObjectUtils.isEmpty(oAuth2UserDetails)) {
            throw new BaseException("400", "Can't find any OAuth2 user");
        }

        Optional<UserEntity> user = userRepositorySecurity.findByEmailAndProviderId(
                oAuth2UserDetails.getEmail(),
                oAuth2UserRequest.getClientRegistration().getRegistrationId());
        UserEntity userDetails;
        if (user.isPresent()) {
            userDetails = user.get();
            if (!userDetails.getProviderId().equals(oAuth2UserRequest.getClientRegistration().getRegistrationId())){
                throw new BaseException("400", "Invalid site login with " + userDetails.getProviderId());
            }

            userDetails = updateOAuth2UserDetails(userDetails, oAuth2UserDetails);
        } else {
            userDetails = registerOAuth2UserDetails(oAuth2UserRequest, oAuth2UserDetails);
        }
        return new OAuth2UserDetailsCustom(
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getPassword(),
                userDetails.getRoles().stream()
                        .filter(role -> role != null && role.getRoleName() != null)
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
    }

    public UserEntity registerOAuth2UserDetails(OAuth2UserRequest oAuth2UserRequest, OAuth2UserDetails oAuth2UserDetails) {
        UserEntity user = new UserEntity();
        user.setEmail(oAuth2UserDetails.getEmail());
        user.setName(oAuth2UserDetails.getName());
        user.setProviderId(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);

        RoleEntity userRole = roleRepositorySecurity.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new BaseException("500", "Role USER not found"));

        user.setRoles(new HashSet<>());
        user.getRoles().add(userRole);

        user.setStatus("Active");
        return userRepositorySecurity.save(user);
    }


    public UserEntity updateOAuth2UserDetails(UserEntity user, OAuth2UserDetails oAuth2UserDetails){
        user.setEmail(oAuth2UserDetails.getEmail());
        return userRepositorySecurity.save(user);
    }
}
