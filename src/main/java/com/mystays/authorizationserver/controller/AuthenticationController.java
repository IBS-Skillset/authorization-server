package com.mystays.authorizationserver.controller;

import com.mystays.authorizationserver.config.CustomAuthenticationManager;
import com.mystays.authorizationserver.entity.User;
import com.mystays.authorizationserver.model.AuthenticationRequest;
import com.mystays.authorizationserver.model.AuthenticationResponse;
import com.mystays.authorizationserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Validated
@Slf4j
public class AuthenticationController {

    private CustomAuthenticationManager customAuthenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    public AuthenticationController(CustomAuthenticationManager customAuthenticationManager ,UserRepository userRepository ,PasswordEncoder passwordEncoder){
        this.customAuthenticationManager = customAuthenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initUsers() {
        List<User> users = Stream.of(
                new User(101, "Rithin" ,"Remash", passwordEncoder.encode("password"), "rithin@gmail.com" ,"USER" ,true),
                new User(102, "Abhishek" ,"Ravindran", passwordEncoder.encode("pwd1"), "abhishek@gmail.com" ,"USER" ,true),
                new User(103, "Noel" ,"Shibu", passwordEncoder.encode("pwd2"), "noel@gmail.com" ,"USER" ,true)
        ).collect(Collectors.toList());
        userRepository.saveAll(users);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@Valid @RequestBody AuthenticationRequest authRequest) {
        log.info("Authenticate endpoint called");
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        final Authentication authentication = customAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmailId(), authRequest.getPassword()));
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
            // set authentication in security context holder
            SecurityContextHolder.getContext().setAuthentication(authentication);
            authenticationResponse.setStatus("Authenticated");
        }
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK) ;
    }
}
