package com.sam.basicAsync.config;

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
     * Async Executor Configuration
     *
     * This executor controls how @Async tasks are processed using a bounded thread pool and queue.
     *
     * 🔍 For a detailed explanation of each setting (core pool size, max pool size, queue capacity, etc.),
     * please refer to:
     * → /home/bereka/Downloads/basic_async_service/notes/async_executor_explained.txt
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

