package com.example.authserver.Controller;

import com.example.authserver.entity.User;
import com.example.authserver.repository.UserRepository;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;


@RestController
public class RegistrationController {


    private final UserRepository userRepository;

    public RegistrationController(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @PostConstruct
    @Transactional
    public void initUsers() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setFirstName("TestFirstName");
        user.setLastName("TestLastName");
        user.setRole("USER");
        user.setPassword("test");
        userRepository.save(user);
    }
}

