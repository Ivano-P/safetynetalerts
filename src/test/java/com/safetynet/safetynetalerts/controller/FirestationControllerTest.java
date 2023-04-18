package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.MinorAndFamily;
import com.safetynet.safetynetalerts.dto.MinorAndFamilyByAddress;
import com.safetynet.safetynetalerts.dto.PeopleByFirestationNumber;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(Controller.class)
 class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirestationService firestationService;

   @MockBean
   private PersonService personService;

   private PeopleByFirestationNumber peopleByFirestationNumber;
   private MinorAndFamilyByAddress minorAndFamilyByAddress;

   private static List<MinorAndFamily> minorAndFamilyList;

   @BeforeAll
   static void setUp() throws Exception{
      minorAndFamilyList = new ArrayList<>();
   }

   @BeforeEach
   void setUpPerEach() {
      peopleByFirestationNumber = new PeopleByFirestationNumber(null, 1, 1); // Populate with appropriate data
      minorAndFamilyByAddress = new MinorAndFamilyByAddress(minorAndFamilyList); // Populate with appropriate data
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
              .thenReturn(minorAndFamilyByAddress);

      //Act
      mockMvc.perform(get("/childAlert?address=1 route saint george")).andExpect(status().isOk());


      //Assert
      verify(personService, times(1)).getListMinorsAndFamilyByAddress("1 route saint george");
   }

}
