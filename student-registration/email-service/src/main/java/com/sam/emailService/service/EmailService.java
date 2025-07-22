package com.sam.emailService.service;

import com.sam.emailService.dto.RabbitMQRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public CompletableFuture<Boolean> sendRegistrationEmail(RabbitMQRequestDto rabbitMQRequestDto) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(rabbitMQRequestDto.getEmail());
            message.setSubject(rabbitMQRequestDto.getSubject());
            message.setText(rabbitMQRequestDto.getBody());
            log.info("BEFORE SEND - Simulating a delay of 7 seconds to validate the async.");
            Thread.sleep(7000);
            javaMailSender.send(message);
            log.info("EMAIL SENT!");
        } catch (Exception e) {
            log.info("Failed to send an email: {}", e.getMessage());
            return CompletableFuture.completedFuture(false);
        }
        return CompletableFuture.completedFuture(true);
    }
}
