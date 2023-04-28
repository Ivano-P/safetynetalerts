package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.Houshold;
import com.safetynet.safetynetalerts.dto.PeopleByFirestationNumber;
import com.safetynet.safetynetalerts.dto.PhoneNumbersByFirestation;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.service.FirestationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class FirestationController {
    private final FirestationService firestationService;

    @Autowired
    public FirestationController(FirestationService firestationService){
        this.firestationService = firestationService;
    }

    @GetMapping("/firestation")
    public ResponseEntity<PeopleByFirestationNumber> getPeopleByFirestationNumber(@RequestParam("stationNumber") String stationNumber) {
        log.debug("getPeopleByFirestationNumber() with" + stationNumber);
        PeopleByFirestationNumber result = firestationService
                .getAdultsAndMinorsCoveredByFirestationNumber(stationNumber);

        if ( result != null){
            return ResponseEntity.ok(result);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<PhoneNumbersByFirestation> getPhoneNumbersOfPeopleByFirestation(@RequestParam("firestation") String stationNumber) {
        log.debug("getPhoneNumbersOfPeopleByFirestation() with " + stationNumber);
        PhoneNumbersByFirestation result = firestationService.getPhoneNumbersByFirestationNumber(stationNumber);
        if (result != null){
            return ResponseEntity.ok(result);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<List<Houshold>> getHousholds(@RequestParam("stations") List<String> stationNumbers) {
        log.debug("getHousholds() with" + stationNumbers);
        List<Houshold> result = firestationService.getHousholdsByFirestationNumbers(stationNumbers);
        if (result != null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //to add FireStation in body of post request
    @PostMapping("/firestation")
    public ResponseEntity<Firestation> postFirestation(@RequestBody Firestation firestation) {
        log.debug("postFirestation()");
        Firestation addedFirestation = firestationService.postFireStation(firestation);
        if (addedFirestation != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(addedFirestation);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/firestation")
    public ResponseEntity<Firestation> putFirestationNumber(@RequestBody Firestation firestationToEdit) {
        log.debug("putFirestationNumber()");
        Firestation updatedFirestation = firestationService.putFireStaion(firestationToEdit);
        if (updatedFirestation != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedFirestation);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<Firestation> deleteFirestationCoverageofaddress(@RequestBody Firestation firestationToDelete) {
        log.debug("deleteFirestationCoverageofaddress()");
        Firestation deletedFirestation = firestationService.deleteFirestation(firestationToDelete);
        if (deletedFirestation != null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
