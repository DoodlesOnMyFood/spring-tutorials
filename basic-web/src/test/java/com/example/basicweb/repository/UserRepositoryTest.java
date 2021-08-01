package com.example.basicweb.repository;

import com.example.basicweb.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsername_HappyPath_ShouldReturnUser() throws Exception{
        User user = new User();
        user.setUsername("test_user");
        user.setPassword("test_password");
        user.setRole("USER");
        testEntityManager.persist(user);
        testEntityManager.flush();

        User actual = userRepository.findByUsername("test_user");

        assertEquals(actual, user);
    }

    @Test
    public void save_HappyPath_ShouldSaveUser() throws Exception{
        User user = new User();
        user.setUsername("test_user");
        user.setPassword("test_password");
        user.setRole("USER");

        User actual = userRepository.save(user);
        assertNotNull(actual);
        assertNotNull(actual.getId());
    }




}