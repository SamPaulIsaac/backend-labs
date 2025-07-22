package com.sam.netRunRateCalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NetRunRateCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetRunRateCalculatorApplication.class, args);
    }

}
