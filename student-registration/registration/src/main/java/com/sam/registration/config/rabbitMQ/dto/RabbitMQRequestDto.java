package com.sam.registration.config.rabbitMQ.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RabbitMQRequestDto {
    private Long studentId;
    private String email;
    private String subject;
    private String body;
    private String correlationId;
}

