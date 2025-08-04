package com.sam.completablefutureBasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CompletablefutureBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompletablefutureBasicApplication.class, args);
	}

}
