package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.FirestationsRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordsRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    //to get dob to calculate age
    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;

    @Autowired
    private FirestationsRepository firestationsRepository;

    public List<MinorAndFamily> getMinorsAndFamilyByAddress(String address){
        List<Person> personsAtSameAddress = personRepository.findPeopleByAddress(address);

        //List with age for each person from list 'personAtSameAddress'
        List<Integer> ages = medicalRecordsRepository.calculateAgesByDatesOfBirth( medicalRecordsRepository
                .convertListDateStringsToListOfDatesOfBirth(medicalRecordsRepository
                        .findDatesOfBirthInMedicalRecordsByPersons(personsAtSameAddress)));

        List<MinorAndFamily> minorsAgeAndFamily = new ArrayList<>();

        // Iterate over all ages and create MinorAndFamily instances for minors
        for (int i = 0; i < ages.size(); i++) {
            if(ages.get(i) < 19){
                List<Person> familyMembersAtSameAddress = new ArrayList<>();
                // adds all other family member except the minor on index i of personsAtSameAddress to new list
                for (int j = 0; j < personsAtSameAddress.size(); j++) {
                    if (j != i) {
                        familyMembersAtSameAddress.add(personsAtSameAddress.get(j));
                    }
                }
                // Create and add a new MinorAndFamily instance to the list
                minorsAgeAndFamily.add(new MinorAndFamily(personsAtSameAddress.get(i), ages.get(i)
                        ,familyMembersAtSameAddress)
                );
            }
        }
        return minorsAgeAndFamily;
    }

    //TODO: Unit Test
    public PeopleMedicalRecordsAndFirestation getPeopleMedicalRecordsAndFirestationByAddress(String address){
        List<PersonAndMedicalRecord> listOfPersonsMedicalRecordAndFireStation = new ArrayList<>();

        //List off people at inputted address
        List<Person> personsAtSameAddress = personRepository.findPeopleByAddress(address);

        //List of MedicalRecords of the people at that address
        List<MedicalRecord> medicalRecordsOfPeopleAtSameAddress = medicalRecordsRepository
                .findMedicalRecordsByPersons(personsAtSameAddress);

        //list of dates of people of the people in personsAtSameAddress
        List<Integer> ages =medicalRecordsRepository.calculateAgesByDatesOfBirth(medicalRecordsRepository
                .convertListDateStringsToListOfDatesOfBirth(medicalRecordsRepository
                        .findDatesOfBirthInMedicalRecordsByPersons(personsAtSameAddress)));

        /*
        goes through list of people at same address and for each person adds creats a
        PeopleMedicalRecordAndFirestation object that is added to listOfPersonsMedicalRecordAndFireStation
        and then returned for creation of dto object
        */
        for (int i=0; i < personsAtSameAddress.size(); i++){
            listOfPersonsMedicalRecordAndFireStation
                    .add(i, new PersonAndMedicalRecord(personsAtSameAddress.get(i).getFirstName()
                            ,personsAtSameAddress.get(i).getLastName(), personsAtSameAddress.get(i).getPhone()
                            ,ages.get(i), medicalRecordsOfPeopleAtSameAddress.get(i).getMedications()
                            ,medicalRecordsOfPeopleAtSameAddress.get(i).getAllergies()));
        }
        return new PeopleMedicalRecordsAndFirestation(listOfPersonsMedicalRecordAndFireStation
                , firestationsRepository.findFirestationNumberByAddress(personsAtSameAddress.get(0).getAddress()));
    }

    //TODO: unit test
    public List<PersonInfoAndMedicalRecord> getPersonInfoAndMedicalRecordByName(String firstName, String lastName){
        //stores list of people found  with that first and lsat name.
        List<Person> personByName = personRepository.findPeopleByName(firstName, lastName);
        //stores their medical records
        List<MedicalRecord> medicalRecordsOfPersonByName =medicalRecordsRepository
                .findMedicalRecordsByPersons(personByName);
        //stores their ages
        List<Integer> ages =medicalRecordsRepository.calculateAgesByDatesOfBirth(medicalRecordsRepository
                .convertListDateStringsToListOfDatesOfBirth(medicalRecordsRepository
                        .findDatesOfBirthInMedicalRecordsByPersons(personByName)));

        //for method return
        List<PersonInfoAndMedicalRecord> personInfoAndMedicalRecords = new ArrayList<>();

        /*
        go through list of person, age and medical records and use the same index of each list to fetch information
        needed to creat personInfoAndMedicalRecords object. this object is added to as list of the same type in case
        there are multiple people with the same name
         */
        for (int i= 0; i < personByName.size(); i++){
            Person person = personByName.get(i);
            MedicalRecord medicalRecord = medicalRecordsOfPersonByName.get(i);
            int age = ages.get(i);

        personInfoAndMedicalRecords.add(new PersonInfoAndMedicalRecord(person.getFirstName(), person.getLastName()
                , person.getAddress(), person.getCity(), person.getZip(), age ,person.getEmail()
                , medicalRecord.getMedications(), medicalRecord.getAllergies()));
        }
        return personInfoAndMedicalRecords;
    }

    //TODO: unit test
    public List<String> getEmailsByCity(String city){
        List<Person> personsFromCity = personRepository.findPeopleByCity(city);

        //retrieves the email from each person in that list of people from that city
        List<String> personsEmailFromCity = new ArrayList<>();
        for (Person person : personsFromCity){
            if (person.getCity().equals(city)){
            personsEmailFromCity.add(person.getEmail());
            }
        }
        return personsEmailFromCity;
    }

    //adds newPersonToAd as a list of one newPersonToAd, since safetyNet take a list of Persons
    //TODO: unit test
    public Person postNewPerson(Person newPersonToAd){
        personRepository.addPerson(newPersonToAd);

        return newPersonToAd;
    }

    //TODO: unit test
    public void putPerson(Person personToEdit){
        personRepository.updatePerson(personToEdit);
    }

    public Person deletePerson(String firstName, String lastName) {
        return personRepository.removePerson(firstName, lastName);
    }
}
