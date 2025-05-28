package com.sam.userService.controller;

import com.sam.userService.dto.request.LoginRequestDTO;
import com.sam.userService.dto.request.TokenRefreshRequest;
import com.sam.userService.dto.request.UserRegisterRequestDto;
import com.sam.userService.dto.response.JwtAuthenticationResponse;
import com.sam.userService.dto.response.LoginResponseDTO;
import com.sam.userService.dto.response.UserRegisterResponseDto;
import com.sam.userService.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> registerUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        log.info("Request received to register user.");
        return ResponseEntity.ok(userService.registerUser(userRegisterRequestDto));
    }


    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestParam String token) {
        log.info("Request received to verify token.");
        return ResponseEntity.ok(userService.verifyToken(token));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> userLogin(@RequestBody LoginRequestDTO loginRequestDTO) {
        log.info("Requested received to login user with request body: {} ", loginRequestDTO);
        LoginResponseDTO loginResponseDTO = userService.userLogin(loginRequestDTO);
        log.info("Response: {} ", loginRequestDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        log.info("Requested received to refresh token: {} ", request);
        JwtAuthenticationResponse response = userService.refreshJwtToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

}
