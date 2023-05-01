package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MedicalRecordController.class)
 class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Mock
    private MedicalRecord medicalRecord;

    @BeforeEach
    void setUpBeforeEach() {
        medicalRecord = new MedicalRecord("John", "Doe", "1980-01-01", null, null);
    }

    @Test
    void testPostNewMedicalRecord() throws Exception {
        // Arrange
        when(medicalRecordService.postNewMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord);

        // Act
        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthdate\":\"1980-01-01\"}"))
                .andExpect(status().isCreated());

        // Assert
        verify(medicalRecordService, times(1)).postNewMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    void testPutMedicalRecord() throws Exception {
        // Arrange
        when(medicalRecordService.putMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord);

        // Act
        mockMvc.perform(put("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthdate\":\"1980-01-01\"}"))
                .andExpect(status().isOk());

        // Assert
        verify(medicalRecordService, times(1)).putMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    void testDeleteMedicalRecord() throws Exception {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        when(medicalRecordService.deleteMedicalRecord(firstName, lastName)).thenReturn(medicalRecord);

        // Act
        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", firstName)
                        .param("lastName", lastName))
                .andExpect(status().isNoContent());

        // Assert
        verify(medicalRecordService, times(1)).deleteMedicalRecord(firstName, lastName);
    }
}

