package com.mystays.authorizationserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mystays.authorizationserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class OidcUserInfoService {

    private UserRepository userRepository;

    public OidcUserInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public OidcUserInfo loadUser(String id) {
        return new OidcUserInfo(userDetails(id));
    }

    private Map<String, Object> userDetails(String id) {
        var user = userRepository.findById(Long.valueOf(id));
        OidcUserInfo.Builder info =
                OidcUserInfo.builder()
                        .subject(id)
                        .name(user.get().getFirstName() + " " + user.get().getLastName())
                        .givenName(user.get().getFirstName())
                        .familyName(user.get().getLastName())
                        .email((user.get().getEmail()))
                        .profile(user.get().getRole())
                        .phoneNumber(user.get().getPhone());
        try {
            info.claim("address", new ObjectMapper().writeValueAsString(user.get().getAddress()));
        } catch (Exception e) {
            log.error("Exception occurred while creating oidc token " + e);
        }
        return info.build()
                .getClaims();
    }
}

