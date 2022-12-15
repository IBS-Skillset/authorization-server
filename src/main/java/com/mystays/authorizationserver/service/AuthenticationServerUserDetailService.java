package com.mystays.authorizationserver.service;

import com.mystays.authorizationserver.entity.User;
import com.mystays.authorizationserver.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;

@Service
public class AuthenticationServerUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    public AuthenticationServerUserDetailService (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(emailId);
        if(ObjectUtils.isEmpty(user)){
            throw new UsernameNotFoundException("Invalid User");
        }
        return new org.springframework.security.core.userdetails.User(String.valueOf(user.getId()), user.getPassword(), new ArrayList<>());
    }
}
