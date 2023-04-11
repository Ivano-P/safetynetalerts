package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Firestation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynetalerts.dao.ExtractObject.extractDataFromJason;

//make repo!
@Repository
public class FirestationsRepository {
    public List<String> sortAdressRelatedToFirestation(String idFirestation){

        List<Firestation> firestations = extractDataFromJason().getFirestations();
        List<String> adressHandledByFirestation =  new ArrayList<>();

        for(Firestation firestation : firestations){
            if(firestation.getStation().equals(idFirestation)){
                adressHandledByFirestation.add(firestation.getAddress());
            }
        }
        return adressHandledByFirestation;
    }
}
