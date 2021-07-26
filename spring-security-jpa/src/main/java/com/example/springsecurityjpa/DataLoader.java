package com.example.springsecurityjpa;

import com.example.springsecurityjpa.domain.User;
import com.example.springsecurityjpa.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader implements CommandLineRunner {

    UserRepository userRepository;
    PasswordEncoder encoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        insert();
    }

    private void insert(){
        User user = new User();
        user.setId(1L);
        user.setPassword(encoder.encode("password1"));
        user.setUsername("user");
        user.setActive(true);
        user.setRole("ROLE_ADMIN");
        userRepository.save(user);
    }
}
