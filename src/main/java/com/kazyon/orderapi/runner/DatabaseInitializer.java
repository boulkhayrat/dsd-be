package com.kazyon.orderapi.runner;

import com.kazyon.orderapi.model.User;
import com.kazyon.orderapi.security.WebSecurityConfig;
import com.kazyon.orderapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userService.getUsers().isEmpty()) {
            return;
        }
        USERS.forEach(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
        });
        log.info("Database initialized");
    }

    private static final List<User> USERS = Arrays.asList(
            new User("admin", "K@zyon2024", "Admin", "admin@kazyon.com", WebSecurityConfig.ADMIN),
            new User("manager","K@zyon2025","Manager","manager@kazyon.com",WebSecurityConfig.MANAGER),
            new User("user", "user", "User", "user@kazyon.com", WebSecurityConfig.STORE)
    );
}
