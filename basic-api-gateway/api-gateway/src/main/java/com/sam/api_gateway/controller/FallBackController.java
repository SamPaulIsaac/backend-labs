package com.sam.api_gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FallBackController {

    @GetMapping("/userFallback")
    public ResponseEntity<String> userFallback() {
        log.info("User service is down. Handling fallback.");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                             .body("User Service is currently unavailable. Please try again later.");
    }

    @GetMapping("/productFallback")
    public ResponseEntity<String> productFallback() {
        log.info("Product service is down. Handling fallback.");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                             .body("Product Service is currently unavailable. Please try again later.");
    }

    @PostMapping({"/userFallback", "/productFallback"})
    public ResponseEntity<String> postFallback() {
        log.info("Fallback triggered for POST request.");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                             .body("The target service is unavailable. POST operations cannot be fulfilled right now.");
    }
}


