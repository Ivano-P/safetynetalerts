package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.MinorAndFamily;
import com.safetynet.safetynetalerts.dto.PeopleMedicalRecordsAndFirestation;
import com.safetynet.safetynetalerts.dto.PersonInfo;
import com.safetynet.safetynetalerts.dto.PersonInfoAndMedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.PersonRepositoryImpl;
import com.safetynet.safetynetalerts.service.PersonServiceImpl;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonServiceImpl personService;

    @Mock
    private PersonRepositoryImpl personRepository;

    private static List<MinorAndFamily> minorAndFamilyList;

    private static Person person;

    @BeforeAll
    static void setUp() throws Exception {
        minorAndFamilyList = new ArrayList<>();
    }

    @BeforeEach
    void setUpBeforeEach() {
        person = new Person("John", "Doe", "1 route saint george", "New York", "0123456789", "john.doe@example.com", "1980-01-01");
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
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        PersonInfo personInfo = new PersonInfo(firstName, lastName, "mock address", "mockCity",
                "12345", "mockEmail");
        List<PersonInfoAndMedicalRecord> mockPersonInfoAndMedicalRecords =
                Collections.singletonList(new PersonInfoAndMedicalRecord(personInfo,
                        21, null, null));

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
        String city = person.getCity();
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

    @Test
    void testAddNewPerson() throws Exception {
        // Arrange
        when(personService.postNewPerson(any(Person.class))).thenReturn(person);

        // Act
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\"}")) // Replace with the JSON representation of your person object
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.firstName").value("John"))
                .andExpect((ResultMatcher) jsonPath("$.lastName").value("Doe"));

        // Assert
        verify(personService, times(1)).postNewPerson(any(Person.class));
    }

    @Test
    void testEditPerson() throws Exception {
        // Arrange
        when(personService.putPerson(any(Person.class))).thenReturn(person);

        // Act
        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\"}")) // Replace with the JSON representation of your person object
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.firstName").value("John"))
                .andExpect((ResultMatcher) jsonPath("$.lastName").value("Doe"));

        // Assert
        verify(personService, times(1)).putPerson(any(Person.class));
    }


    @Test
    void testDeletePerson() throws Exception {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        when(personService.deletePerson(firstName, lastName)).thenReturn(person);

        // Act
        mockMvc.perform(delete("/person")
                        .param("firstName", firstName)
                        .param("lastName", lastName))
                .andExpect(status().isNoContent());

        // Assert
        verify(personService, times(1)).deletePerson(firstName, lastName);
    }
}
