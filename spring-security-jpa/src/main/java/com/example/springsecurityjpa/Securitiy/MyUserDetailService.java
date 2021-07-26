package com.example.springsecurityjpa.Securitiy;

import com.example.springsecurityjpa.domain.MyUserDetail;
import com.example.springsecurityjpa.domain.User;
import com.example.springsecurityjpa.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    UserRepository userRepository;

    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(s);
        if(user == null)
            throw new UsernameNotFoundException("User " + s + " Not found.");
        return new MyUserDetail(user);
    }
}
