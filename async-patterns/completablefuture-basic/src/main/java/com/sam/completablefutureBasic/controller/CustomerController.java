package com.sam.completablefutureBasic.controller;

import com.sam.completablefutureBasic.dto.CustomerDto;
import com.sam.completablefutureBasic.service.CustomerService;
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

    private final CustomerService customerService;

    @GetMapping("/details")
    public ResponseEntity<?> getCustomerDetails() {
        CompletableFuture<CustomerDto> customerFuture = customerService.fetchCustomerInfo();
        CompletableFuture<String> preferenceFuture = customerService.fetchCustomerPreferences();

        return customerFuture.thenCombine(preferenceFuture, (customer, preference) -> {
            log.info("Combining results on thread: {}", Thread.currentThread().getName());
            return ResponseEntity.ok(
                    String.format("Customer: %s, Age: %d, Preferences: %s",
                            customer.getName(),
                            customer.getAge(),
                            preference)
            );
        }).join(); // Block to send the combined result as a response
    }
}
