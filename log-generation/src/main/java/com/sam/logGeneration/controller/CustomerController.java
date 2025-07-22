package com.sam.logGeneration.controller;


import com.sam.logGeneration.dto.CustomerDto;
import com.sam.logGeneration.entity.Customer;
import com.sam.logGeneration.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @GetMapping(path = "/getDetails")
    public List<Customer> getCustomerDetails() {
        return customerService.getCustomerDetails();
    }

    @GetMapping(path = "/getDetailById/{customerId}")
    public Customer getCustomerDetail(@PathVariable Integer customerId) {
        return customerService.getCustomerDetails(customerId);
    }


    @PostMapping(path = "/saveDetail")
    public Customer postCustomerDetail(@RequestBody CustomerDto customerDto) {
        return customerService.postCustomerDetail(customerDto);
    }


    @PutMapping(path = "/updateDetails/{customerId}")
    public Customer updateCustomerDetail(Integer customerId, CustomerDto customerDto) {
        return customerService.updateCustomerDetail(customerId, customerDto);
    }


    @DeleteMapping(path = "/deleteDetail/{customerId}")
    public void deleteCustomerDetail(Integer customerId) {
        customerService.deleteCustomerDetail(customerId);
    }


}
