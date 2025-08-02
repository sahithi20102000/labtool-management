package com.tataelxsi.labtool.controller;

import com.tataelxsi.labtool.LabtoolManagementApplication;
import com.tataelxsi.labtool.service.StorageOptimizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = LabtoolManagementApplication.class)
@AutoConfigureMockMvc
public class StorageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StorageOptimizationService service;

    @BeforeEach
    public void setUp() {
        service.updatePartUsage(Arrays.asList("Brake", "Engine Oil", "Brake Filter"));
    }

    @Test
    public void testGetFastAccessParts() throws Exception {
        mockMvc.perform(get("/api/appointments/fast-access-parts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("Brake Filter")) // LRU: most recent first
                .andExpect(jsonPath("$[1]").value("Engine Oil"))
                .andExpect(jsonPath("$[2]").value("Brake"));
    }

   @Test
public void testGetFastAccessPartsInitiallyEmpty() throws Exception {
    service.clearCache();  // Clear internal cache properly

    mockMvc.perform(get("/api/appointments/fast-access-parts"))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.APPLICATION_JSON))
           .andExpect(jsonPath("$.length()").value(0));
}
}
