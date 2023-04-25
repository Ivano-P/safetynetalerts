package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynetalerts.dao.ExtractObject.extractDataFromJason;


@Repository
public class MedicalRecordsRepository {

    List<MedicalRecord> listOfAllMedicalRecords;

    //Dependency injection constructor for production
    public MedicalRecordsRepository() {
        this.listOfAllMedicalRecords = extractDataFromJason().getMedicalRecords();
    }

    //Dependency injection consctructor for test
    public MedicalRecordsRepository(List<MedicalRecord> listOfAllMedicalRecords) {
        this.listOfAllMedicalRecords = listOfAllMedicalRecords;
    }


    //TODO: Unit Test
    //returns list of medicalRecords of persons using list of persons
    public List<MedicalRecord> findMedicalRecordsByPersons(List<Person> listOfPersons) {
        List<MedicalRecord> medicalRecordsOfPersons = new ArrayList<>();

        for (Person person : listOfPersons) {
            for (MedicalRecord medicalRecord : listOfAllMedicalRecords) {
                if (person.getFirstName().equals(medicalRecord
                        .getFirstName()) && person.getLastName().equals(medicalRecord
                        .getLastName())) {
                    medicalRecordsOfPersons.add(medicalRecord);
                }
            }
        }
        return medicalRecordsOfPersons;
    }


    //to get the dob of a list of people from medical records using their first and last name.
    public List<String> findDatesOfBirthInMedicalRecordsByPersons(List<Person> listOfPeopleToCheck) {
        ArrayList<String> datesOfBirth = new ArrayList<>();

        for (Person person : listOfPeopleToCheck) {
            String lastName = person.getLastName();
            String firstName = person.getFirstName();

            for (MedicalRecord medicalRecord : listOfAllMedicalRecords) {
                if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)) {
                    datesOfBirth.add(medicalRecord.getBirthdate());
                }
            }
        }
        return datesOfBirth;
    }

    /*
    Iterates through list of String dates of biths and uses "convertDateStringToLocalDate()" to convert each dob
    in the list
     */
    public List<LocalDate> convertListDateStringsToListOfDatesOfBirth(List<String> datesOfBirthStrings) {
        ArrayList<LocalDate> datesOfBirthLocalDateFormat = new ArrayList<>();

        for (String dateOfBirth : datesOfBirthStrings) {
            datesOfBirthLocalDateFormat.add(convertDateStringToLocalDate(dateOfBirth));
        }
        return datesOfBirthLocalDateFormat;
    }

    //converts single date of birth in string format to date of birth in local date time format
    private LocalDate convertDateStringToLocalDate(String dateOfBirthString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(dateOfBirthString, formatter);
    }


    //converts list of date of births into a list of ages
    public List<Integer> calculateAgesByDatesOfBirth(List<LocalDate> datesOfBirth) {
        ArrayList<Integer> ages = new ArrayList<>();
        for (LocalDate dateOfBirth : datesOfBirth) {
            LocalDate now = LocalDate.now();
            Period period = Period.between(dateOfBirth, now);
            ages.add(period.getYears());
        }
        return ages;
    }

    public Integer countAmountOfAdults(List<Integer> ages) {
        int amountOfAdults = 0;
        for (int age : ages) {
            if (age >= 19) {
                amountOfAdults++;
            }
        }
        return amountOfAdults;
    }

    public int countAmountOfMinors(List<Integer> ages) {
        int amountOfMinors = 0;
        for (int age : ages) {
            if (age < 19) {
                amountOfMinors++;
            }
        }
        return amountOfMinors;
    }

    //TODO: unit test
    public void addNewMedicalRecord(MedicalRecord medicalRecordToAdd) {
        listOfAllMedicalRecords.add(medicalRecordToAdd);
    }

    /*
    checks for a MedicalRecord with same first and last name and if found sets all the informations to the value of
    the MedicalRecord imputed as arguement
     */
    public void updateMedicalRecordByFirstAndLastName(MedicalRecord updatedMedicalRecord) {
        for(MedicalRecord medicalRecord : listOfAllMedicalRecords){
            if (medicalRecord.getLastName().equals(updatedMedicalRecord.getLastName()) && medicalRecord.getFirstName()
                    .equals(updatedMedicalRecord.getFirstName())) {

                medicalRecord.setBirthdate(updatedMedicalRecord.getBirthdate());
                medicalRecord.setMedications(updatedMedicalRecord.getMedications());
                medicalRecord.setAllergies(updatedMedicalRecord.getAllergies());
                break;
            }
        }
    }

    //checks for a MedicalRecord with same first and last name and if found deletes it
    public void removeMedicalRecordByName(String firstName, String lastName) {
        for(int i = 0 ; i < listOfAllMedicalRecords.size(); i++){
            MedicalRecord medicalRecord = listOfAllMedicalRecords.get(i);
            if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)){
                listOfAllMedicalRecords.remove(i);
                break;
            }
        }
    }
}
