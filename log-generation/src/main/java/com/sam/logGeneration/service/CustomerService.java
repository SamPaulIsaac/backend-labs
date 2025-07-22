package com.sam.logGeneration.service;

import com.sam.logGeneration.dto.CustomerDto;
import com.sam.logGeneration.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomerDetails();

    Customer getCustomerDetails(Integer customerId);

    Customer postCustomerDetail(CustomerDto customerDto);

    Customer updateCustomerDetail(Integer customerId, CustomerDto customerDto);

    void deleteCustomerDetail(Integer customerId);
}
