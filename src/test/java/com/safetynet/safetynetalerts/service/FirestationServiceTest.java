package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.PeopleByFirestationNumber;
import com.safetynet.safetynetalerts.dto.PhoneNumbersByFirestation;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.FirestationRepositoryImpl;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepositoryImpl;
import com.safetynet.safetynetalerts.repository.PersonRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FirestationServiceTest {

    @InjectMocks
    private FirestationService firestationService;

    @Mock
    private FirestationRepositoryImpl firestationsRepository;

    @Mock
    private PersonRepositoryImpl personRepository;

    @Mock
    private MedicalRecordRepositoryImpl medicalRecordsRepository;

    private List<Person> mockPersons;
    @BeforeEach
    void setUp() {
        mockPersons = new ArrayList<>();
        mockPersons.add(new Person("John", "Doe", "1234 rue jean", "City"
                , "12345", "111-222-3333", "johndoe@mail.com"));
        mockPersons.add(new Person("Jane", "Doe", "5678 route jacque", "City"
                , "12345", "444-555-6666", "janedoe@mail.ciom"));
    }

    @Test
    @DisplayName("Verify that repository methods are called once when searching by firestation")
    void testGetListOfAdultsAndMinorsCoveredByFirestation() {
        // Arrange
        String firestationNumber = "1";
        when(firestationsRepository.findAddressByFirestationNumber(anyString())).thenReturn(Arrays.asList("1 route saint george"));
        when(personRepository.findPeopleByFireStationAddress(anyString())).thenReturn(mockPersons);

        List<LocalDate> mockDatesOfBirth = Arrays.asList(
                LocalDate.of(1990, 1, 1), // Adult (33 years old)
                LocalDate.of(2010, 1, 1)  // Minor (13 years old)
        );

        when(medicalRecordsRepository.findDatesOfBirthInMedicalRecordsByPersons(anyList())).thenReturn(Arrays.asList("01/01/1990", "01/01/2010"));
        when(medicalRecordsRepository.convertListDateStringsToListOfDatesOfBirth(anyList())).thenReturn(mockDatesOfBirth);
        when(medicalRecordsRepository.calculateAgesByDatesOfBirth(anyList())).thenReturn(Arrays.asList(33, 13));
        when(medicalRecordsRepository.countAmountOfAdults(anyList())).thenReturn(1);

        // Act
        PeopleByFirestationNumber result = firestationService.getAdultsAndMinorsCoveredByFirestationNumber(firestationNumber);

        // Assert
        assertThat(result.getPerson()).hasSize(2);
        assertThat(result.getAmountOfAdults()).isEqualTo(1);
        assertThat(result.getAmountOfMinors()).isEqualTo(1);
    }

    @Test
    void testGetListOfPhoneNumbersByFirestation(){
        // Arrange
        String firestationNumber = "1";
        when(firestationsRepository.findAddressByFirestationNumber(anyString()))
                .thenReturn(List.of("1 route saint george"));
        when(personRepository.findPeopleByFireStationAddress(anyString())).thenReturn(mockPersons);

        // Act
        PhoneNumbersByFirestation result = firestationService.getPhoneNumbersByFirestationNumber(firestationNumber);

        // Assert
        assertThat(result.getPhoneNumbers()).containsExactlyInAnyOrder("111-222-3333", "444-555-6666");
    }


}
