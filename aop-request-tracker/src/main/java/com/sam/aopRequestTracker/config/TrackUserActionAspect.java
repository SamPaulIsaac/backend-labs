package com.sam.aopRequestTracker.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class TrackUserActionAspect {

    // This advice wraps around any method annotated with @TrackUserAction and logs execution time
    @Around("@annotation(TrackUserAction)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();

        log.info("[AROUND-START] Executing {}", methodName);

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        log.info("[AROUND-END] {} executed in {}ms", methodName, executionTime);
        return result;
    }

    // This advice runs before the execution of any method annotated with @TrackUserAction
    @Before("@annotation(TrackUserAction)")
    public void logBefore(JoinPoint joinPoint) {
        String username = "sam"; // Placeholder; extend from security context if needed
        String methodName = joinPoint.getSignature().toShortString();
        String args = Arrays.toString(joinPoint.getArgs());
        LocalDateTime timestamp = LocalDateTime.now();

        log.info("[AUDIT-BEGIN] User: {} invoked: {} with args: {} at: {}", username, methodName, args, timestamp);
    }

    // This advice runs after successful execution of any method annotated with @TrackUserAction
    @AfterReturning("@annotation(TrackUserAction)")
    public void logAfterReturning(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("[AFTER-RETURNING] Method {} completed successfully.", methodName);
    }

    // This advice runs after completion (success or exception) of any method annotated with @TrackUserAction
    @After("@annotation(TrackUserAction)")
    public void logAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("[AFTER] Method {} has finished execution.", methodName);
    }
}
