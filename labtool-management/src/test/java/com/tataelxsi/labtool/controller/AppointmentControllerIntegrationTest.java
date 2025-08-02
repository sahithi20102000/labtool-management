package com.tataelxsi.labtool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tataelxsi.labtool.LabtoolManagementApplication;
import com.tataelxsi.labtool.entity.Appointment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = LabtoolManagementApplication.class)
@AutoConfigureMockMvc
public class AppointmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAndGetAppointment() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setCustomerName("John");  // Corrected from setClientName
        appointment.setParts(List.of("Brake", "Oil Filter"));  // Corrected from setRequiredParts

        // Create the appointment
        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John"))  // Corrected JSON field name
                .andExpect(jsonPath("$.parts", hasSize(2)));          // Corrected JSON field name

        // Get all appointments and check if "John" is present
        mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].customerName", hasItem("John")));  // Corrected JSON field name
    }

    @Test
    public void testGetAppointmentById_NotFound() throws Exception {
        mockMvc.perform(get("/api/appointments/99999"))
                .andExpect(status().isOk())
                .andExpect(content().string("")); // Should return empty or null if not found
    }

    @Test
    public void testDeleteAppointment() throws Exception {
        // Create an appointment first
        Appointment appointment = new Appointment();
        appointment.setCustomerName("DeleteMe");  // Corrected from setClientName
        appointment.setParts(List.of("PartA"));   // Corrected from setRequiredParts

        String response = mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointment)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Appointment created = objectMapper.readValue(response, Appointment.class);

        // Delete it
        mockMvc.perform(delete("/api/appointments/" + created.getId()))
                .andExpect(status().isOk());
    }
}
