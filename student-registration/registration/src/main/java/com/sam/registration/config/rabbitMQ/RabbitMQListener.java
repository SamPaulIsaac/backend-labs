package com.sam.registration.config.rabbitMQ;

import com.sam.registration.config.rabbitMQ.dto.RabbitMQResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQListener {

    @RabbitListener(queues = "email-response-queue")
    public void receiveEnrollmentMessage(RabbitMQResponseDto rabbitMQResponseDto) {
        log.info("Received Enrollment message: {}", rabbitMQResponseDto);
    }
}

