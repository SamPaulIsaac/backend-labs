package com.sam.completablefutureOrchestration.service;

import com.sam.completablefutureOrchestration.dto.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerOrchestratorService {

    private final CustomerServiceHelper helper;

    // Phase A: thenCombine
    public CompletableFuture<Customer> getCustomerDetailsPhaseA() {
        log.info("Starting Phase A: thenCombine");
        return helper.getCustomerName()
                .thenCombine(helper.getCustomerEmail(), (name, email) -> {
                    log.info("Combining name and email into Customer");
                    return new Customer(1L, name, email);
                });
    }

    // Phase B: thenCompose
    public CompletableFuture<Customer> getCustomerDetailsPhaseB() {
        log.info("Starting Phase B: thenCompose");
        return helper.getCustomerName()
                .thenCompose(helper::getEmailForName)
                .thenApply(email -> {
                    log.info("Chained to get email; assembling Customer");
                    return new Customer(2L, "Alice", email);
                });
    }

    // Phase C.1: allOf
    public CompletableFuture<Customer> getCustomerDetailsPhaseCAllOf() {
        log.info("Starting Phase C (allOf): parallel tasks");
        CompletableFuture<String> nameFuture = helper.getCustomerName();
        CompletableFuture<String> emailFuture = helper.getCustomerEmail();
        CompletableFuture<String> locationFuture = helper.getCustomerLocation();

        return CompletableFuture.allOf(nameFuture, emailFuture, locationFuture)
                .thenApply(voidResult -> {
                    try {
                        String name = nameFuture.get();
                        String email = emailFuture.get();
                        String location = locationFuture.get();
                        log.info("All tasks complete → Creating enriched Customer");
                        return new Customer(3L, name + " (" + location + ")", email);
                    } catch (Exception e) {
                        throw new RuntimeException("Error combining results in allOf", e);
                    }
                });
    }

    // Phase C.2: anyOf (optional)
    public CompletableFuture<String> getCustomerDetailsPhaseCAnyOf() {
        log.info("Starting Phase C (anyOf): return fastest response");

        CompletableFuture<String> nameFuture = helper.getCustomerName();
        CompletableFuture<String> emailFuture = helper.getCustomerEmail();
        CompletableFuture<String> locationFuture = helper.getCustomerLocation();

        return CompletableFuture.anyOf(nameFuture, emailFuture, locationFuture)
                .thenApply(result -> {
                    log.info("First completed task: {}", result);
                    return "First Result: " + result;
                });
    }

    // Phase D.1: thenAcceptBoth
    public void processWithThenAcceptBoth() {
        CompletableFuture<String> nameFuture = helper.getCustomerName();
        CompletableFuture<String> emailFuture = helper.getCustomerEmail();

        nameFuture.thenAcceptBoth(emailFuture, (name, email) -> {
            log.info("thenAcceptBoth → Name: {}, Email: {}", name, email);
        });
    }

    // Phase D.2: runAfterBoth
    public void processWithRunAfterBoth() {
        CompletableFuture<String> nameFuture = helper.getCustomerName();
        CompletableFuture<String> emailFuture = helper.getCustomerEmail();

        nameFuture.runAfterBoth(emailFuture, () -> {
            log.info("runAfterBoth → Both tasks completed (no return)");
        });
    }

    // Phase E: handle failures
    public CompletableFuture<Customer> getCustomerDetailsPhaseE() {
        log.info("Starting Phase E: exception handling");
        CompletableFuture<String> nameFuture = helper.getPossiblyFailingName()
                .exceptionally(ex -> {
                    log.error("Fallback in name future: {}", ex.getMessage());
                    return "Default Name";
                });

        CompletableFuture<String> emailFuture = helper.getCustomerEmail()
                .handle((email, ex) -> {
                    if (ex != null) {
                        log.warn("Handled error in email future: {}", ex.getMessage());
                        return "default@example.com";
                    }
                    return email;
                });

        return nameFuture.thenCombine(emailFuture, (name, email) -> {
            log.info("Combining (possibly recovered) name and email");
            return new Customer(5L, name, email);
        }).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Phase E failed overall: {}", ex.getMessage());
            } else {
                log.info("Final Customer: {}", result);
            }
        });
    }
}
