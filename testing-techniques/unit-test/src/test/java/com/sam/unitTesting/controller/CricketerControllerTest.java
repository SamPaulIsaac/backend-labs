package com.sam.unitTesting.controller;

import com.sam.unitTesting.entity.Cricketer;
import com.sam.unitTesting.service.CricketerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CricketerController.class)
public class CricketerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CricketerService cricketerService;

    @Test
    public void shouldReturnCricketer() throws Exception {
        // Mock the service response
        Cricketer cricketer = new Cricketer(1L, "Sachin Tendulkar", "India", 50000);
        when(cricketerService.getCricketerById(1L)).thenReturn(cricketer);

        // Test the controller layer
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cricketers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sachin Tendulkar"))
                .andExpect(jsonPath("$.country").value("India"));
    }
}

