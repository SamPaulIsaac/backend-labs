package com.sam.customerbatchprocessor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "failed_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FailedRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "json")
    private String originalData;

    private String errorMessage;

    private String jobName;

    private Long jobExecutionId;

    private String fileReference;

    private LocalDateTime failedAt = LocalDateTime.now();
}
