package com.sam.testContainers;

import com.sam.testContainers.repository.CricketerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * This test verifies that the Spring Boot application context loads successfully.
 * DB auto-configuration is disabled to avoid conflicts with Testcontainers-based tests.
 */
@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ActiveProfiles("nodb")
@Import(MockedRepoConfig.class)
class TestContainersApplicationTests {
    @Test
    void contextLoads() {
        // Context load verification only
    }
}

@TestConfiguration
class MockedRepoConfig {
    @Bean
    public CricketerRepository cricketerRepository() {
        return Mockito.mock(CricketerRepository.class);
    }
}