package com.sam.userService.service;

import com.sam.userService.dto.request.LoginRequestDTO;
import com.sam.userService.dto.request.UserRegisterRequestDto;
import com.sam.userService.dto.response.JwtAuthenticationResponse;
import com.sam.userService.dto.response.LoginResponseDTO;
import com.sam.userService.dto.response.UserRegisterResponseDto;
import com.sam.userService.entity.*;
import com.sam.userService.exceptions.TokenExpiredException;
import com.sam.userService.repository.RoleRepository;
import com.sam.userService.repository.UserRepository;
import com.sam.userService.repository.VerificationTokenRepository;
import com.sam.userService.utility.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;


    @Transactional
    public UserRegisterResponseDto registerUser(UserRegisterRequestDto requestDto) {
        User newUser = modelMapper.map(requestDto, User.class);
        newUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        newUser.setStatus(UserStatus.PENDING_VERIFICATION);
        Role passengerRole = roleRepository.findByName("PASSENGER")
                .orElseThrow(() -> new RuntimeException("Role 'PASSENGER' not found"));
        newUser.setRoles(Collections.singletonList(passengerRole));

        User registeredUser = userRepository.saveAndFlush(newUser);
        // Async logic has been moved to a separate service class (EmailService)
        // because self-invocation of @Async methods within the same class does not trigger asynchronous behavior.
        // Spring AOP proxies only intercept external method calls via a Spring-managed bean.
        emailService.sendVerificationEmail(registeredUser);
        return UserRegisterResponseDto.builder().userId(registeredUser.getId()).message("Registration successful. Please verify your email.").verificationSent(true).build();
    }


    @Transactional
    public String verifyToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new EntityNotFoundException("Verification token does not exist."));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now()))
            throw new TokenExpiredException("Token Expired.");

        if (verificationToken.isUsed())
            throw new TokenExpiredException("Invalid token, as it is used already.");

        User user = userRepository.findById(verificationToken.getUser().getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("User does not exist for requested id: {}." +
                                verificationToken.getUser().getId()));

        user.setStatus(UserStatus.ACTIVE);
        userRepository.saveAndFlush(user);
        log.info("Updated the status for the user: {}", user.getName());

        verificationToken.setUsed(true);
        verificationTokenRepository.saveAndFlush(verificationToken);
        log.info("Updated the used status for the verification token: {}", verificationToken.getId());
        return "Successfully verified the token.";
    }

    public LoginResponseDTO userLogin(LoginRequestDTO loginRequestDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getName(),
                        loginRequestDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());
        return LoginResponseDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .jwtToken(jwtUtils.generateJwtToken(authentication))
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public JwtAuthenticationResponse refreshJwtToken(String refreshToken) {

        RefreshToken token = refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new RuntimeException("Refresh token is invalid or expired"));

        User user = token.getUser();
        String newJwt = jwtUtils.generateJwtToken(user.getName());

        return JwtAuthenticationResponse
                .builder()
                .accessToken(newJwt)
                .refreshToken(refreshToken)
                .build();
    }
}
