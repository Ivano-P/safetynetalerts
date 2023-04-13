package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.repository.FirestationsRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordsRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {

    @InjectMocks
    private FirestationService firestationService;

    @Mock
    private FirestationsRepository firestationsRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalRecordsRepository medicalRecordsRepository;

    @Test
    @DisplayName("Verify that repository methods are called once when searching by firestation")
    public void testRepositoryMethodsCalledWhenSearchingByFirestation() {
        // Arrange
        List<String> addressesHandlesByFirestation1 = Arrays.asList("1234 rue jean", "5678 route jacque");

        when(firestationsRepository.sortAdressRelatedToFirestation("1"))
                .thenReturn(addressesHandlesByFirestation1);

        when(personRepository.sortPeopleByFireStation(addressesHandlesByFirestation1.get(0)))
                .thenReturn(new ArrayList<>());
        when(personRepository.sortPeopleByFireStation(addressesHandlesByFirestation1.get(1)))
                .thenReturn(new ArrayList<>());

        // Act
        firestationService.getListOfAdultsAndMinorsCoveredByFirestation("1");

        // Assert
        verify(firestationsRepository, times(1))
                .sortAdressRelatedToFirestation(anyString());
        verify(personRepository, times(2))
                .sortPeopleByFireStation(anyString());
    }


}
