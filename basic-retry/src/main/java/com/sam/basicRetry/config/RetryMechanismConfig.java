package com.sam.retryMechanism.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RetryMechanismConfig {

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        /* By default, it retries 3.
           If we need to customize the retries for a specific exception, we can do.
           Retry only for 500 errors (HttpServerErrorException) */

        Map<Class<? extends Throwable>, Boolean> retriableExceptions = new HashMap<>();
        retriableExceptions.put(HttpServerErrorException.class, true);
        retriableExceptions.put(ResourceAccessException.class,true);
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(7, retriableExceptions); // 3 retries for 500 error
        retryTemplate.setRetryPolicy(retryPolicy);

        // Waits before attempting a retry.
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000);  // Initial interval in milliseconds
        backOffPolicy.setMultiplier(2.0);        // Backoff multiplier
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
