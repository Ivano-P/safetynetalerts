package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.PeopleByFirestationNumber;
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

    public PeopleByFirestationNumber getListOfAdultsAndMinorsCoveredByFirestation(String firestationNumber){
      //adds to this list all addresses handled by fire-station with the fire-station number inputted in parameter.
      List<String> addressHandledByFirestation = firestationsRepository
              .sortAdressRelatedToFirestation(firestationNumber);

      List<Person> peopleByFirestation = new ArrayList<>();

      for(String address: addressHandledByFirestation){
        peopleByFirestation.addAll(personRepository.sortPeopleByFireStation(address));
      }

      int amountOfPersons = peopleByFirestation.size();
      int amountOfAdults = medicalRecordsRepository.getAmountOfAdults(medicalRecordsRepository
              .calculateAges(medicalRecordsRepository.convertListOfStringsToListOfDateOfBirth(medicalRecordsRepository
                      .checkAgesInMedicalRecords(peopleByFirestation))));

      int amountOfMinors = amountOfPersons - amountOfAdults;

      return new PeopleByFirestationNumber(peopleByFirestation, amountOfAdults, amountOfMinors);
    }



}
