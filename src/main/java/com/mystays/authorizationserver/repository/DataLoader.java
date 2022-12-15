package com.mystays.authorizationserver.repository;

import com.mystays.authorizationserver.entity.User;
import com.mystays.authorizationserver.entity.UserAddress;
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
    private final AddressRepository addressRepository;

    public DataLoader(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public void run(ApplicationArguments args) {
        User user = new User();
        user.setId(1);
        user.setEmail("test@gmail.com");
        user.setFirstName("TestFirstName");
        user.setLastName("TestLastName");
        user.setRole("USER");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));

        UserAddress address =new UserAddress();
        address.setAddress("Home address");
        address.setType("Permanent");
        address.setCity("New york");
        address.setCountry("USA");
        address.setZipcode("846983");
        address.setUser(userRepository.save(user));
        addressRepository.save(address);
    }
}