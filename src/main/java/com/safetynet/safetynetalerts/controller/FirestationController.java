package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.Houshold;
import com.safetynet.safetynetalerts.dto.PeopleByFirestationNumber;
import com.safetynet.safetynetalerts.dto.PhoneNumbersByFirestation;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.service.FirestationService;
import io.swagger.v3.oas.annotations.Operation;
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
    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @Operation(summary = "Get a list of people covered by a fire station and a count of the number of adults and minors, using the station number ")
    @GetMapping("/firestation")
    public ResponseEntity<PeopleByFirestationNumber> getPeopleByFirestationNumber(@RequestParam("stationNumber") String stationNumber) {

        log.debug("getPeopleByFirestationNumber() " + stationNumber);
        PeopleByFirestationNumber result = firestationService
                .getAdultsAndMinorsCoveredByFirestationNumber(stationNumber);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get a list of each person's phone number by their fire station number")
    @GetMapping("/phoneAlert")
    public ResponseEntity<PhoneNumbersByFirestation> getPhoneNumbersOfPeopleByFirestation(@RequestParam("firestation") String stationNumber) {

        log.debug("getPhoneNumbersOfPeopleByFirestation() " + stationNumber);
        PhoneNumbersByFirestation result = firestationService.getPhoneNumbersByFirestationNumber(stationNumber);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Get a list of households by fire station number. For each household, the people and their medical records are specified")
    @GetMapping("/flood/stations")
    public ResponseEntity<List<Houshold>> getHousholds(@RequestParam("stations") List<String> stationNumbers) {

        log.debug("getHousholds() " + stationNumbers);
        List<Houshold> result = firestationService.getHousholdsByFirestationNumbers(stationNumbers);
        return ResponseEntity.ok(result);
    }

    //to add FireStation in body of post request
    @Operation(summary = "Create new fire station and associate it with an address")
    @PostMapping("/firestation")
    public ResponseEntity<Firestation> postFirestation(@RequestBody Firestation firestation) {

        log.debug("postFirestation() " + firestation);
        Firestation addedFirestation = firestationService.postFireStation(firestation);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedFirestation);
    }

    @Operation(summary = "Update fire station's number by associated address")
    @PutMapping("/firestation")
    public ResponseEntity<Firestation> putFirestationNumber(@RequestBody Firestation firestationToEdit) {

        log.debug("putFirestationNumber() " + firestationToEdit);
        Firestation updatedFirestation = firestationService.putFireStaion(firestationToEdit);
        return ResponseEntity.status(HttpStatus.OK).body(updatedFirestation);
    }

    @Operation(summary = "Delete fire station and its association with an address")
    @DeleteMapping("/firestation")
    public ResponseEntity<Void> deleteFirestationCoverageofaddress(@RequestBody Firestation firestationToDelete) {

        log.debug("deleteFirestationCoverageofaddress() " + firestationToDelete);
        firestationService.deleteFirestation(firestationToDelete);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
