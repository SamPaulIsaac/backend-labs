package com.sam.completablefutureBasic.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@Slf4j
public class AsyncConfig implements AsyncConfigurer {

    /**
     * Initializes and configures a ThreadPoolTaskExecutor for async tasks.
     *
     * For detailed explanation of the configuration,
     * refer to the note: /home/bereka/Downloads/basic_async_service/notes/async_executor_explained.txt
     */
    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        log.info("Initializing Async Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        return executor;
    }
}
