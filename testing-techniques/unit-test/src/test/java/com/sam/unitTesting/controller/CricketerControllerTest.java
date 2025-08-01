package com.sam.unitTesting.controller;

import com.sam.unitTesting.config.TestMockConfig;
import com.sam.unitTesting.entity.Cricketer;
import com.sam.unitTesting.service.CricketerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@WebMvcTest(CricketerController.class)
@Import(TestMockConfig.class)
class CricketerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CricketerService cricketerService;

    @Test
    void shouldReturnCricketer() throws Exception {
        Cricketer cricketer = new Cricketer(1L, "Sachin Tendulkar", "India", 50000);
        when(cricketerService.getCricketerById(1L)).thenReturn(cricketer);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/cricketers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sachin Tendulkar"))
                .andExpect(jsonPath("$.country").value("India"));
    }
}
