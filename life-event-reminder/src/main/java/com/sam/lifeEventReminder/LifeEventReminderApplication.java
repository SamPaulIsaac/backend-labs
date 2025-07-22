package com.sam.lifeEventReminder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LifeEventReminderApplication {
    public static void main(String[] args) {
        SpringApplication.run(LifeEventReminderApplication.class, args);
    }

}
