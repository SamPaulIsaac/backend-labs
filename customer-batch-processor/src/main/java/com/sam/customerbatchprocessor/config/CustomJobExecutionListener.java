package com.sam.customerbatchprocessor.config;

import com.sam.customerbatchprocessor.service.FailedRecordExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomJobExecutionListener implements JobExecutionListener {

    private final FailedRecordExportService exportService;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("-----BEFORE JOB-----");
        log.info("Before - Job is starting at: {}", jobExecution.getStartTime());
        log.info("Before - Job Id: {}", jobExecution.getJobId());
        log.info("Before - Job status: {}", jobExecution.getStatus());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("-----AFTER JOB-----");
        log.info("After - Job is starting at: {}", jobExecution.getStartTime());
        log.info("After - Job Id: {}", jobExecution.getJobId());
        log.info("After - Job status: {}", jobExecution.getStatus());
        log.info("After - Job End Time: {}", jobExecution.getEndTime());

        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            exportService.exportToCsv("failed-records.csv");
            log.info("Failed records exported to failed-records.csv");
        }
    }
}
