package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepositoryImpl;
import com.safetynet.safetynetalerts.repository.PersonRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceImplTest {

    @InjectMocks
    private MedicalRecordServiceImpl medicalRecordServiceImpl;

    @Mock
    private MedicalRecordRepositoryImpl medicalRecordRepositoryImpl;

    @Mock
    private PersonRepositoryImpl personRepositoryImpl;

    private MedicalRecord newMedicalRecord;
    private MedicalRecord updatedMedicalRecord;
    private String firstName;
    private String lastName;
    private MedicalRecord deletedMedicalRecord;

    @BeforeEach
    void setUp() {
        newMedicalRecord = new MedicalRecord("John", "Doe", "01/01/2000", null, null);
        updatedMedicalRecord = new MedicalRecord("John", "Doe", "01/01/2000", null, null);
        firstName = "John";
        lastName = "Doe";
        deletedMedicalRecord = new MedicalRecord(firstName, lastName, "01/01/2000", null, null);
    }

    @Test
    @DisplayName("Verify that a new medical record is added successfully")
    void testPostNewMedicalRecord() {
        // Arrange
        when(medicalRecordRepositoryImpl.addNewMedicalRecord(newMedicalRecord)).thenReturn(newMedicalRecord);
        when(personRepositoryImpl.findPeopleByName(anyString(), anyString()))
                .thenReturn(List.of(new Person("first", "last", "null", "null",
                        "null", "null", "null")));

        // Act
        MedicalRecord result = medicalRecordServiceImpl.postNewMedicalRecord(newMedicalRecord);

        // Assert
        assertThat(result).isEqualTo(newMedicalRecord);
    }

    @Test
    @DisplayName("Verify that a medical record is updated successfully")
    void testPutMedicalRecord() {
        // Arrange
        when(medicalRecordRepositoryImpl.updateMedicalRecordByFirstAndLastName(updatedMedicalRecord))
                .thenReturn(updatedMedicalRecord);

        // Act
        MedicalRecord result = medicalRecordServiceImpl.putMedicalRecord(updatedMedicalRecord);

        // Assert
        assertThat(result).isEqualTo(updatedMedicalRecord);
    }

    @Test
    @DisplayName("Verify that a medical record is deleted successfully")
    void testDeleteMedicalRecord() {
        // Arrange
        when(medicalRecordRepositoryImpl.removeMedicalRecordByName(firstName, lastName)).thenReturn(deletedMedicalRecord);

        // Act
        MedicalRecord result = medicalRecordServiceImpl.deleteMedicalRecord(firstName, lastName);

        // Assert
        assertThat(result).isEqualTo(deletedMedicalRecord);
    }
}
