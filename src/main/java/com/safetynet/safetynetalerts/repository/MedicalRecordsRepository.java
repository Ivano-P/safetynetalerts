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
    public List<MedicalRecord> findMedicalRecordsOfPersons(List<Person> listOfPersons) {
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
    public List<String> checkAgesInMedicalRecords(List<Person> listOfPeopleToCheck) {
        ArrayList<String> dateOfBirths = new ArrayList<>();

        for (Person person : listOfPeopleToCheck) {
            String lastName = person.getLastName();
            String firstName = person.getFirstName();

            for (MedicalRecord medicalRecord : listOfAllMedicalRecords) {
                if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)) {
                    dateOfBirths.add(medicalRecord.getBirthdate());
                }
            }
        }
        return dateOfBirths;
    }

    public List<LocalDate> convertListOfStringsToListOfDateOfBirth(List<String> datesOfBirthStrings) {
        ArrayList<LocalDate> datesOfBirthLocalDateFormat = new ArrayList<>();

        for (String dateOfBirth : datesOfBirthStrings) {
            datesOfBirthLocalDateFormat.add(parseDateOfBirth(dateOfBirth));
        }
        return datesOfBirthLocalDateFormat;
    }

    //converts date of birth in string format to date of birth in local date time format
    private LocalDate parseDateOfBirth(String dateOfBirthString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(dateOfBirthString, formatter);
    }


    //converts list of date of births into a list of ages
    public List<Integer> calculateAges(List<LocalDate> datesOfBirth) {
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


    //TODO: delete if not needed after creation of all URLS
    /*
    not used, i created List<MedicalRecord> findMedicalRecordsOfPersons(List<Person> listOfPersons)
    that takes in a list of persons and give back a list of medical records directly.
    //reruns medicalRecord of person using first and last name.
    public MedicalRecord findMedicalRecordOf(String firstName, String lastName){
        MedicalRecord medicalRecordOfPersonByName = null;

        //Loop through list of medical records, when medicalRecord is found with same first and last name, return it
        for(MedicalRecord medicalRecord : listOfAllMedicalRecords){
           if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)){
               medicalRecordOfPersonByName = new MedicalRecord(medicalRecord.getFirstName()
                       ,medicalRecord.getLastName(),medicalRecord.getBirthdate(),medicalRecord.getMedications()
                       ,medicalRecord.getAllergies());
           }
        }
        return medicalRecordOfPersonByName;
    }
    */
}
