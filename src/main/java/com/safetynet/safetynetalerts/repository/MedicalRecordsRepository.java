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


    //to get the dob of a list of people from medical records using their first and last name.
    public ArrayList<String> checkAgesInMedicalRecords(List<Person> listOfPeopleToCheck){
        List<MedicalRecord> medicalRecords = extractDataFromJason().getMedicalrecords();
        ArrayList<String> dateOfBirths = new ArrayList<String>();

        for(Person person : listOfPeopleToCheck){
            String lastName = person.getLastName();
            String firstName = person.getFirstName();

            for(MedicalRecord medicalRecord : medicalRecords) {
                if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)){
                    dateOfBirths.add(medicalRecord.getBirthdate());
                }
            }
        }
        return dateOfBirths;
    }

    public ArrayList<LocalDate> convertListOfStringsToListOfDateOfBirth(List<String> datesOfBirthStrings){
        ArrayList<LocalDate> datesOfBirthLocalDateFormat = new ArrayList<>();

        for (String dateOfBirth : datesOfBirthStrings){
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
    public List<Integer> calculateAges(ArrayList<LocalDate> datesOfBirth) {
        ArrayList<Integer> ages = new ArrayList<>();
        for (LocalDate dateOfBirth : datesOfBirth){
            LocalDate now = LocalDate.now();
            Period period = Period.between(dateOfBirth, now);
            ages.add(period.getYears());
        }
        return ages;
    }

    public Integer getAmountOfAdults(List<Integer> ages){
        int amountOfAdults = 0;
        for (int age : ages){
            if (age >= 19){
                amountOfAdults ++;
            }
        }
        return amountOfAdults;
    }

    public int getAmountOfMinors(List<Integer> ages){
        int amountOfMinors = 0;
        for (int age : ages){
            if (age < 19){
                amountOfMinors ++;
            }
        }
        return amountOfMinors;
    }


    /*to get the dob of one people using their first and last name.
    public String checkAgesOfPersonInMedicalRecords(String firstName, String lastName){
        List<MedicalRecord> medicalRecords = extractDataFromJason().getMedicalrecords();
        String dateOfBirths = null;

        for(MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)){
                dateOfBirths = medicalRecord.getBirthdate();
            }
        }
        return dateOfBirths;
    }
    */



}
