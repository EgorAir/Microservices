package com.example.securservice.repository;

import com.example.securservice.domain.Role;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final List<User> userList = Arrays.asList(
            new User(
                    "user",
                    new BCryptPasswordEncoder().encode("123456"),
                    Collections.singleton(Role.USER))
    );

    public Optional<User> findByUserName(String userName) {
        return userList.stream()
                .filter(user -> user.getUsername().equals(userName))
                .findFirst();
    }

}

