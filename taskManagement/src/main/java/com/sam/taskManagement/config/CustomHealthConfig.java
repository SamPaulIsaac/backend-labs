package com.sam.taskManagement.config;


import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthConfig implements HealthIndicator {

    @Override
    public Health health() {
        boolean appIsHealthy = checkSomeCondition();
        if (appIsHealthy) {
            return Health.up().withDetail("message", "Task Management application is healthy").build();
        }
        return Health.down().withDetail("message", "Task Management application is unhealthy").build();
    }

    private boolean checkSomeCondition() {
        return true;
    }
}
