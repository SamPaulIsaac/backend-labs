package com.sam.taskManagement.controller.userAuthAndRegistration;


import com.sam.taskManagement.domain.userAuthAndRegistration.Role;
import com.sam.taskManagement.service.userAuthAndRegistration.UserService;
import com.sam.taskManagement.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthAndRegistrationController {

    private UserService userService;

    @PostMapping("/signUp")
    public String signUp(@RequestParam String username, @RequestParam String password, @RequestParam List<Role> role) {

        log.info("Request received for signup: {}", username);
        userService.registerUser(username, password, role);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {

        log.info("Request received for login: {}", username);
        List<Role> roles = userService.authenticateAndGetRole(username, password);
        if (!roles.isEmpty()) return JwtUtil.generateToken(username, roles); // Generate and return JWT token
        else throw new RuntimeException("Invalid username or password");
    }
}

