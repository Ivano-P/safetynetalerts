package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.MinorAndFamilyByAddress;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.MedicalRecordsRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    PersonRepository personRepository;

    @Mock
    MedicalRecordsRepository medicalRecordsRepository;

    @InjectMocks
    PersonService personService;

    private List<Person> personsAtSameAddress;
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
    }

    @Test
    void testGetListMinorsAndFamilyByAddress() {
        //Arrange
        String address = "1 route saint george";

        when(personRepository.sortPeopleByAddress(address)).thenReturn(personsAtSameAddress);
        when(medicalRecordsRepository.checkAgesInMedicalRecords(personsAtSameAddress)).thenReturn(dobListString);
        when(medicalRecordsRepository.convertListOfStringsToListOfDateOfBirth(dobListString)).thenReturn(dobList);
        when(medicalRecordsRepository.calculateAges(dobList)).thenReturn(ages);

        //Act
        MinorAndFamilyByAddress minorAndFamilyByAddress = personService.getListMinorsAndFamilyByAddress(address);

        //Assert
        assertThat(minorAndFamilyByAddress.getMinorAndFamilies()).hasSize(2);
        assertThat(minorAndFamilyByAddress.getMinorAndFamilies().get(0).getAge()).isEqualTo(13);
        assertThat(minorAndFamilyByAddress.getMinorAndFamilies().get(1).getAge()).isEqualTo(8);
    }

}

