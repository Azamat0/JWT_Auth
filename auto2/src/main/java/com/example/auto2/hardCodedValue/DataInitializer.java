package com.example.auto2.hardCodedValue;

import com.example.auto2.model.UserD;
import com.example.auto2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        UserD user = new UserD();
        user.setName("user");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRole("ROLE_USER");
        userRepository.save(user);

        UserD admin = new UserD();
        admin.setName("admin");
        admin.setPassword(passwordEncoder.encode("password"));
        admin.setRole("ROLE_ADMIN");
        userRepository.save(admin);

//        User superAdmin = new User();
//        superAdmin.setName("superAdmin");
//        superAdmin.setPassword(passwordEncoder.encode("password"));
//        superAdmin.setRole("ROLE_ADMIN", "ROLE_USER");
//        userRepository.save(superAdmin);
    }
}