package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.exceptions.DuplicateFirestationException;
import com.safetynet.safetynetalerts.exceptions.FirestationNotFoundException;
import com.safetynet.safetynetalerts.model.Firestation;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynetalerts.dao.ExtractObject.extractDataFromJason;

//make repo!
@Log4j2
@Repository
@Primary
public class FirestationRepositoryImpl implements FirestationRepository{
    List<Firestation> listOfAllFirestations;

    //Dependency injection constructor for productions
    public FirestationRepositoryImpl(){
        this.listOfAllFirestations = extractDataFromJason().getFirestations();
    }

    //Dependency injection constructor for test
    public FirestationRepositoryImpl(List<Firestation> listOfAllFirestations){
        this.listOfAllFirestations = listOfAllFirestations;
    }

    @Override
    public List<String> findAddressByFirestationNumber(String firestationNumber){

        log.debug("findAddressByFirestationNumber() " + firestationNumber);
        List<String> adressHandledByFirestation =  new ArrayList<>();

        for(Firestation firestation : listOfAllFirestations){
            if(firestation.getStation().equals(firestationNumber)){
                adressHandledByFirestation.add(firestation.getAddress());
            }
        }
        if (adressHandledByFirestation.isEmpty()){
            log.error(" Fire station not found exception");
            throw new FirestationNotFoundException("No fire station found with the specified station number");
        }

        log.debug("completed findAddressByFirestationNumber() " + firestationNumber);
        return adressHandledByFirestation;
    }

    //TODO: unit test
    @Override
    public String findFirestationNumberByAddress(String address){
        log.debug("findFirestationNumberByAddress() " + address);
        String firestationNumber = null;
        for (Firestation firestation : listOfAllFirestations){
            if (firestation.getAddress().equals(address)){
               firestationNumber = firestation.getStation();
            }
        }
        if (firestationNumber == null){
            log.error(" Fire station not found exception");
            throw new FirestationNotFoundException("No fire station found covering the specified Address");
        }
        log.debug("Completed findFirestationNumberByAddress() " + address);
        return firestationNumber;
    }

    //TODO: unit test
    @Override
    public Firestation addFirestation(Firestation firestationToAdd) {

        //check if there is any duplicate firestation and throw DuplicateFirestationExeption if there is
        log.debug("addFirestation() " + firestationToAdd);
        for (Firestation firestation : listOfAllFirestations){
            if (firestation.getAddress().equals(firestationToAdd.getAddress()) && firestation.getStation()
                    .equals(firestationToAdd.getStation())){
                log.error("Duplicate Firestation Exception");
                throw new DuplicateFirestationException();
            }
        }
        listOfAllFirestations.add(firestationToAdd);
        log.info("completed addFirestation() " + firestationToAdd);
        return firestationToAdd;
    }

    //changes the station number of the firestation the imputed address
    //TODO: unit test
    @Override
    public Firestation updateFirestationByAddress(String addressCoveredByFirestation, String firestationNumberToUpdate)  {

        log.debug("updateFirestationByAddress() " + addressCoveredByFirestation + " " + firestationNumberToUpdate);
        Firestation updatedFirestation = null;
        for (Firestation firestation : listOfAllFirestations) {
            if (firestation.getAddress().equals(addressCoveredByFirestation)) {
                firestation.setStation(firestationNumberToUpdate);
                updatedFirestation = new Firestation(firestation.getAddress(), firestation.getStation());
                break;
            }
        }
        if (updatedFirestation == null){
            log.error("Firestation Not Found Exception");
            throw new FirestationNotFoundException("No firestation found with the specified address");
        }
        log.info("completed updateFirestationByAddress() " + addressCoveredByFirestation + " " + firestationNumberToUpdate);
        return  updatedFirestation;
    }

    /*
    cycles through the listOfAllFirestations and stops iterating through the list once on the index of the firestation
    covering that address, remove that firestation from the list and break out of the loop.
     */
    //TODO: unit test
    @Override
    public Firestation removeFirestationByAddress(String firestationCoveredAddress){

        log.debug("removeFirestationByAddress() " + firestationCoveredAddress);
        Firestation firestationToDelete = null;
        for(int i = 0; i < listOfAllFirestations.size(); i++){
            Firestation firestation = listOfAllFirestations.get(i);
            if(firestation.getAddress().equals(firestationCoveredAddress)){
                firestationToDelete = new Firestation(firestation.getAddress(),firestation.getStation());
                listOfAllFirestations.remove(i);
                break;
            }
        }
        if (firestationToDelete == null){
            log.error("Firestation Not Found Exception ");
            throw new FirestationNotFoundException("No firestation found with the specified address");
        }
        log.info("completed removeFirestationByAddress() " + firestationCoveredAddress);
        return firestationToDelete;
    }
}
