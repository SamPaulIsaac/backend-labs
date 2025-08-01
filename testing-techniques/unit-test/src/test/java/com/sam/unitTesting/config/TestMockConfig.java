package com.sam.unitTesting.config;


import com.sam.unitTesting.repository.CricketerRepository;
import com.sam.unitTesting.service.CricketerService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestMockConfig {

    @Bean
    public CricketerRepository cricketerRepository() {
        return Mockito.mock(CricketerRepository.class);
    }
    @Bean
    public CricketerService cricketerService() {
        return Mockito.mock(CricketerService.class);
    }
}

