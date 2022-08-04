package com.mystays.authorizationserver.controller;

import com.mystays.authorizationserver.config.CustomAuthenticationManager;
import com.mystays.authorizationserver.entity.User;
import com.mystays.authorizationserver.model.AuthenticationRequest;
import com.mystays.authorizationserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class AuthenticationController {

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initUsers() {

        String password1 = passwordEncoder.encode("password");
        String password2 = passwordEncoder.encode("pwd1");
        String password3 = passwordEncoder.encode("pwd2");
        String password4 = passwordEncoder.encode("pwd3");
        List<User> users = Stream.of(
                new User(101, "rithin", password1, "rithin@gmail.com"),
                new User(102, "user1", password2, "user1@gmail.com"),
                new User(103, "user2", password3, "user2@gmail.com"),
                new User(104, "user3", password4, "user3@gmail.com")
        ).collect(Collectors.toList());
        userRepository.saveAll(users);
    }

    @PostMapping("/authenticate")
    public String authenticateUser(@RequestBody AuthenticationRequest authRequest) throws Exception {
        final Authentication authentication = customAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
            // set authentication in security context holder
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "Authenticated" ;
        }
        else return "Not Authenticated";
    }
}
