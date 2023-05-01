package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.MinorAndFamily;
import com.safetynet.safetynetalerts.dto.PeopleMedicalRecordsAndFirestation;
import com.safetynet.safetynetalerts.dto.PersonInfoAndMedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;
import com.safetynet.safetynetalerts.service.PersonServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping("/childAlert")
    public List<MinorAndFamily> getMinorAndFamilyByAddress(@RequestParam("address") String address) {

        log.debug("getMinorAndFamilyByAddress()" + address);
        return personService.getMinorsAndFamilyByAddress(address);
    }

    @GetMapping("/fire")
    public PeopleMedicalRecordsAndFirestation getPersonMedicalRecordAndFirestation(@RequestParam("address") String address){

        log.debug("getPersonMedicalRecordAndFirestation()" + address);
        return personService.getPeopleMedicalRecordsAndFirestationByAddress(address);
    }

    @GetMapping("/personInfo")
    public List<PersonInfoAndMedicalRecord> getPersonInfoAndMedicalRecords(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName){

        log.debug("getPersonInfoAndMedicalRecords()" + firstName + " " + lastName);
        return personService.getPersonInfoAndMedicalRecordByName(firstName, lastName);
    }

    @GetMapping("/communityEmail")
    public List<String> getEmailsOfPeopleFromCity(@RequestParam("city") String city){

        log.debug("getEmailsOfPeopleFromCity()" + city);
        return personService.getEmailsByCity(city);
    }

    @PostMapping("/person")
    public ResponseEntity<Person> addNewPerson(@RequestBody Person person){

        log.debug("addNewPerson()" + person);
        Person addedPerson = personService.postNewPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedPerson);
    }

    @PutMapping("/person")
    public ResponseEntity<Person> editPerson(@RequestBody Person person){

        log.debug("editPerson()" + person);
        Person updatedPerson = personService.putPerson(person);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
    }


    @DeleteMapping("/person")
    public ResponseEntity<Void> deletePerson(@RequestParam("firstName") String firstName,
                                               @RequestParam("lastName") String lastName){

        log.debug("deletePerson()" + firstName + lastName);
        personService.deletePerson(firstName, lastName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
