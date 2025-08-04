package com.sam.basicAsync.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsyncLoggerService {

    @Async("asyncExecutor")
    public void logCustomerRegistration(String customerName) {
        log.info("Started async logging for customer: {}", customerName);
        try {
            Thread.sleep(3000); // Simulate some heavy task
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("Completed async logging for customer: {} on thread: {}", customerName, Thread.currentThread().getName());
    }
}

