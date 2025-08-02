package com.sam.integrationTesting;

import com.sam.integrationTesting.entity.Cricketer;
import com.sam.integrationTesting.repository.CricketerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

// Activate one profile at a time depending on the DB target.
// Use 'integration-h2' for CI or in-memory testing.
// Use 'integration-mysql' for local MySQL DB testing.
@ActiveProfiles("integration-h2") // <-- switch to "integration-mysql" to test with real MySQL
public class CricketerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CricketerRepository cricketerRepository;

    @BeforeEach
    public void setup() {
        cricketerRepository.deleteAll();
        Cricketer cricketer = new Cricketer();
        cricketer.setName("Sachin Tendulkar");
        cricketer.setCountry("India");
        cricketer.setRuns(18000);
        cricketerRepository.save(cricketer);
    }

    @Test
    public void shouldReturnCricketer() {
        String url = "http://localhost:" + port + "/api/cricketers";
        ResponseEntity<List<Cricketer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        System.out.println("Response: " + response);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(Objects.requireNonNull(response.getBody()).size()).isEqualTo(1L);
        assertThat(response.getBody().get(0).getName()).isEqualTo("Sachin Tendulkar");
    }
}
