package com.sam.basicAsync.controller;

import com.sam.basicAsync.entity.Customer;
import com.sam.basicAsync.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.registerCustomer(customer));
    }
}

