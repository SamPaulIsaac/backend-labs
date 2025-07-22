package com.sam.repositoryTestUsingSpringBootTest.repository;

import com.sam.repositoryTestUsingSpringBootTest.entity.Cricketer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("repositoryTest")
public class CricketerRepositoryTest {

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


    public void buildCricketers() {
        Cricketer cricketer1 = new Cricketer(1L, "Player1", "Country1", 5000);
        Cricketer cricketer2 = new Cricketer(2L, "Player2", "Country2", 6000);
        cricketerRepository.saveAll(Arrays.asList(cricketer1, cricketer2));
    }
}
