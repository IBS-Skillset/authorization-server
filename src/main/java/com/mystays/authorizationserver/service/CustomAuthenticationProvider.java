package com.mystays.authorizationserver.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationServerUserDetailService authenticationServerUserDetailService;

    public CustomAuthenticationProvider(AuthenticationServerUserDetailService authenticationServerUserDetailService) {
        this.authenticationServerUserDetailService = authenticationServerUserDetailService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails user = authenticationServerUserDetailService.loadUserByUsername(username);
        return checkPassword(user, password);
    }

    private Authentication checkPassword(UserDetails user, String rawPassword) {
        if (rawPassword.equals(user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities());
        } else {
            throw new BadCredentialsException("Bad Credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
