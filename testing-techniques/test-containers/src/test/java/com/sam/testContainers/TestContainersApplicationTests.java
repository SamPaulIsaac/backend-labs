package com.sam.testContainers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This test verifies that the Spring Boot application context loads successfully.
 * DB auto-configuration is disabled to avoid conflicts with Testcontainers-based tests.
 */
@SpringBootTest
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
class TestContainersApplicationTests {
	@Test
	void contextLoads() {
		// Context load verification only
	}
}
