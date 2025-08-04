package com.sam.basicAsync.service;

import com.sam.basicAsync.entity.Customer;
import com.sam.basicAsync.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AsyncLoggerService asyncLoggerService;

    public Customer registerCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer saved: {}", savedCustomer.getName());

        asyncLoggerService.logCustomerRegistration(savedCustomer.getName()); // fire-and-forget
        log.info("Continuing main thread after triggering async logger");

        return savedCustomer;
    }
}

