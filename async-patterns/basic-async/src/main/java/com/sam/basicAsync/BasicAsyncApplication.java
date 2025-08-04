package com.sam.basicAsync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BasicAsyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicAsyncApplication.class, args);
	}

}
