package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.dao.ExtractObject;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.SafetyNet;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class MedicalRecordsRepositoryTest {
    private MedicalRecordsRepository medicalRecordsRepository;

    @BeforeEach
    public void setUp() {
        medicalRecordsRepository = new MedicalRecordsRepository();
    }


    @Test
    @Disabled
    public void testCheckAgesInMedicalRecords() {
        //Arrange
        List<String> expextedDatesOfBirth = Arrays.asList("04/04/2024", "05/05/2025", "06/06/2026");

        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord("John", "Doe", expextedDatesOfBirth.get(0)
                , null, null));
        medicalRecords.add(new MedicalRecord("Jane", "Doe", expextedDatesOfBirth.get(1)
                , null, null));
        medicalRecords.add(new MedicalRecord("Jack", "Doe", expextedDatesOfBirth.get(2)
                , null, null));

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", null, null, null, null, null));
        persons.add(new Person("Jane", "Doe", null, null, null, null, null));
        persons.add(new Person("Jack", "Doe", null, null, null, null, null));


        //ExtracObject returns a static SafetyNet object, hence the use pf MockStatic
        try (MockedStatic<ExtractObject> mockedStaticClass = Mockito.mockStatic(ExtractObject.class)) {
            SafetyNet mockedSafetyNet = new SafetyNet(null, null, medicalRecords);

            mockedStaticClass.when(ExtractObject::extractDataFromJason)
                    .thenReturn(mockedSafetyNet);

            //Act
            List<String> actualDatesOfBirths = medicalRecordsRepository
                    .checkAgesInMedicalRecords(persons);

            //Assert
            assertThat(actualDatesOfBirths).isEqualTo(expextedDatesOfBirth);
        }


    }

    @Test
    public void testConvertListOfStringsToListOfDateOfBirth() {
        //Arrange
        List<String> testDates = Arrays.asList("01/01/2021", "02/02/2022", "03/03/2023");
        List<LocalDate> expectedDatesOfBirth = Arrays.asList(
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2022, 2, 2),
                LocalDate.of(2023, 3, 3)
        );

        //Act
        List<LocalDate> actualDatesOfBirth = medicalRecordsRepository
                .convertListOfStringsToListOfDateOfBirth(testDates);

        //Assert
        assertThat(actualDatesOfBirth).isEqualTo(expectedDatesOfBirth);

    }

    @Test
    @DisplayName("test count of ages over 18")
    public void testCountAmountOfAdults() {
        //Arrange
        List<Integer> ages = Arrays.asList(12, 21, 65, 18, 2);

        //Act : Minors are 18 and younger in this case
        int amountOfAdults = medicalRecordsRepository.countAmountOfAdults(ages);

        //Assert
        assertThat(amountOfAdults).isEqualTo(2);
    }

    @Test
    @DisplayName("test count of ages 18 and lower")
    public void testCountAmountOfMinors() {
        //Arrange
        List<Integer> ages = Arrays.asList(12, 21, 65, 18, 2);

        //Act : Minors are 18 and younger in this case
        int amountOfMinors = medicalRecordsRepository.countAmountOfMinors(ages);

        //Assert
        assertThat(amountOfMinors).isEqualTo(3);
    }
}
