package com.safetynet.safetynetalerts.data;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.SafetyNet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExtractObjectTest {

    @Test
    @DisplayName("test that json is being deserialized, SafetyNet should have at the least" +
            "one list of Firestations, one list of Person and one list of MedicalRecords")
    void testExtractDateFromJason(){
        // Arrange: SafetyNet object should have a minimum of one of each list
            //(Firestations, Persons, MedicalRecords)
        int minNumberOfFirestations = 1;
        int minNumberOfPersons = 1;
        int minNumberOfMedicalRecords = 1;

        // Act:
        SafetyNet extractedSafetyNet = ExtractObject.extractDataFromJason();
        List<Firestation> firestations = extractedSafetyNet.getFirestations();
        List<Person> persons = extractedSafetyNet.getPersons();
        List<MedicalRecord> medicalRecords = extractedSafetyNet.getMedicalrecords();

        // Assert:
        assertThat(firestations).hasSizeGreaterThanOrEqualTo(minNumberOfFirestations);
        assertThat(persons).hasSizeGreaterThanOrEqualTo(minNumberOfPersons);
        assertThat(medicalRecords).hasSizeGreaterThanOrEqualTo(minNumberOfMedicalRecords);
    }
}
