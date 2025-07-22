package com.sam.emailService.config.rabbitMQ;

import com.sam.emailService.dto.RabbitMQRequestDto;
import com.sam.emailService.dto.RabbitMQResponseDto;
import com.sam.emailService.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class RabbitMQListener {

    private RabbitTemplate rabbitTemplate;
    private EmailService emailService;


    @RabbitListener(queues = "email-request-queue")
    public void receiveEnrollmentMessage(RabbitMQRequestDto rabbitMQRequestDto) throws ExecutionException, InterruptedException {

        log.info("Received Enrollment request: {} ", rabbitMQRequestDto);

        boolean isEmailSent = emailService.sendRegistrationEmail(rabbitMQRequestDto).get();


        RabbitMQResponseDto rabbitMQResponseDto
                = RabbitMQResponseDto.builder()
                .studentId(rabbitMQRequestDto.getStudentId())
                .emailStatus(isEmailSent)
                .correlationId(rabbitMQRequestDto.getCorrelationId())
                .build();


        rabbitTemplate.convertAndSend("email-response-queue", rabbitMQResponseDto);

        log.info("Email status sent to response queue: {}", rabbitMQResponseDto);
    }
}

