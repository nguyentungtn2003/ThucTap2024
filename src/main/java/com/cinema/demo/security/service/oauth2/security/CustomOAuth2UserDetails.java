package com.cinema.demo.security.service.oauth2.security;

import com.cinema.demo.entities.RoleEntity;
import lombok.RequiredArgsConstructor;
import com.cinema.demo.entities.UserEntity;
import com.cinema.demo.security.exception.BaseException;
import com.cinema.demo.security.repository.RoleRepository;
import com.cinema.demo.security.repository.UserRepository;
import com.cinema.demo.security.service.oauth2.OAuth2UserDetails;
import com.cinema.demo.security.service.oauth2.OAuth2UserDetailsFactory;
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

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

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

        Optional<UserEntity> user = userRepository.findByEmailAndProviderId(
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
                        .filter(role -> role != null && role.getName() != null)
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }

    public UserEntity registerOAuth2UserDetails(OAuth2UserRequest oAuth2UserRequest, OAuth2UserDetails oAuth2UserDetails) {
        UserEntity user = new UserEntity();
        user.setEmail(oAuth2UserDetails.getEmail());
        user.setProviderId(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);

        RoleEntity userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new BaseException("500", "Role USER not found"));

        user.setRoles(new HashSet<>());
        user.getRoles().add(userRole);

        return userRepository.save(user);
    }


    public UserEntity updateOAuth2UserDetails(UserEntity user, OAuth2UserDetails oAuth2UserDetails){
        user.setEmail(oAuth2UserDetails.getEmail());
        return userRepository.save(user);
    }
}
