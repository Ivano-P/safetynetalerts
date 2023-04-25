package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.Houshold;
import com.safetynet.safetynetalerts.dto.PeopleByFirestationNumber;
import com.safetynet.safetynetalerts.dto.PersonWithMedicalInfo;
import com.safetynet.safetynetalerts.dto.PhoneNumbersByFirestation;
import com.safetynet.safetynetalerts.model.Firestation;
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

    public List<Person> getPersonsByFireStationNumber(String firestationNumber){
        List<String> addressesHandledByFirestation = firestationsRepository
                .findAddressByFirestationNumber(firestationNumber);

        List<Person> peopleByFirestation = new ArrayList<>();

        for(String address: addressesHandledByFirestation){
            peopleByFirestation.addAll(personRepository.findPeopleByFireStationAddress(address));
        }
        return peopleByFirestation;
    }

    public PeopleByFirestationNumber getAdultsAndMinorsCoveredByFirestationNumber(String firestationNumber){
      List<Person> peopleByFirestation = getPersonsByFireStationNumber(firestationNumber);

      int amountOfPersons = peopleByFirestation.size();
      int amountOfAdults = medicalRecordsRepository.countAmountOfAdults(medicalRecordsRepository
              .calculateAgesByDatesOfBirth(medicalRecordsRepository.convertListDateStringsToListOfDatesOfBirth(medicalRecordsRepository
                      .findDatesOfBirthInMedicalRecordsByPersons(peopleByFirestation))));

      int amountOfMinors = amountOfPersons - amountOfAdults;

      return new PeopleByFirestationNumber(peopleByFirestation, amountOfAdults, amountOfMinors);
    }


    public PhoneNumbersByFirestation getPhoneNumbersByFirestationNumber(String firestationNumber){
        List<Person> peopleByFirestation = getPersonsByFireStationNumber(firestationNumber);

        List<String> phoneNumberOfPeopleRelatedToFirestation = new ArrayList<>();

        for (Person person : peopleByFirestation) {
            phoneNumberOfPeopleRelatedToFirestation.add(person.getPhone());
        }

        return new PhoneNumbersByFirestation(phoneNumberOfPeopleRelatedToFirestation);
    }


    //TODO: Unit Test
    public List<Houshold> getHousholdsByFirestationNumbers(List<String> firestationNumbers){
        List<Houshold> housholdsLinkedToFirestations = new ArrayList<>();
        for (String firestationNumber : firestationNumbers){
            housholdsLinkedToFirestations.addAll(getHousholdsByFirestationNumber(firestationNumber));
        }
        return housholdsLinkedToFirestations;
    }

    //TODO: Unit Test
    private List<Houshold> getHousholdsByFirestationNumber(String firestationNumber){
        List<Person> peopleByFirestation = getPersonsByFireStationNumber(firestationNumber);

        List<MedicalRecord> medicalRecordsOfPeopleByFirestation = medicalRecordsRepository
                .findMedicalRecordsByPersons(peopleByFirestation);

        List<Integer> ages = medicalRecordsRepository.calculateAgesByDatesOfBirth(medicalRecordsRepository
                .convertListDateStringsToListOfDatesOfBirth(medicalRecordsRepository
                        .findDatesOfBirthInMedicalRecordsByPersons(peopleByFirestation)));

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

    //TODO: add unit test<
    //add firestation from request boddy to listOfAllFirestations
    public Firestation postFireStation(Firestation firestation) {
        return firestationsRepository.addFirestation(firestation);
    }

    /*
    Creates a new Firestations with the informations from the parameters in the request and add that new Firestation
    to listOfAllFirestations

    public Firestation postFiresationAndAddressCoverage(String stationNumber, String addressToCover) {
        return firestationsRepository.addFirestation(new Firestation(addressToCover, stationNumber));
    }
    */

    //TODO: add unit test
    public void putFireStaion(Firestation firestationToUpdate) {
        firestationsRepository.updateFirestationByAddress(firestationToUpdate.getAddress(), firestationToUpdate.getStation());
    }

    //TODO: add unit test
    public void deleteFirestation(Firestation firestationToDelete) {
        firestationsRepository.removeFirestationByAddress(firestationToDelete.getAddress());
    }
}
