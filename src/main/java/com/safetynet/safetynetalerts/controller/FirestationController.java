package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class FirestationController {
    @Autowired
    private FirestationService firestationService;

    @GetMapping("/firestation")
    public ResponseEntity<PeopleByFirestationNumber> getPeopleByFirestationNumber(@RequestParam("stationNumber") String stationNumber) {
        try {
            PeopleByFirestationNumber result = firestationService
                    .getAdultsAndMinorsCoveredByFirestationNumber(stationNumber);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<PhoneNumbersByFirestation> getPhoneNumbersOfPeopleByFirestation(@RequestParam("firestation") String stationNumber) {
        try {
            PhoneNumbersByFirestation result = firestationService.getPhoneNumbersByFirestationNumber(stationNumber);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/flood/stations")
    public List<Houshold> getHousholds(@RequestParam("stations") List<String> stationNumbers) {
        return firestationService.getHousholdsByFirestationNumbers(stationNumbers);
    }

    //to add FireStation in body of post request
    @PostMapping("/firestation")
    public ResponseEntity<Firestation> postFirestation(@RequestBody Firestation firestation) {
        Firestation addedFirestation = firestationService.postFireStation(firestation);

        if (addedFirestation != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(addedFirestation);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/firestation")
    public ResponseEntity<String> putFirestationNumber(@RequestBody Firestation firestationToEdit) {
        firestationService.putFireStaion(firestationToEdit);

        return ResponseEntity.ok("Firestation number has been updated to: " + firestationToEdit.getStation());
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<String> deleteFirestationCoverageofaddress(@RequestBody Firestation firestationToDelete) {
        firestationService.deleteFirestation(firestationToDelete);

        return ResponseEntity.ok(" address removed from coverage of firestation " + firestationToDelete
                .getAddress());
    }

}
