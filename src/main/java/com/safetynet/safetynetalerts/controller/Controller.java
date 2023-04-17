package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.safetynet.safetynetalerts.dto.PeopleByFirestationNumber;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @GetMapping("/firestation")
    public PeopleByFirestationNumber getPeopleByFirestationNumber(@RequestParam("stationNumber") String stationNumber) {
        return firestationService.getListOfAdultsAndMinorsCoveredByFirestation(stationNumber);
    }
}
