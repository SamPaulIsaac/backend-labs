package com.sam.retryMechanism.controller;

import com.sam.retryMechanism.service.RetryMechanismService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/retry-mechanism")
@RequiredArgsConstructor
public class RetryMechanismController {

    private final RetryMechanismService retryMechanismService;

    @GetMapping("/test")
    public void invokeRetryMechanism() {
        retryMechanismService.testRetryMechanism();
    }
}
