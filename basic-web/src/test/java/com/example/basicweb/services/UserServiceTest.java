package com.example.basicweb.services;

import com.example.basicweb.domain.User;
import com.example.basicweb.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        userService = new UserService(userRepository);
    }

    @Test
    public void loadUserByUsername_HappyPath_ShouldReturnOneUserDetails() {
        User user = new User();
        user.setUsername("test_user");
        user.setPassword("test_password");
        user.setRole("USER");

        when(userRepository.findByUsername("test_user")).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("test_user");

        verify(userRepository, times(1)).findByUsername("test_user");
        assertNotNull(userDetails);
    }
}