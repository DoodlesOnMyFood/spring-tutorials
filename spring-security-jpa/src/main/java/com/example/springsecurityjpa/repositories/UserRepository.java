package com.example.springsecurityjpa.repositories;

import com.example.springsecurityjpa.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
}
