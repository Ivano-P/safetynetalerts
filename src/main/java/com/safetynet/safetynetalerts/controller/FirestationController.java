package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FirestationController {
    @Autowired
    private FirestationService firestationService;

    @GetMapping("/firestation")
    public PeopleByFirestationNumber getPeopleByFirestationNumber(@RequestParam("stationNumber") String stationNumber) {
        return firestationService.getListOfAdultsAndMinorsCoveredByFirestation(stationNumber);
    }
    @GetMapping("/phoneAlert")
    public PhoneNumbersByFirestation getPhoneNumbersOfPeopleByFirestation(@RequestParam("firestation") String stationNumber) {
        return firestationService.getListOfPhoneNumbersByFirestation(stationNumber);
    }

    @GetMapping("/flood/stations")
    public List<Houshold> getHousholds(@RequestParam("stations") List<String> stationNumbers) {
        return firestationService.getListOfHousholdsByListOfFirestationNumber(stationNumbers);
    }

    //to add FireStation in body of post request
    //TODO: unit test
    @PostMapping("/firestation")
    public ResponseEntity<Firestation> postFirestation(@RequestBody Firestation firestation){
        Firestation addedFirestation = firestationService.postFireStation(firestation);

        if (addedFirestation != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(addedFirestation);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /*
    //to add FireStation with parameters in post request
    @PostMapping("/firestation")
    public ResponseEntity<Firestation> postFirestationAndAddressCoverage(@RequestParam String stationNumber
                                                                         , @RequestParam String addressToCover){

        Firestation addedFirestation = firestationService
                .postFiresationAndAddressCoverage(stationNumber, addressToCover);

        if (addedFirestation != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(addedFirestation);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    */

    //TODO: unit test
    @PutMapping("/firestation")
    public ResponseEntity<String> putFirestationNumber(@RequestBody Firestation firestationToEdit){
        firestationService.putFireStaion(firestationToEdit);

        return ResponseEntity.ok("Firestation number has been updated to: " + firestationToEdit.getStation());
    }

    //TODO: unit test
    @DeleteMapping("/firestation")
    public ResponseEntity<String> deleteFirestationCoverageofaddress(@RequestBody Firestation firestationToDelete){
        firestationService.deleteFirestation(firestationToDelete);

        return ResponseEntity.ok(" address removed from coverage of firestation " + firestationToDelete
                .getAddress() );
    }

}
