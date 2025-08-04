package com.sam.completablefutureBasic.service;

import com.sam.completablefutureBasic.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class CustomerService {

    @Async("asyncExecutor")
    public CompletableFuture<CustomerDto> fetchCustomerInfo() {
        log.info("Fetching customer info on thread: {}", Thread.currentThread().getName());
        simulateDelay(2000);
        CustomerDto customer = CustomerDto.builder()
                .name("Alice")
                .age(28)
                .build();
        return CompletableFuture.completedFuture(customer);
    }

    @Async("asyncExecutor")
    public CompletableFuture<String> fetchCustomerPreferences() {
        log.info("Fetching customer preferences on thread: {}", Thread.currentThread().getName());
        simulateDelay(3000);
        return CompletableFuture.completedFuture("EMAIL, SMS");
    }

    private void simulateDelay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Task interrupted", e);
        }
    }
}
