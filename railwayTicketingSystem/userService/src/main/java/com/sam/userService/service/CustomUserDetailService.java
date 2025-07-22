package com.sam.userService.service;

import com.sam.userService.entity.CustomUserDetails;
import com.sam.userService.entity.Role;
import com.sam.userService.entity.User;
import com.sam.userService.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Load user by username is invoked!");
        User user = userRepository.findByName(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        log.info("User loaded: {}", user);

        Set<Role> roles = new HashSet<>(user.getRoles()); // avoid Hibernate proxy concurrency issue

        Set<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        authorities.forEach(auth -> System.out.println("Authority: " + auth.getAuthority()));
        return CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getName())
                .password(user.getPassword())
                .email(user.getEmail())
                .authorities(authorities)
                .build();
    }
}
