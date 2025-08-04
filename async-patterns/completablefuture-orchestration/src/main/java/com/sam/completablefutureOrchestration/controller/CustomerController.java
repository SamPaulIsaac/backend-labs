package com.sam.completablefutureOrchestration.controller;

import com.sam.completablefutureOrchestration.dto.Customer;
import com.sam.completablefutureOrchestration.service.CustomerOrchestratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerOrchestratorService orchestratorService;

    // Phase A: thenCombine
    @GetMapping("/phase-a")
    public CompletableFuture<Customer> getCustomerPhaseA() {
        log.info("Received request for Phase A (thenCombine)");
        return orchestratorService.getCustomerDetailsPhaseA();
    }

    // Phase B: thenCompose
    @GetMapping("/phase-b")
    public CompletableFuture<Customer> getCustomerPhaseB() {
        log.info("Received request for Phase B (thenCompose)");
        return orchestratorService.getCustomerDetailsPhaseB();
    }

    // Phase C: allOf
    @GetMapping("/phase-c/all-of")
    public CompletableFuture<ResponseEntity<Customer>> getCustomerPhaseCAllOf() {
        log.info("Received request for Phase C (allOf)");
        return orchestratorService.getCustomerDetailsPhaseCAllOf()
                .thenApply(ResponseEntity::ok);
    }

    // Phase C: anyOf (optional demo)
    @GetMapping("/phase-c/any-of")
    public CompletableFuture<ResponseEntity<String>> getCustomerPhaseCAnyOf() {
        log.info("Received request for Phase C (anyOf)");
        return orchestratorService.getCustomerDetailsPhaseCAnyOf()
                .thenApply(ResponseEntity::ok);
    }

    // Phase D: thenAcceptBoth
    @GetMapping("/phase-d/accept-both")
    public ResponseEntity<String> triggerThenAcceptBoth() {
        orchestratorService.processWithThenAcceptBoth();
        return ResponseEntity.ok("Triggered thenAcceptBoth");
    }

    // Phase D: runAfterBoth
    @GetMapping("/phase-d/run-after-both")
    public ResponseEntity<String> triggerRunAfterBoth() {
        orchestratorService.processWithRunAfterBoth();
        return ResponseEntity.ok("Triggered runAfterBoth");
    }

    // Phase E: exception handling
    @GetMapping("/phase-e")
    public CompletableFuture<ResponseEntity<Customer>> getCustomerPhaseE() {
        log.info("Received request for Phase E (exceptionally + handle + whenComplete)");
        return orchestratorService.getCustomerDetailsPhaseE()
                .thenApply(ResponseEntity::ok);
    }
}
