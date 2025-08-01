package com.sam.testContainers.repository;

import com.sam.testContainers.entity.Cricketer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@DataJpaTest
@ActiveProfiles("repositoryTest")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CricketerRepositoryTest {

    @Container
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.33")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpassword");

    @DynamicPropertySource
    static void overrideDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }

    @Autowired
    private CricketerRepository cricketerRepository;

    @BeforeEach
    void setUp() {
        cricketerRepository.deleteAll();
    }

    @Test
    void testFindAllCricketers() {
        buildCricketers();
        List<Cricketer> cricketers = cricketerRepository.findAll();
        assertNotNull(cricketers, "Not null");
        assertEquals(2, cricketers.size(), "The size of the cricketers list should be 2.");
    }

    private void buildCricketers() {
        Cricketer cricketer1 = Cricketer.builder()
                .name("Player1")
                .country("Country1")
                .runs(5000)
                .build();

        Cricketer cricketer2 = Cricketer.builder()
                .name("Player2")
                .country("Country2")
                .runs(6000)
                .build();

        cricketerRepository.saveAllAndFlush(Arrays.asList(cricketer1, cricketer2));
    }

    @AfterAll
    public static void tearDown() {
        mysqlContainer.stop(); // optional; containers stop automatically after test scope
    }
}
