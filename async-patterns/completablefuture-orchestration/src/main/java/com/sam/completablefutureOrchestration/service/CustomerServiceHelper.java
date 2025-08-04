package com.sam.completablefutureOrchestration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class CustomerServiceHelper {

    public CompletableFuture<String> getCustomerName() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            log.info("Fetched customer name");
            return "Alice";
        });
    }

    public CompletableFuture<String> getCustomerEmail() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(1200);
            log.info("Fetched customer email");
            return "alice@example.com";
        });
    }

    public CompletableFuture<String> getEmailForName(String name) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            log.info("Fetched email for {}", name);
            return name.toLowerCase() + "@example.com";
        });
    }

    public CompletableFuture<String> getCustomerLocation() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(1500);
            log.info("Fetched customer location");
            return "Bangalore";
        });
    }

    public CompletableFuture<String> getPossiblyFailingName() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            if (new Random().nextBoolean()) {
                log.warn("Simulated failure in getPossiblyFailingName");
                throw new RuntimeException("Name service failed");
            }
            log.info("Fetched customer name (no failure)");
            return "Ethan Hunt";
        });
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }
}
