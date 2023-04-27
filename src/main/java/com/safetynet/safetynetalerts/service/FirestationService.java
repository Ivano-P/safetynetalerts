package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.Houshold;
import com.safetynet.safetynetalerts.dto.PeopleByFirestationNumber;
import com.safetynet.safetynetalerts.dto.PhoneNumbersByFirestation;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.Person;

import java.util.List;

public interface FirestationService {
    List<Person> getPersonsByFireStationNumber(String firestationNumber);
    PeopleByFirestationNumber getAdultsAndMinorsCoveredByFirestationNumber(String firestationNumber);
    PhoneNumbersByFirestation getPhoneNumbersByFirestationNumber(String firestationNumber);
    List<Houshold> getHousholdsByFirestationNumbers(List<String> firestationNumbers);

    Firestation postFireStation(Firestation firestation);

    void putFireStaion(Firestation firestationToUpdate);

    void deleteFirestation(Firestation firestationToDelete);
}
