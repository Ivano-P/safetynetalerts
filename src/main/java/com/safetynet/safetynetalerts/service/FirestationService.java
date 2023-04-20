package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.Houshold;
import com.safetynet.safetynetalerts.dto.PeopleByFirestationNumber;
import com.safetynet.safetynetalerts.dto.PersonWithMedicalInfo;
import com.safetynet.safetynetalerts.dto.PhoneNumbersByFirestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.FirestationsRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordsRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    //TODO: Unit Test
    public List<Houshold> getListOfHousholdsByListOfFirestationNumber(List<String> firestationNumbers){
        List<Houshold> housholdsLinkedToFirestations = new ArrayList<>();
        for (String firestationNumber : firestationNumbers){
            housholdsLinkedToFirestations.addAll(getListOfHousholdsByFirestationNumber(firestationNumber));
        }
        return housholdsLinkedToFirestations;
    }

    //TODO: Unit Test
    public List<Houshold> getListOfHousholdsByFirestationNumber(String firestationNumber){
        List<Person> peopleByFirestation = getPersonsLinkedToFireStation(firestationNumber);

        List<MedicalRecord> medicalRecordsOfPeopleByFirestation = medicalRecordsRepository
                .findMedicalRecordsOfPersons(peopleByFirestation);

        List<Integer> ages = medicalRecordsRepository.calculateAges(medicalRecordsRepository
                .convertListOfStringsToListOfDateOfBirth(medicalRecordsRepository
                        .checkAgesInMedicalRecords(peopleByFirestation)));

        Map<String, List<PersonWithMedicalInfo>> addressToResidentsMap = new HashMap<>();
        /*
        uses the 3 lists (peopleByFirestation, medicalRecordsOfPeopleByFirestation and ages) to creat a
        personWithMedicalInfo this is done in loop and added to the Map addressToResidentsMap for each address
         */
        for (int i = 0; i < peopleByFirestation.size(); i++) {
            Person person = peopleByFirestation.get(i);
            MedicalRecord medicalRecord = medicalRecordsOfPeopleByFirestation.get(i);
            int age = ages.get(i);

            PersonWithMedicalInfo personWithMedicalInfo = new PersonWithMedicalInfo(person.getFirstName()
                    , person.getLastName(), person.getPhone(), age, medicalRecord.getMedications()
                    , medicalRecord.getAllergies());
            String address = person.getAddress();

            /*
            check if address is already in map, if not, creat new empty list of PersonWithMedicalInfo and associate
            it with the address in the map.
            */
            addressToResidentsMap.computeIfAbsent(address, k -> new ArrayList<>()).add(personWithMedicalInfo);
        }

        List<Houshold> households = new ArrayList<>();

        //add Houshold to list of Housholds by using key and value of hashMap to creat each Houshold object
        for (Map.Entry<String, List<PersonWithMedicalInfo>> entry : addressToResidentsMap.entrySet()) {
            households.add(new Houshold(entry.getKey(), entry.getValue()));
        }

        return households;
    }



}
