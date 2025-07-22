package com.sam.userService.service;

import com.sam.userService.entity.RefreshToken;
import com.sam.userService.entity.User;
import com.sam.userService.repository.RefreshTokenRepository;
import com.sam.userService.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

@Service
@AllArgsConstructor
@Slf4j
public class RefreshTokenService {

//    @Value("${app.jwt.refreshExpirationMs}")
//    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public RefreshToken createRefreshToken(String username) {
        log.info("Create Refresh token is invoked.");
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        log.info("Call to delete existing refresh token for user.");
        // Optionally delete existing refresh tokens for user to avoid duplicates
        deleteRefreshTokensByUser(user);
        //int deletedToken = refreshTokenRepository.deleteByUser(user);
        log.info("Deleted existing refresh token for user.");

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .expiryDate(Instant.now().plusMillis(604800000))
                .token(UUID.randomUUID().toString()).build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional(value = REQUIRES_NEW)
    public int deleteRefreshTokensByUser(User user) {
        return refreshTokenRepository.deleteByUser(user);
    }

    /**
     * Retrieves a refresh token entity by token string.
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Verifies if the refresh token has expired.
     * Deletes token if expired and throws an exception.
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired. Please login again.");
        }
        return token;
    }

    /**
     * Deletes all refresh tokens associated with a given user.
     */
    public int deleteByUser(User user) {
        return refreshTokenRepository.deleteByUser(user);
    }
}

