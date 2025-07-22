package com.sam.registration.config.rabbitMQ;


import com.sam.registration.config.rabbitMQ.dto.RabbitMQRequestDto;
import com.sam.registration.entity.Student;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    public void sendEmailRequest(Student student) {

        String correlationId = UUID.randomUUID().toString();

        RabbitMQRequestDto rabbitMQRequestDto = RabbitMQRequestDto.builder()
                .studentId(student.getId())
                .email(student.getEmail())
                .subject("RE: Enrollment")
                .body("Registration successful.")
                .correlationId(correlationId)
                .build();

        rabbitTemplate.convertAndSend("email-request-queue", rabbitMQRequestDto);

        log.info("Email request sent for student: {} ", rabbitMQRequestDto);
    }
}

