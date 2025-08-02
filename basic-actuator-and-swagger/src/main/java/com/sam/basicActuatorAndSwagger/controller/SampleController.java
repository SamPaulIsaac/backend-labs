package com.sam.basicActuatorAndSwagger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuator")
public class SampleController {

    @GetMapping("/first")
    public String getFirstMethod() {
        return "This is the response from the get api (1).";
    }

    @GetMapping("/second")
    public String getSecondMethod() {
        return "This is the response from the get api (2).";
    }

    @GetMapping("/third")
    public String getThirdMethod() {
        return "This is the response from the get api (3).";
    }
}
