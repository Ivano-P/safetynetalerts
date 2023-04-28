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
        log.debug("findAddressByFirestationNumber()");
        List<String> adressHandledByFirestation =  new ArrayList<>();

        for(Firestation firestation : listOfAllFirestations){
            if(firestation.getStation().equals(firestationNumber)){
                adressHandledByFirestation.add(firestation.getAddress());
            }
        }
        log.debug("completed findAddressByFirestationNumber()");
        return adressHandledByFirestation;
    }

    //TODO: unit test
    @Override
    public String findFirestationNumberByAddress(String address){
        log.debug("findFirestationNumberByAddress()");
        String firestationNumber = null;
        for (Firestation firestation : listOfAllFirestations){
            if (firestation.getAddress().equals(address)){
               firestationNumber = firestation.getStation();
            }
        }
        log.debug("Completed findFirestationNumberByAddress()");
        return firestationNumber;
    }

    //TODO: unit test
    @Override
    public Firestation addFirestation(Firestation firestationToAdd) {
        //check if there is any duplicate firestation and throw DuplicateFirestationExeption if there is
        log.debug("addFirestation()");
        for (Firestation firestation : listOfAllFirestations){
            if (firestation.getAddress().equals(firestationToAdd.getAddress()) && firestation.getStation()
                    .equals(firestationToAdd.getStation())){
                log.error(" failed addFirestation() - Duplicate firestation found");
                throw new DuplicateFirestationException();
            }
        }
        listOfAllFirestations.add(firestationToAdd);
        log.info("completed addFirestation()");
        return firestationToAdd;
    }

    //changes the station number of the firestation the imputed address
    //TODO: unit test
    @Override
    public Firestation updateFirestationByAddress(String addressCoveredByFirestation, String firestationNumberToUpdate)  {
        log.debug("updateFirestationByAddress()");
        Firestation updatedFirestation = null;
        for (Firestation firestation : listOfAllFirestations) {
            if (firestation.getAddress().equals(addressCoveredByFirestation)) {
                firestation.setStation(firestationNumberToUpdate);
                log.info("completed updateFirestationByAddress()");
                updatedFirestation = new Firestation(firestation.getAddress(), firestation.getStation());
                break;
            }
        }
        if (updatedFirestation == null){
            log.error("failed updateFirestationByAddress() - no firestation found to update with the specified address");
            throw new FirestationNotFoundException("No firestation found with the specified address");
        }
        return  updatedFirestation;
    }

    /*
    cycles through the listOfAllFirestations and stops iterating through the list once on the index of the firestation
    covering that address, remove that firestation from the list and break out of the loop.
     */
    //TODO: unit test
    @Override
    public Firestation removeFirestationByAddress(String firestationCoveredAddress){
        log.debug("removeFirestationByAddress()");
        Firestation firestationToDelete = null;
        for(int i = 0; i < listOfAllFirestations.size(); i++){
            Firestation firestation = listOfAllFirestations.get(i);
            if(firestation.getAddress().equals(firestationCoveredAddress)){
                firestationToDelete = new Firestation(firestation.getAddress(),firestation.getStation());
                listOfAllFirestations.remove(i);
                log.info("completed removeFirestationByAddress()");
                break;
            }
        }
        if (firestationToDelete == null){
            log.error("failed - removeFirestationByAddress()- no firestation found to delete with the specified address");
            throw new FirestationNotFoundException("No firestation found with the specified address");
        }
        return firestationToDelete;
    }
}
