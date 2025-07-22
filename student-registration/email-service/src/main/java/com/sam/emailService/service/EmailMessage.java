package com.sam.emailService.service;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessage {
    private Long studentId;
    private String email;
    private String subject;
    private String body;
    private String correlationId;
    private boolean emailStatus;

}

