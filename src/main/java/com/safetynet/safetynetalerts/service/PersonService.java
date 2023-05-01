package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.MinorAndFamily;
import com.safetynet.safetynetalerts.dto.PeopleMedicalRecordsAndFirestation;
import com.safetynet.safetynetalerts.dto.PersonInfoAndMedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

import java.util.List;

public interface PersonService {
    List<MinorAndFamily> getMinorsAndFamilyByAddress(String address);
    PeopleMedicalRecordsAndFirestation getPeopleMedicalRecordsAndFirestationByAddress(String address);
    List<PersonInfoAndMedicalRecord> getPersonInfoAndMedicalRecordByName(String firstName, String lastName);
    List<String> getEmailsByCity(String city);
    Person postNewPerson(Person newPersonToAd);
    Person putPerson(Person personToEdit);
    Person deletePerson(String firstName, String lastName);
}
