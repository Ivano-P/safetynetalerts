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

    public List<String> sortAdressRelatedToFirestation(String idFirestation){
        List<String> adressHandledByFirestation =  new ArrayList<>();

        for(Firestation firestation : listOfAllFirestations){
            if(firestation.getStation().equals(idFirestation)){
                adressHandledByFirestation.add(firestation.getAddress());
            }
        }
        return adressHandledByFirestation;
    }
}
