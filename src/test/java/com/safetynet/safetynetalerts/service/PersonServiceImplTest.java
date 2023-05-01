package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.MinorAndFamily;
import com.safetynet.safetynetalerts.dto.PeopleMedicalRecordsAndFirestation;
import com.safetynet.safetynetalerts.dto.PersonInfoAndMedicalRecord;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.FirestationRepositoryImpl;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    PersonRepositoryImpl personRepository;

    @Mock
    MedicalRecordRepository medicalRecordsRepository;

    @Mock
    FirestationRepositoryImpl firestationRepository;

    @InjectMocks
    PersonServiceImpl personService;

    private List<Person> personsAtSameAddress;
    private List<MedicalRecord> medicalRecordsOfPeopleAtSameAddress;
    private List<LocalDate> dobList;
    private List<String> dobListString;
    private List<Integer> ages;

    @BeforeEach
    void setUpPerEach() {
        personsAtSameAddress = new ArrayList<>();
        personsAtSameAddress.add(new Person("John", "Doe", "1 route saint george", null, null, null, null));
        personsAtSameAddress.add(new Person("Jane", "Doe", "1 route saint george", null, null, null, null));
        personsAtSameAddress.add(new Person("Jack", "Doe", "1 route saint george", null, null, null, null));

        dobList = Arrays.asList(
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2010, 1, 1),
                LocalDate.of(2015, 1, 1)
        );

        dobListString = Arrays.asList("01/01/2000", "01/01/2010", "01/01/2015");

        ages = Arrays.asList(23, 13, 8); // Assuming the current year is 2023

        medicalRecordsOfPeopleAtSameAddress = Arrays.asList(
                new MedicalRecord("John", "Doe", "01/01/2000", new ArrayList<>(), new ArrayList<>()),
                new MedicalRecord("Jane", "Doe", "01/01/2010", new ArrayList<>(), new ArrayList<>()),
                new MedicalRecord("Jack", "Doe", "01/01/2015", new ArrayList<>(), new ArrayList<>())
        );
    }

    @Test
    void testGetMinorsAndFamilyByAddress() {
        //Arrange
        String address = "1 route saint george";

        when(personRepository.findPeopleByAddress(address)).thenReturn(personsAtSameAddress);
        when(medicalRecordsRepository.findDatesOfBirthInMedicalRecordsByPersons(personsAtSameAddress)).thenReturn(dobListString);
        when(medicalRecordsRepository.convertListDateStringsToListOfDatesOfBirth(dobListString)).thenReturn(dobList);
        when(medicalRecordsRepository.calculateAgesByDatesOfBirth(dobList)).thenReturn(ages);

        //Act
        List<MinorAndFamily> minorAndFamilyByAddress = personService.getMinorsAndFamilyByAddress(address);

        //Assert
        assertThat(minorAndFamilyByAddress).hasSize(2);
        assertThat(minorAndFamilyByAddress.get(0).getAge()).isEqualTo(13);
        assertThat(minorAndFamilyByAddress.get(1).getAge()).isEqualTo(8);
    }

    @Test
    void testGetPeopleMedicalRecordsAndFirestationByAddress() {
        // Arrange
        String address = "1 route saint george";
        String firestationNumber = "2";

        List<MedicalRecord> medicalRecordsOfPeopleAtSameAddress = Arrays.asList(
                new MedicalRecord("John", "Doe", dobListString.get(0), null, null),
                new MedicalRecord("Jane", "Doe", dobListString.get(1), null, null),
                new MedicalRecord("Jack", "Doe", dobListString.get(2), null, null)
        );

        when(personRepository.findPeopleByAddress(address)).thenReturn(personsAtSameAddress);
        when(medicalRecordsRepository.findMedicalRecordsByPersons(personsAtSameAddress)).thenReturn(medicalRecordsOfPeopleAtSameAddress);
        when(medicalRecordsRepository.findDatesOfBirthInMedicalRecordsByPersons(personsAtSameAddress)).thenReturn(dobListString);
        when(medicalRecordsRepository.convertListDateStringsToListOfDatesOfBirth(dobListString)).thenReturn(dobList);
        when(medicalRecordsRepository.calculateAgesByDatesOfBirth(dobList)).thenReturn(ages);
        when(firestationRepository.findFirestationNumberByAddress(address)).thenReturn(firestationNumber);

        // Act
        PeopleMedicalRecordsAndFirestation pmraf = personService.getPeopleMedicalRecordsAndFirestationByAddress(address);

        // Assert
        assertThat(pmraf.getFireStationNumber()).isEqualTo(firestationNumber);
        assertThat(pmraf.getListOfPersonsMedicalRecordsAndFirestationByAddress())
                .hasSize(3);
    }

    @Test
    void testGetPersonInfoAndMedicalRecordByName() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";

        when(personRepository.findPeopleByName(firstName, lastName))
                .thenReturn(personsAtSameAddress);
        when(medicalRecordsRepository.findMedicalRecordsByPersons(personsAtSameAddress))
                .thenReturn(medicalRecordsOfPeopleAtSameAddress);
        when(medicalRecordsRepository
                .findDatesOfBirthInMedicalRecordsByPersons(personsAtSameAddress))
                .thenReturn(dobListString);
        when(medicalRecordsRepository
                .convertListDateStringsToListOfDatesOfBirth(dobListString)).thenReturn(dobList);
        when(medicalRecordsRepository.calculateAgesByDatesOfBirth(dobList))
                .thenReturn(ages);

        // Act
        List<PersonInfoAndMedicalRecord> piamr = personService.getPersonInfoAndMedicalRecordByName(firstName, lastName);

        // Assert
        assertThat(piamr).hasSize(3);
    }

    @Test
    void testGetEmailsByCity() {
        // Arrange
        String city = "TestCity";

        List<Person> personsFromCity = new ArrayList<>(personsAtSameAddress);
        personsFromCity.forEach(person -> person.setCity(city));

        when(personRepository.findPeopleByCity(city)).thenReturn(personsFromCity);

        // Act
        List<String> result = personService.getEmailsByCity(city);

        // Assert
        assertThat(result).hasSize(3);
    }

    @Test
    void testPostNewPerson() {
        // Arrange
        Person newPerson = new Person("New", "Person",
                "123 Main St", "Anytown", "12345", "555-1234",
                "newperson@example.com");
        when(personRepository.addPerson(newPerson)).thenReturn(newPerson);

        // Act
        Person addedPerson = personService.postNewPerson(newPerson);

        // Assert
        assertThat(addedPerson).isEqualTo(newPerson);
        verify(personRepository).addPerson(newPerson);
    }

    @Test
    void testPutPerson() {
        // Arrange
        Person existingPerson = new Person("Existing", "Person",
                "456 Main St", "Anytown", "67890", "555-6789",
                "existingperson@example.com");
        Person updatedPerson = new Person("Existing", "Person",
                "789 Main St", "Anytown", "11111", "555-0000",
                "updatedperson@example.com");
        when(personRepository.updatePerson(updatedPerson)).thenReturn(updatedPerson);

        // Act
        Person editedPerson = personService.putPerson(updatedPerson);

        // Assert
        assertThat(editedPerson).isEqualTo(updatedPerson);
        verify(personRepository).updatePerson(updatedPerson);
    }

    @Test
    void testDeletePerson() {
        // Arrange
        String firstName = "Delete";
        String lastName = "Person";
        Person deletedPerson = new Person(firstName, lastName, "222 Main St",
                "Anytown", "33333", "555-2222",
                "deletedperson@example.com");
        when(personRepository.removePerson(firstName, lastName)).thenReturn(deletedPerson);

        // Act
        Person removedPerson = personService.deletePerson(firstName, lastName);

        // Assert
        assertThat(removedPerson).isEqualTo(deletedPerson);
        verify(personRepository).removePerson(firstName, lastName);
    }

}

