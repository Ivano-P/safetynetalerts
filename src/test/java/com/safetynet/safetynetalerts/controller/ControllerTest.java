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
import java.util.Collections;
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
        PeopleMedicalRecordsAndFirestation mockPeopleMedicalRecordsAndFirestation =
                new PeopleMedicalRecordsAndFirestation(null
                        , "3");
        when(personService.getListOfPeopleMedicalRecordsAndFirestation(address))
                .thenReturn(mockPeopleMedicalRecordsAndFirestation);

        // Act
        mockMvc.perform(get("/fire?address=1509 Culver St"))
                .andExpect(status().isOk());

        // Assert
        verify(personService, times(1))
                .getListOfPeopleMedicalRecordsAndFirestation("1509 Culver St");
    }

    @Test
    void testGetHousholds() throws Exception {
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

    @Test
    void testGetPersonInfoAndMedicalRecords() throws Exception {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        List<PersonInfoAndMedicalRecord> mockPersonInfoAndMedicalRecords =
                Collections.singletonList(new PersonInfoAndMedicalRecord(firstName, lastName, null, null
                        ,null, 21 , null, null, null));

        when(personService.getPersonInfoAndMedicalRecord(firstName, lastName))
                .thenReturn(mockPersonInfoAndMedicalRecords);

        // Act
        mockMvc.perform(get("/personInfo")
                        .param("firstName", firstName)
                        .param("lastName", lastName))
                .andExpect(status().isOk());

        // Assert
        verify(personService, times(1))
                .getPersonInfoAndMedicalRecord(firstName, lastName);
    }

    @Test
    void testGetEmailsOfPeopleFromCity() throws Exception {
        // Arrange
        String city = "New York";
        List<String> mockEmails = Arrays.asList("john@example.com", "jane@example.com");
        when(personService.getListOfEmails(city)).thenReturn(mockEmails);

        // Act
        mockMvc.perform(get("/communityEmail")
                        .param("city", city))
                .andExpect(status().isOk());

        // Assert
        verify(personService, times(1))
                .getListOfEmails(city);
    }


}
