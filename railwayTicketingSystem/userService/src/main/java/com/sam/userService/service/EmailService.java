package com.sam.userService.service;

import com.sam.userService.entity.User;
import com.sam.userService.entity.VerificationToken;
import com.sam.userService.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class EmailService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final JavaMailSender mailSender;


    @Async
    public void sendVerificationEmail(User user) {
        log.info("Sending verification email.");
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder().user(user).expiryDate(LocalDateTime.now().plusMinutes(2)).token(token).build();
        verificationTokenRepository.saveAndFlush(verificationToken);
        String verificationUrl = "http://localhost:8080/api/user/verify?token=" + token;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Verify your email");
        mailMessage.setText("Click the link to verify your account: " + verificationUrl);
        mailSender.send(mailMessage);
        log.info("Verification email sent.");
    }
}
