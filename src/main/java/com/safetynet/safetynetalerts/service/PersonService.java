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
    PersonRepository personRepository;

    //to get dob to calculate age
    @Autowired
    MedicalRecordsRepository medicalRecordsRepository;

    @Autowired
    FirestationsRepository firestationsRepository;

    public List<MinorAndFamily> getListMinorsAndFamilyByAddress(String address){
        List<Person> personsAtSameAddress = personRepository.sortPeopleByAddress(address);

        //List with age for each person from list 'personAtSameAddress'
        List<Integer> ages = medicalRecordsRepository.calculateAges( medicalRecordsRepository
                .convertListOfStringsToListOfDateOfBirth(medicalRecordsRepository
                        .checkAgesInMedicalRecords(personsAtSameAddress)));

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
    public PeopleMedicalRecordsAndFirestationByAddress getListOfPeopleMedicalRecordsAndFirestation(String address){
        List<PersonAndMedicalRecord> listOfPersonsMedicalRecordAndFireStation = new ArrayList<>();

        //List off people at inputted address
        List<Person> personsAtSameAddress = personRepository.sortPeopleByAddress(address);

        //List of MedicalRecords of the people at that address
        List<MedicalRecord> medicalRecordsOfPeopleAtSameAddress = medicalRecordsRepository
                .findMedicalRecordsOfPersons(personsAtSameAddress);

        //list of dates of people of the people in personsAtSameAddress
        List<String> datesOfBirths = medicalRecordsRepository.checkAgesInMedicalRecords(personsAtSameAddress);

        /*
        goes through list of people at same address and for each person adds creats a
        PeopleMedicalRecordAndFirestation object that is added to listOfPersonsMedicalRecordAndFireStation
        and then returned for creation of dto object
        */
        for (int i=0; i < personsAtSameAddress.size(); i++){
            listOfPersonsMedicalRecordAndFireStation
                    .add(i, new PersonAndMedicalRecord(personsAtSameAddress.get(i).getFirstName()
                            ,personsAtSameAddress.get(i).getLastName(), personsAtSameAddress.get(i).getPhone()
                            ,datesOfBirths.get(i), medicalRecordsOfPeopleAtSameAddress.get(i).getMedications()
                            ,medicalRecordsOfPeopleAtSameAddress.get(i).getAllergies()));
        }
        return new PeopleMedicalRecordsAndFirestationByAddress(listOfPersonsMedicalRecordAndFireStation
                , firestationsRepository.checkFireStationNumberWithAdress(personsAtSameAddress.get(0).getAddress()));
    }




}
