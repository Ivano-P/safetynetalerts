package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

import java.time.LocalDate;
import java.util.List;

public interface MedicalRecordRepository {
    List<MedicalRecord> findMedicalRecordsByPersons(List<Person> listOfPersons);

    List<String> findDatesOfBirthInMedicalRecordsByPersons(List<Person> listOfPeopleToCheck);

    List<LocalDate> convertListDateStringsToListOfDatesOfBirth(List<String> datesOfBirthStrings);

    List<Integer> calculateAgesByDatesOfBirth(List<LocalDate> datesOfBirth);

    Integer countAmountOfAdults(List<Integer> ages);

    int countAmountOfMinors(List<Integer> ages);

    MedicalRecord addNewMedicalRecord(MedicalRecord medicalRecordToAdd);

    MedicalRecord updateMedicalRecordByFirstAndLastName(MedicalRecord updatedMedicalRecord);

    MedicalRecord removeMedicalRecordByName(String firstName, String lastName);
}
