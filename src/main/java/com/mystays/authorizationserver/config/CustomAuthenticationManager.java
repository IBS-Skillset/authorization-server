package com.mystays.authorizationserver.config;

import com.mystays.authorizationserver.service.AuthenticationServerUserDetailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private AuthenticationServerUserDetailService authenticationServerUserDetailService;
    private PasswordEncoder passwordEncoder;

    public CustomAuthenticationManager(AuthenticationServerUserDetailService authenticationServerUserDetailService ,PasswordEncoder passwordEncoder){
        this.authenticationServerUserDetailService = authenticationServerUserDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final UserDetails userDetail = authenticationServerUserDetailService.loadUserByUsername(authentication.getName());
        if (!passwordEncoder.matches(authentication.getCredentials().toString(), userDetail.getPassword())) {
            throw new BadCredentialsException("Invalid Credentials");

        }
        return new UsernamePasswordAuthenticationToken(userDetail.getUsername(), userDetail.getPassword(), userDetail.getAuthorities());
    }

}