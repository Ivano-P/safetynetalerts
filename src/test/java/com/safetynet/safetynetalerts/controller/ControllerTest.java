package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.service.FirestationService;
import com.safetynet.safetynetalerts.service.PersonService;
import org.junit.jupiter.api.BeforeAll;
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
@WebMvcTest(Controller.class)
 class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirestationService firestationService;

   @MockBean
   private PersonService personService;

   private PeopleByFirestationNumber peopleByFirestationNumber;

   private PhoneNumbersByFirestation phoneNumbersByFirestation;

   private static List<MinorAndFamily> minorAndFamilyList;

   @BeforeAll
   static void setUp() throws Exception{
      minorAndFamilyList = new ArrayList<>();
   }

   @BeforeEach
   void setUpPerEach() {
      peopleByFirestationNumber = new PeopleByFirestationNumber(null, 1, 1); 

   }

    @Test
     void testGetPeopleByFirestationNumber() throws Exception {
        //Arrange
        // Stub the service call
        when(firestationService.getListOfAdultsAndMinorsCoveredByFirestation(anyString()))
                .thenReturn(peopleByFirestationNumber);

        //Act
        mockMvc.perform(get("/firestation?stationNumber=1"))
                .andExpect(status().isOk());

        //Assert: Verify that the service was called once
        verify(firestationService, times(1))
                .getListOfAdultsAndMinorsCoveredByFirestation("1");
    }

   @Test
   void testGetMinorAndFamilyByAddress() throws Exception {
      //Arrange
      when(personService.getListMinorsAndFamilyByAddress(anyString()))
              .thenReturn(minorAndFamilyList);

      //Act
      mockMvc.perform(get("/childAlert?address=1 route saint george")).andExpect(status().isOk());


      //Assert
      verify(personService, times(1)).getListMinorsAndFamilyByAddress("1 route saint george");
   }

   @Test
    void testGetPhoneNumbersOfPeopleByFirestation() throws Exception {
       // Arrange
       String firestationNumber = "1";
       List<String> phoneNumbers = Arrays.asList("123-456-7890", "098-765-4321");
       PhoneNumbersByFirestation mockPhoneNumbersByFirestation = new PhoneNumbersByFirestation(phoneNumbers);
       when(firestationService.getListOfPhoneNumbersByFirestation(firestationNumber)).thenReturn(mockPhoneNumbersByFirestation);

       // Act
       mockMvc.perform(get("/phoneAlert?firestation=1")).andExpect(status().isOk());

       //Assert
       verify(firestationService, times(1))
               .getListOfPhoneNumbersByFirestation("1");
   }

    @Test
    void testGetPersonMedicalRecordAndFirestation() throws Exception {
        // Arrange
        String address = "1509 Culver St";
        PeopleMedicalRecordsAndFirestationByAddress mockPeopleMedicalRecordsAndFirestationByAddress =
                new PeopleMedicalRecordsAndFirestationByAddress(null
                        , "3");
        when(personService.getListOfPeopleMedicalRecordsAndFirestation(address))
                .thenReturn(mockPeopleMedicalRecordsAndFirestationByAddress);

        // Act
        mockMvc.perform(get("/fire?address=1509 Culver St"))
                .andExpect(status().isOk());

        // Assert
        verify(personService, times(1))
                .getListOfPeopleMedicalRecordsAndFirestation("1509 Culver St");
    }

    @Test
    void testGetHousholdsByListOfFirestationNumber() throws Exception {
        // Arrange
        List<String> stationNumbers = Arrays.asList("1", "2", "3");
        List<Houshold> mockHousholds = new ArrayList<>(); // Populate with appropriate test data

        when(firestationService.getListOfHousholdsByListOfFirestationNumber(stationNumbers))
                .thenReturn(mockHousholds);

        // Act
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "1")
                        .param("stations", "2")
                        .param("stations", "3"))
                .andExpect(status().isOk());

        // Assert
        verify(firestationService, times(1))
                .getListOfHousholdsByListOfFirestationNumber(stationNumbers);
    }

}
