package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.PeopleByFirestationNumber;
import com.safetynet.safetynetalerts.dto.PhoneNumbersByFirestation;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.FirestationsRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordsRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FirestationService {

    @Autowired
    FirestationsRepository firestationsRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    MedicalRecordsRepository medicalRecordsRepository;

    public List<Person> getPersonsLinkedToFireStation(String firestationNumber){
        List<String> addressesHandledByFirestation = firestationsRepository
                .sortAdressRelatedToFirestation(firestationNumber);

        List<Person> peopleByFirestation = new ArrayList<>();

        for(String address: addressesHandledByFirestation){
            peopleByFirestation.addAll(personRepository.sortPeopleByFireStation(address));
        }
        return peopleByFirestation;
    }

    public PeopleByFirestationNumber getListOfAdultsAndMinorsCoveredByFirestation(String firestationNumber){
      List<Person> peopleByFirestation = getPersonsLinkedToFireStation(firestationNumber);

      int amountOfPersons = peopleByFirestation.size();
      int amountOfAdults = medicalRecordsRepository.countAmountOfAdults(medicalRecordsRepository
              .calculateAges(medicalRecordsRepository.convertListOfStringsToListOfDateOfBirth(medicalRecordsRepository
                      .checkAgesInMedicalRecords(peopleByFirestation))));

      int amountOfMinors = amountOfPersons - amountOfAdults;

      return new PeopleByFirestationNumber(peopleByFirestation, amountOfAdults, amountOfMinors);
    }


    public PhoneNumbersByFirestation getListOfPhoneNumbersByFirestation(String firestationNumber){
        List<Person> peopleByFirestation = getPersonsLinkedToFireStation(firestationNumber);

        List<String> phoneNumberOfPeopleRelatedToFirestation = new ArrayList<>();

        for (Person person : peopleByFirestation) {
            phoneNumberOfPeopleRelatedToFirestation.add(person.getPhone());
        }

        return new PhoneNumbersByFirestation(phoneNumberOfPeopleRelatedToFirestation);
    }
}
