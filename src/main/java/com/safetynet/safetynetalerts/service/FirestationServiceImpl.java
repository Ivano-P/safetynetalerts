package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.Houshold;
import com.safetynet.safetynetalerts.dto.PeopleByFirestationNumber;
import com.safetynet.safetynetalerts.dto.PersonWithMedicalInfo;
import com.safetynet.safetynetalerts.dto.PhoneNumbersByFirestation;
import com.safetynet.safetynetalerts.exceptions.IncompleteRequestException;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.FirestationRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@Primary
public class FirestationServiceImpl implements FirestationService{

    private final FirestationRepository firestationRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PersonRepository personRepository;

    @Autowired
    public FirestationServiceImpl(FirestationRepository firestationRepository,
                                  MedicalRecordRepository medicalRecordRepository,
                                  PersonRepository personRepository){

        this.firestationRepository = firestationRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getPersonsByFireStationNumber(String firestationNumber){

        log.debug("getPersonsByFireStationNumber() " + firestationNumber);
        List<String> addressesHandledByFirestation = firestationRepository
                .findAddressByFirestationNumber(firestationNumber);

        List<Person> peopleByFirestation = new ArrayList<>();

        for(String address: addressesHandledByFirestation){

            peopleByFirestation.addAll(personRepository.findPeopleByFireStationAddress(address));
        }
        log.info("Completed getPersonsByFireStationNumber() " + firestationNumber);
        return peopleByFirestation;
    }

    @Override
    public PeopleByFirestationNumber getAdultsAndMinorsCoveredByFirestationNumber(String firestationNumber){

        log.debug("getAdultsAndMinorsCoveredByFirestationNumber() " + firestationNumber);
        List<Person> peopleByFirestation = getPersonsByFireStationNumber(firestationNumber);

        int amountOfPersons = peopleByFirestation.size();
        int amountOfAdults = medicalRecordRepository.countAmountOfAdults(medicalRecordRepository
              .calculateAgesByDatesOfBirth(medicalRecordRepository
                      .convertListDateStringsToListOfDatesOfBirth(medicalRecordRepository
                      .findDatesOfBirthInMedicalRecordsByPersons(peopleByFirestation))));

        int amountOfMinors = amountOfPersons - amountOfAdults;
        log.info("completed getAdultsAndMinorsCoveredByFirestationNumber() " + firestationNumber);
        return new PeopleByFirestationNumber(peopleByFirestation, amountOfAdults, amountOfMinors);
    }


    @Override
    public PhoneNumbersByFirestation getPhoneNumbersByFirestationNumber(String firestationNumber){

        log.debug("getPhoneNumbersByFirestationNumber() " + firestationNumber);
        List<Person> peopleByFirestation = getPersonsByFireStationNumber(firestationNumber);

        List<String> phoneNumberOfPeopleRelatedToFirestation = new ArrayList<>();

        for (Person person : peopleByFirestation) {
            phoneNumberOfPeopleRelatedToFirestation.add(person.getPhone());
        }
        log.info("Compelted getPhoneNumbersByFirestationNumber() " + firestationNumber);
        return new PhoneNumbersByFirestation(phoneNumberOfPeopleRelatedToFirestation);
    }


    @Override
    public List<Houshold> getHousholdsByFirestationNumbers(List<String> firestationNumbers){

        log.debug("getHousholdsByFirestationNumbers() " + firestationNumbers);
        List<Houshold> housholdsLinkedToFirestations = new ArrayList<>();

        for (String firestationNumber : firestationNumbers){
            List<Person> peopleByFirestation = getPersonsByFireStationNumber(firestationNumber);
            List<MedicalRecord> medicalRecordsOfPeopleByFirestation = medicalRecordRepository
                    .findMedicalRecordsByPersons(peopleByFirestation);

            List<Integer> ages = medicalRecordRepository.calculateAgesByDatesOfBirth(medicalRecordRepository
                    .convertListDateStringsToListOfDatesOfBirth(medicalRecordRepository
                            .findDatesOfBirthInMedicalRecordsByPersons(peopleByFirestation)));

            Map<String, List<PersonWithMedicalInfo>> addressToResidentsMap = new HashMap<>();
            /*
            Uses the 3 lists (peopleByFirestation, medicalRecordsOfPeopleByFirestation and ages) to create a
            personWithMedicalInfo. This is done in a loop and added to the Map addressToResidentsMap for each address.
             */
            for (int i = 0; i < peopleByFirestation.size(); i++) {
                Person person = peopleByFirestation.get(i);
                MedicalRecord medicalRecord = medicalRecordsOfPeopleByFirestation.get(i);
                int age = ages.get(i);

                PersonWithMedicalInfo personWithMedicalInfo = new PersonWithMedicalInfo(person.getFirstName()
                        , person.getLastName(), person.getPhone(), age, medicalRecord.getMedications()
                        , medicalRecord.getAllergies());
                String address = person.getAddress();

                // Check if address is already in the map, if not, create a new empty list of PersonWithMedicalInfo and associate
                // it with the address in the map.
                addressToResidentsMap.computeIfAbsent(address, k -> new ArrayList<>()).add(personWithMedicalInfo);
            }

            // Add Houshold to the list of Housholds by using key and value of hashMap to create each Houshold object.
            for (Map.Entry<String, List<PersonWithMedicalInfo>> entry : addressToResidentsMap.entrySet()) {
                housholdsLinkedToFirestations.add(new Houshold(entry.getKey(), entry.getValue()));
            }

            log.info("completed getHousholdsByFirestationNumbers() " + firestationNumbers);
        }

        return housholdsLinkedToFirestations;
    }


    //add firestation from request boddy to listOfAllFirestations
    @Override
    public Firestation postFireStation(Firestation firestation)  {

        log.debug("postFireStation() " + firestation);
        // Check if the required fields are present in the Firestation object
        if (firestation.getAddress() == null || firestation.getAddress().isEmpty() ||
                firestation.getStation() == null || firestation.getStation().isEmpty()) {
            log.error("Incomplete Request Exception");
            throw new IncompleteRequestException("Incomplete request. Both 'address' and 'station' fields must be " +
                    "provided in the request body.");
        }

        return firestationRepository.addFirestation(firestation);
    }

    @Override
    public Firestation putFireStaion(Firestation firestationToUpdate) {

        log.debug("putFireStaion() " + firestationToUpdate);
        // Check if the required fields are present in the Firestation object
        if (firestationToUpdate.getAddress() == null || firestationToUpdate.getAddress().isEmpty() ||
                firestationToUpdate.getStation() == null || firestationToUpdate.getStation().isEmpty()) {
            log.error("Incomplete Request Exception");
            throw new IncompleteRequestException("Incomplete request. Both 'address' and 'station' fields must be " +
                    "provided in the request body.");
        }
       return firestationRepository.updateFirestationByAddress(firestationToUpdate.getAddress(), firestationToUpdate
                .getStation());
    }

    @Override
    public Firestation deleteFirestation(Firestation firestationToDelete)  {

        log.debug("deleteFirestation() " + firestationToDelete);
       return firestationRepository.removeFirestationByAddress(firestationToDelete.getAddress());
    }
}
