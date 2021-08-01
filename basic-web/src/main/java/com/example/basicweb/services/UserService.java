package com.example.basicweb.services;

import com.example.basicweb.domain.User;
import com.example.basicweb.repository.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if(user == null) throw new UsernameNotFoundException("User not found : " + s);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(user.getRole()))
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public User create(User user){
        return userRepository.save(user);
    }
}
