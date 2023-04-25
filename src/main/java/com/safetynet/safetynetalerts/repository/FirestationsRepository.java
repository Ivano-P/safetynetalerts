package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Firestation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynetalerts.dao.ExtractObject.extractDataFromJason;

//make repo!
@Repository
public class FirestationsRepository {
    List<Firestation> listOfAllFirestations;

    //Dependency injection constructor for productions
    public FirestationsRepository(){
        this.listOfAllFirestations = extractDataFromJason().getFirestations();
    }

    //Dependency injection constructor for test
    public FirestationsRepository(List<Firestation> listOfAllFirestations){
        this.listOfAllFirestations = listOfAllFirestations;
    }

    public List<String> findAddressByFirestationNumber(String firestationNumber){
        List<String> adressHandledByFirestation =  new ArrayList<>();

        for(Firestation firestation : listOfAllFirestations){
            if(firestation.getStation().equals(firestationNumber)){
                adressHandledByFirestation.add(firestation.getAddress());
            }
        }
        return adressHandledByFirestation;
    }

    //TODO: unit test
    public String findFirestationNumberByAddress(String address){
        String firestationNumber = null;
        for (Firestation firestation : listOfAllFirestations){
            if (firestation.getAddress().equals(address)){
               firestationNumber = firestation.getStation();
            }
        }
        return firestationNumber;
    }

    //TODO: unit test
    public Firestation addFirestation(Firestation firestationToAdd) {
        listOfAllFirestations.add(firestationToAdd);
        return firestationToAdd;
    }

    //changes the station number of the firestation the imputed address
    //TODO: unit test
    public void updateFirestationByAddress(String addressCoveredByFirestation, String firestationNumberToUpdate) {
        for (Firestation firestation : listOfAllFirestations) {
            if (firestation.getAddress().equals(addressCoveredByFirestation)) {
                firestation.setStation(firestationNumberToUpdate);
                break;
            }
        }
    }

    /*
    cycles through the listOfAllFirestations and stops iterating through the list once on the index of the firestation
    covering that address, remove that firestation from the list and break out of the loop.
     */
    //TODO: unit test
    public void removeFirestationByAddress(String firestationCoveredAddress) {
        for(int i = 0; i < listOfAllFirestations.size(); i++){
            Firestation firestation = listOfAllFirestations.get(i);
            if(firestation.getAddress().equals(firestationCoveredAddress)){
                listOfAllFirestations.remove(i);
                break;
            }
        }
    }
}
