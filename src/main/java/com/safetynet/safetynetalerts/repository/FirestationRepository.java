package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.exceptions.FirestationNotFoundException;
import com.safetynet.safetynetalerts.model.Firestation;

import java.util.List;

public interface FirestationRepository {
    List<String> findAddressByFirestationNumber(String firestationNumber);
    String findFirestationNumberByAddress(String address);
    Firestation addFirestation(Firestation firestationToAdd);
    Firestation updateFirestationByAddress(String addressCoveredByFirestation, String firestationNumberToUpdate);
    Firestation removeFirestationByAddress(String firestationCoveredAddress) throws FirestationNotFoundException;
}
