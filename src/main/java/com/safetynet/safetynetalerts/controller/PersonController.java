package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.MinorAndFamily;
import com.safetynet.safetynetalerts.dto.PeopleMedicalRecordsAndFirestation;
import com.safetynet.safetynetalerts.dto.PersonInfoAndMedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    
    @Autowired
    private PersonService personService;

    @GetMapping("/childAlert")
    public List<MinorAndFamily> getMinorAndFamilyByAddress(@RequestParam("address") String address) {
        return personService.getListMinorsAndFamilyByAddress(address);
    }

    @GetMapping("/fire")
    public PeopleMedicalRecordsAndFirestation getPersonMedicalRecordAndFirestation(@RequestParam("address") String address){
        return personService.getListOfPeopleMedicalRecordsAndFirestation(address);
    }

    @GetMapping("/personInfo")
    public List<PersonInfoAndMedicalRecord> getPersonInfoAndMedicalRecords(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName){

        return personService.getPersonInfoAndMedicalRecord(firstName, lastName);
    }

    @GetMapping("/communityEmail")
    public List<String> getEmailsOfPeopleFromCity(@RequestParam("city") String city){
        return personService.getListOfEmails(city);
    }

    //TODO: add unit test
    @PostMapping("/person")
    public ResponseEntity<Person> addNewPerson(@RequestBody Person person){
        Person addedPerson = personService.addNewPerson(person);
        if (addedPerson != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(addedPerson);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //TODO: add unit test
    @PutMapping("/person")
    public ResponseEntity<Person> editPerson(@RequestBody Person person){
        Person editPerson = personService.editPerson(person);

        if (editPerson != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(editPerson);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
