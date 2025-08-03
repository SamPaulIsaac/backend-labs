package com.sam.user_service.controller;

import com.sam.user_service.entity.User;
import com.sam.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public User createUser(@RequestBody User user) {
        log.info("POST /api/user");
        return userService.createUser(user);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        log.info("GET /api/user");
        return userService.getAllUsers();
    }
}

