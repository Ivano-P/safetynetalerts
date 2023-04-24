package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
