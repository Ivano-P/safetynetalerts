package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MedicalRecordsRepositoryImplTest {

    private static List<MedicalRecord> mockListOfAllMedicalRecords;
    private MedicalRecordRepositoryImpl medicalRecordsRepositoryImpl;

    @BeforeEach
    public void setUpPerEach() {
        mockListOfAllMedicalRecords = new ArrayList<>();
        mockListOfAllMedicalRecords.add(new MedicalRecord("John", "Doe", "04/04/2024"
                , null, null));
        mockListOfAllMedicalRecords.add(new MedicalRecord("Jane", "Doe", "05/05/2025"
                , null, null));
        mockListOfAllMedicalRecords.add(new MedicalRecord("Jack", "Doe", "06/06/2026"
                , null, null));

        medicalRecordsRepositoryImpl = new MedicalRecordRepositoryImpl(mockListOfAllMedicalRecords);
    }


    @Test
    @DisplayName("test retrieval of list of Persons ages when searching with list of Persons")
    void testCheckAgesInMedicalRecords() {
        //Arrange
        List<String> expextedDatesOfBirth = Arrays.asList("04/04/2024", "05/05/2025", "06/06/2026");

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", null, null, null, null,
                null));
        persons.add(new Person("Jane", "Doe", null, null, null, null,
                null));
        persons.add(new Person("Jack", "Doe", null, null, null, null,
                null));

        //Act
        List<String> actualDatesOfBirths = medicalRecordsRepositoryImpl
                .findDatesOfBirthInMedicalRecordsByPersons(persons);

        //Assert
        assertThat(actualDatesOfBirths.get(0)).isEqualTo(expextedDatesOfBirth.get(0));

    }

    @Test
    @DisplayName("test retrieval of list of medical records of persons")
    void testFindMedicalRecordsByPersons() {
        //Arrange
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", null, null, null, null,
                null));
        persons.add(new Person("Jane", "Doe", null, null, null, null,
                null));

        List<MedicalRecord> expectedMedicalRecords = new ArrayList<>();
        expectedMedicalRecords.add(mockListOfAllMedicalRecords.get(0));
        expectedMedicalRecords.add(mockListOfAllMedicalRecords.get(1));

        //Act
        List<MedicalRecord> actualMedicalRecords = medicalRecordsRepositoryImpl.findMedicalRecordsByPersons(persons);

        //Assert
        assertThat(actualMedicalRecords).containsExactlyInAnyOrderElementsOf(expectedMedicalRecords);
    }

    @Test
    void testConvertListOfStringsToListOfDateOfBirth() {
        //Arrange
        List<String> testDates = Arrays.asList("01/01/2021", "02/02/2022", "03/03/2023");
        List<LocalDate> expectedDatesOfBirth = Arrays.asList(
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2022, 2, 2),
                LocalDate.of(2023, 3, 3)
        );

        //Act
        List<LocalDate> actualDatesOfBirth = medicalRecordsRepositoryImpl
                .convertListDateStringsToListOfDatesOfBirth(testDates);

        //Assert
        assertThat(actualDatesOfBirth).isEqualTo(expectedDatesOfBirth);

    }

    @Test
    @DisplayName("test count of ages over 18")
    void testCountAmountOfAdults() {
        //Arrange
        List<Integer> ages = Arrays.asList(12, 21, 65, 18, 2);

        //Act : Minors are 18 and younger in this case
        int amountOfAdults = medicalRecordsRepositoryImpl.countAmountOfAdults(ages);

        //Assert
        assertThat(amountOfAdults).isEqualTo(2);
    }

    @Test
    @DisplayName("test count of ages 18 and lower")
    void testCountAmountOfMinors() {
        //Arrange
        List<Integer> ages = Arrays.asList(12, 21, 65, 18, 2);

        //Act : Minors are 18 and younger in this case
        int amountOfMinors = medicalRecordsRepositoryImpl.countAmountOfMinors(ages);

        //Assert
        assertThat(amountOfMinors).isEqualTo(3);
    }

    @Test
    void testCalculateAgesByDatesOfBirth() {
        //Arrange
        List<LocalDate> datesOfBirth = Arrays.asList(
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2005, 5, 5),
                LocalDate.of(2010, 10, 10)
        );
        List<Integer> expectedAges = Arrays.asList(23, 18, 12);

        //Act
        List<Integer> actualAges = medicalRecordsRepositoryImpl.calculateAgesByDatesOfBirth(datesOfBirth);

        //Assert
        assertThat(actualAges).isEqualTo(expectedAges);
    }

    @Test
    @DisplayName("test add new medical record")
    void testAddNewMedicalRecord() {
        //Arrange
        MedicalRecord medicalRecordToAdd = new MedicalRecord("Mike", "Johnson", "01/01/2000", null, null);

        //Act
        MedicalRecord actualMedicalRecord = medicalRecordsRepositoryImpl.addNewMedicalRecord(medicalRecordToAdd);
        List<MedicalRecord> medicalRecords = medicalRecordsRepositoryImpl.findMedicalRecordsByPersons(
                List.of(new Person("Mike", "Johnson", null, null, null,
                        null, null)));

        //Assert
        assertThat(actualMedicalRecord).isEqualTo(medicalRecordToAdd);
        assertThat(medicalRecords).contains(medicalRecordToAdd);
    }

    @Test
    @DisplayName("test update existing medical record")
    void testUpdateMedicalRecordByFirstAndLastName() {
        //Arrange
        MedicalRecord medicalRecordToUpdate = new MedicalRecord("John", "Doe", "04/04/2024",
                List.of("some medication"), List.of("some allergies"));

        //Act
        MedicalRecord updatedMedicalRecord = medicalRecordsRepositoryImpl.updateMedicalRecordByFirstAndLastName(medicalRecordToUpdate);
        List<MedicalRecord> medicalRecords = medicalRecordsRepositoryImpl.findMedicalRecordsByPersons(
                List.of(new Person("John", "Doe", null, null, null, null,
                        null)));

        //Assert
        assertThat(updatedMedicalRecord).isEqualTo(medicalRecordToUpdate);
        assertThat(medicalRecords.get(0).getMedications()).isEqualTo(medicalRecordToUpdate.getMedications());
        assertThat(medicalRecords.get(0).getAllergies()).isEqualTo(medicalRecordToUpdate.getAllergies());
    }

    @Test
    @DisplayName("Remove existing medical record by name")
    void testRemoveMedicalRecordByName() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";

        // Act
        MedicalRecord deletedMedicalRecord = medicalRecordsRepositoryImpl.removeMedicalRecordByName(firstName,
                lastName);

        // Assert
        assertThat(deletedMedicalRecord).isNotNull();
        assertThat(mockListOfAllMedicalRecords)
                .isNotEmpty()
                .doesNotContain(deletedMedicalRecord);
    }
}
