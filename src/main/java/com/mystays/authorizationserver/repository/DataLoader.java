package com.mystays.authorizationserver.repository;

import com.mystays.authorizationserver.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * To add user data for testing purpose
 */
@Component
public class DataLoader implements ApplicationRunner {

    private UserRepository userRepository;

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run(ApplicationArguments args) {
        User user = new User();
        user.setId(1);
        user.setEmail("test@gmail.com");
        user.setFirstName("TestFirstName");
        user.setLastName("TestLastName");
        user.setRole("USER");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        userRepository.save(user);
    }
}