package com.sam.logGeneration.service.impl;

import com.sam.logGeneration.dto.CustomerDto;
import com.sam.logGeneration.entity.Customer;
import com.sam.logGeneration.repository.CustomerRepository;
import com.sam.logGeneration.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getCustomerDetails() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerDetails(Integer customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("NOT FOUND!"));
    }

    @Override
    public Customer postCustomerDetail(CustomerDto customerDto) {
        Customer requestedCustomerDetail = new ModelMapper().map(customerDto, Customer.class);
        return customerRepository.save(requestedCustomerDetail);
    }

    @Override
    public Customer updateCustomerDetail(Integer customerId, CustomerDto customerDto) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("NOT FOUND!"));
        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomerDetail(Integer customerId) {
        customerRepository.deleteById(customerId);
    }
}
