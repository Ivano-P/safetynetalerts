package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.MinorAndFamilyByAddress;
import com.safetynet.safetynetalerts.service.FirestationService;
import com.safetynet.safetynetalerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.safetynet.safetynetalerts.dto.PeopleByFirestationNumber;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private FirestationService firestationService;

    @Autowired
    private PersonService personService;

    @GetMapping("/firestation")
    public PeopleByFirestationNumber getPeopleByFirestationNumber(@RequestParam("stationNumber") String stationNumber) {
        return firestationService.getListOfAdultsAndMinorsCoveredByFirestation(stationNumber);
    }

    @GetMapping("/childAlert")
    public MinorAndFamilyByAddress getMinorAndFamilyByAddress(@RequestParam("address") String address){
        return personService.getListMinorsAndFamilyByAddress(address);
    }
}
