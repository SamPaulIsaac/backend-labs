package com.sam.emailService.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMQResponseDto {
    private Long studentId;
    private String correlationId;
    private boolean emailStatus;
}

