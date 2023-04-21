package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.service.FirestationService;
import com.safetynet.safetynetalerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<MinorAndFamily> getMinorAndFamilyByAddress(@RequestParam("address") String address) {
        return personService.getListMinorsAndFamilyByAddress(address);
    }

    @GetMapping("/phoneAlert")
    public PhoneNumbersByFirestation getPhoneNumbersOfPeopleByFirestation(@RequestParam("firestation") String stationNumber) {
        return firestationService.getListOfPhoneNumbersByFirestation(stationNumber);
    }

    @GetMapping("/fire")
    public PeopleMedicalRecordsAndFirestation getPersonMedicalRecordAndFirestation(@RequestParam("address") String address){
        return personService.getListOfPeopleMedicalRecordsAndFirestation(address);
    }

    @GetMapping("/flood/stations")
    public List<Houshold> getHousholds(@RequestParam("stations") List<String> stationNumbers) {
        return firestationService.getListOfHousholdsByListOfFirestationNumber(stationNumbers);
    }

    @GetMapping("/personInfo")
    public List<PersonInfoAndMedicalRecord> getPersonInfoAndMedicalRecords(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName){

        return personService.getPersonInfoAndMedicalRecord(firstName, lastName);
    }

    //TODO: unit test
    @GetMapping("/communityEmail")
    public List<String> getEmailsOfPeopleFromCity(@RequestParam("city") String city){
        return personService.getListOfEmails(city);
    }

}
