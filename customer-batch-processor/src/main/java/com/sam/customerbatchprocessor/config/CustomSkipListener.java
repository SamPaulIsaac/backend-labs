package com.sam.customerbatchprocessor.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sam.customerbatchprocessor.entity.FailedRecord;
import com.sam.customerbatchprocessor.repository.FailedRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomSkipListener implements SkipListener<Object, Object>,
        StepExecutionListener {

    private final FailedRecordRepository failedRecordRepository;
    private final ObjectMapper objectMapper;

    private String jobName;
    private Long jobExecutionId;
    private String fileReference;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        JobExecution jobExecution = stepExecution.getJobExecution();
        this.jobName = jobExecution.getJobInstance().getJobName();
        this.jobExecutionId = jobExecution.getId();
        this.fileReference = "customers.csv"; // You can make this dynamic if needed
    }

    @Override
    public void onSkipInProcess(Object item, Throwable t) {
        log.info("Skipping item in process: {}", item);
        try {
            String customerJson = objectMapper.writeValueAsString(item);
            persistFailedRecord(customerJson, t.getMessage());
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize skipped item: {}", item, e);
            throw new RuntimeException("Failed to serialize skipped item", e);
        }
    }

    @Override
    public void onSkipInWrite(Object item, Throwable t) {
        log.info("Skipping item in write: {}", item);
        try {
            String customerJson = objectMapper.writeValueAsString(item);
            persistFailedRecord(customerJson, t.getMessage());
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize skipped item: {}", item, e);
            throw new RuntimeException("Failed to serialize skipped item", e);
        }
    }

    @Override
    public void onSkipInRead(Throwable t) {
        log.info("Skipped during read: {}", t.getMessage());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persistFailedRecord(String originalData, String errorMessage) {
        failedRecordRepository.saveAndFlush(
                FailedRecord.builder()
                        .originalData(originalData)
                        .errorMessage(errorMessage)
                        .jobName(jobName)
                        .jobExecutionId(jobExecutionId)
                        .fileReference(fileReference)
                        .failedAt(LocalDateTime.now())
                        .build()
        );
        log.info("Saved failed record to DB.");
    }

}
