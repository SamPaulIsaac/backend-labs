package com.sam.taskManagement.service.userAuthAndRegistration;


import com.sam.taskManagement.domain.userAuthAndRegistration.Role;
import com.sam.taskManagement.domain.userAuthAndRegistration.User;
import com.sam.taskManagement.domain.userAuthAndRegistration.UserRole;
import com.sam.taskManagement.exception.UserAlreadyExistException;
import com.sam.taskManagement.exception.UserNotFoundException;
import com.sam.taskManagement.repository.userAuthAndRegistration.UserRepository;
import com.sam.taskManagement.utils.TaskManagementUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(String username, String password, List<Role> roles) {

        if (userRepository.findByUsername(username).isPresent())
            throw new UserAlreadyExistException("Requested user already exist.");

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .userRoles(roles.stream()
                        .map(this::buildUserRole)
                        .collect(Collectors.toList()))
                .createdBy(TaskManagementUtils.NAME)
                .updatedBy(TaskManagementUtils.NAME)
                .createdAt(TaskManagementUtils.getCurrentDateTime())
                .updatedAt(TaskManagementUtils.getCurrentDateTime())
                .build();

        user.getUserRoles().forEach(userRole -> userRole.setUser(user));
        userRepository.save(user);
        log.info("User registered successfully.");
    }

    private UserRole buildUserRole(Role role) {
        return UserRole.builder()
                .role(role)
                .createdBy(TaskManagementUtils.NAME)
                .updatedBy(TaskManagementUtils.NAME)
                .createdAt(TaskManagementUtils.getCurrentDateTime())
                .updatedAt(TaskManagementUtils.getCurrentDateTime()).build();
    }

    public boolean authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Requested user not found"));
        return passwordEncoder.matches(password, user.getPassword());
    }

    public List<Role> authenticateAndGetRole(String username, String password) {
        // Validate user credentials (this could be a database check)
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(password, user.getPassword())) {
            return user.getUserRoles().stream().map(UserRole::getRole).collect(Collectors.toList()); // Return the user's role (e.g., ROLE_ADMIN or ROLE_USER)
        }
        return null; // Authentication failed
    }

}

