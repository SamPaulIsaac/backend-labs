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
@ActiveProfiles("integrationTest")
public class CricketerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CricketerRepository cricketerRepository;


    @BeforeEach
    public void setup() {
        cleanUp();
        // Create a new Cricketer entity and save it to the repository
        Cricketer cricketer = new Cricketer();
        cricketer.setName("Sachin Tendulkar");
        cricketer.setCountry("India");
        cricketer.setRuns(18000);
       Cricketer saved = cricketerRepository.save(cricketer);
        System.out.println("Saved Cricketer: "+saved);
    }

    private void cleanUp() {
        cricketerRepository.deleteAll();
    }

    @Test
    public void shouldReturnCricketer() {
        // Test the whole stack with a real HTTP request
        String url = "http://localhost:" + port + "/api/cricketers";
        ResponseEntity<List<Cricketer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        System.out.println("Response: " + response);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(Objects.requireNonNull(response.getBody()).size()).isEqualTo(1L);
        assertThat(response.getBody().get(0).getName()).isEqualTo("Sachin Tendulkar");
    }
}

