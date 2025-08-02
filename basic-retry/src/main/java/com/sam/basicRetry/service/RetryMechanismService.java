package com.sam.retryMechanism.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class RetryMechanismService {

    private final RetryTemplate retryTemplate;
    private final RestTemplate restTemplate;
    private final String url = "https://api.example.com/data";

    public void testRetryMechanism() {
        retryTemplate.execute(context -> {
            int attempt = context.getRetryCount() + 1;
            log.info("Attempt {}: Sending request to {}", attempt, url);
            log.info("Timestamp: {}", LocalDateTime.now());

            return restTemplate.getForObject(url, String.class);
        });
    }
}
