package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.Houshold;
import com.safetynet.safetynetalerts.dto.PeopleByFirestationNumber;
import com.safetynet.safetynetalerts.dto.PhoneNumbersByFirestation;
import com.safetynet.safetynetalerts.service.FirestationService;
import com.safetynet.safetynetalerts.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FirestationController.class)
 class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirestationService firestationService;

   @MockBean
   private PersonService personService;

   private PeopleByFirestationNumber peopleByFirestationNumber;

   @BeforeEach
   void setUpPerEach() {
      peopleByFirestationNumber = new PeopleByFirestationNumber(null, 1, 1); 

   }

    @Test
     void testGetPeopleByFirestationNumber() throws Exception {
        //Arrange
        // Stub the service call
        when(firestationService.getAdultsAndMinorsCoveredByFirestationNumber(anyString()))
                .thenReturn(peopleByFirestationNumber);

        //Act
        mockMvc.perform(get("/firestation?stationNumber=1"))
                .andExpect(status().isOk());

        //Assert: Verify that the service was called once
        verify(firestationService, times(1))
                .getAdultsAndMinorsCoveredByFirestationNumber("1");
    }

   @Test
    void testGetPhoneNumbersOfPeopleByFirestation() throws Exception {
       // Arrange
       String firestationNumber = "1";
       List<String> phoneNumbers = Arrays.asList("123-456-7890", "098-765-4321");
       PhoneNumbersByFirestation mockPhoneNumbersByFirestation = new PhoneNumbersByFirestation(phoneNumbers);
       when(firestationService.getPhoneNumbersByFirestationNumber(firestationNumber)).thenReturn(mockPhoneNumbersByFirestation);

       // Act
       mockMvc.perform(get("/phoneAlert?firestation=1")).andExpect(status().isOk());

       //Assert
       verify(firestationService, times(1))
               .getPhoneNumbersByFirestationNumber("1");
   }

    @Test
    void testGetHousholds() throws Exception {
        // Arrange
        List<String> stationNumbers = Arrays.asList("1", "2", "3");
        List<Houshold> mockHousholds = new ArrayList<>(); // Populate with appropriate test data

        when(firestationService.getHousholdsByFirestationNumbers(stationNumbers))
                .thenReturn(mockHousholds);

        // Act
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "1")
                        .param("stations", "2")
                        .param("stations", "3"))
                .andExpect(status().isOk());

        // Assert
        verify(firestationService, times(1))
                .getHousholdsByFirestationNumbers(stationNumbers);
    }
}
