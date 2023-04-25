package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.MinorAndFamily;
import com.safetynet.safetynetalerts.dto.PeopleMedicalRecordsAndFirestation;
import com.safetynet.safetynetalerts.dto.PersonInfoAndMedicalRecord;
import com.safetynet.safetynetalerts.service.PersonService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
 class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private static List<MinorAndFamily> minorAndFamilyList;
    @BeforeAll
    static void setUp() throws Exception{
        minorAndFamilyList = new ArrayList<>();
    }
    @Test
    void testGetMinorAndFamilyByAddress() throws Exception {
        //Arrange
        when(personService.getMinorsAndFamilyByAddress(anyString()))
                .thenReturn(minorAndFamilyList);

        //Act
        mockMvc.perform(get("/childAlert?address=1 route saint george")).andExpect(status().isOk());


        //Assert
        verify(personService, times(1)).getMinorsAndFamilyByAddress("1 route saint george");
    }

    @Test
    void testGetPersonMedicalRecordAndFirestation() throws Exception {
        // Arrange
        String address = "1509 Culver St";
        PeopleMedicalRecordsAndFirestation mockPeopleMedicalRecordsAndFirestation =
                new PeopleMedicalRecordsAndFirestation(null
                        , "3");
        when(personService.getPeopleMedicalRecordsAndFirestationByAddress(address))
                .thenReturn(mockPeopleMedicalRecordsAndFirestation);

        // Act
        mockMvc.perform(get("/fire?address=1509 Culver St"))
                .andExpect(status().isOk());

        // Assert
        verify(personService, times(1))
                .getPeopleMedicalRecordsAndFirestationByAddress("1509 Culver St");
    }

    @Test
    void testGetPersonInfoAndMedicalRecords() throws Exception {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        List<PersonInfoAndMedicalRecord> mockPersonInfoAndMedicalRecords =
                Collections.singletonList(new PersonInfoAndMedicalRecord(firstName, lastName, null, null
                        ,null, 21 , null, null, null));

        when(personService.getPersonInfoAndMedicalRecordByName(firstName, lastName))
                .thenReturn(mockPersonInfoAndMedicalRecords);

        // Act
        mockMvc.perform(get("/personInfo")
                        .param("firstName", firstName)
                        .param("lastName", lastName))
                .andExpect(status().isOk());

        // Assert
        verify(personService, times(1))
                .getPersonInfoAndMedicalRecordByName(firstName, lastName);
    }

    @Test
    void testGetEmailsOfPeopleFromCity() throws Exception {
        // Arrange
        String city = "New York";
        List<String> mockEmails = Arrays.asList("john@example.com", "jane@example.com");
        when(personService.getEmailsByCity(city)).thenReturn(mockEmails);

        // Act
        mockMvc.perform(get("/communityEmail")
                        .param("city", city))
                .andExpect(status().isOk());

        // Assert
        verify(personService, times(1))
                .getEmailsByCity(city);
    }
}
