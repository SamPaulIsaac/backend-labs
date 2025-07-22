package com.sam.unitTesting.service;

import com.sam.unitTesting.entity.Cricketer;
import com.sam.unitTesting.repository.CricketerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CricketerServiceTest {

    @Mock
    private CricketerRepository cricketerRepository;

    @InjectMocks
    private CricketerService cricketerService;

    @Test
    public void shouldReturnCricketer() {
        // Mock the repository response
        Cricketer cricketer = new Cricketer(1L, "Sachin Tendulkar", "India", 50000);
        when(cricketerRepository.findById(1L)).thenReturn(Optional.of(cricketer));

        // Call the service method and verify the result
        Cricketer result = cricketerService.getCricketerById(1L);
        assertThat(result.getName()).isEqualTo("Sachin Tendulkar");
        assertThat(result.getCountry()).isEqualTo("India");
    }
}

