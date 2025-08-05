package com.sam.customerbatchprocessor.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/customerBatchProcess")
@RequiredArgsConstructor
@Slf4j
public class CustomerBatchProcessingController {

    private final JobLauncher jobLauncher;
    private final Job job;

    @GetMapping("/start")
    public void process() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        log.info("Trigger to start the process.");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("jobName", "customerImportJob")
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(job, jobParameters);
        log.info("JOB Execution completed!");
    }
}
