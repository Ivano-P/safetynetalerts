package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Firestation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirestationRepository {
    List<String> findAddressByFirestationNumber(String firestationNumber);
    String findFirestationNumberByAddress(String address);
    Firestation addFirestation(Firestation firestationToAdd);
    void updateFirestationByAddress(String addressCoveredByFirestation, String firestationNumberToUpdate);
    void removeFirestationByAddress(String firestationCoveredAddress);
}
