package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.MinorAndFamily;
import com.safetynet.safetynetalerts.dto.PeopleMedicalRecordsAndFirestation;
import com.safetynet.safetynetalerts.dto.PersonInfoAndMedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;
import com.safetynet.safetynetalerts.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping("/childAlert")
    public List<MinorAndFamily> getMinorAndFamilyByAddress(@RequestParam("address") String address) {
        return personService.getMinorsAndFamilyByAddress(address);
    }

    @GetMapping("/fire")
    public PeopleMedicalRecordsAndFirestation getPersonMedicalRecordAndFirestation(@RequestParam("address") String address){
        return personService.getPeopleMedicalRecordsAndFirestationByAddress(address);
    }

    @GetMapping("/personInfo")
    public List<PersonInfoAndMedicalRecord> getPersonInfoAndMedicalRecords(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName){

        return personService.getPersonInfoAndMedicalRecordByName(firstName, lastName);
    }

    @GetMapping("/communityEmail")
    public List<String> getEmailsOfPeopleFromCity(@RequestParam("city") String city){
        return personService.getEmailsByCity(city);
    }

    //TODO: add unit test
    @PostMapping("/person")
    public ResponseEntity<Person> addNewPerson(@RequestBody Person person){
        Person addedPerson = personService.postNewPerson(person);
        if (addedPerson != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(addedPerson);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //TODO: add unit test
    @PutMapping("/person")
    public ResponseEntity<String> editPerson(@RequestBody Person person){
        personService.putPerson(person);
        return ResponseEntity.ok(person.getFirstName() + person.getLastName() + " information has been updated");
    }

    //TODO: add unit test
    @DeleteMapping("/person")
    public ResponseEntity<String> deletePerson(@RequestParam("firstName") String firstName,
                                               @RequestParam("lastName") String lastName){
        Person deletedPerson = personService.deletePerson(firstName, lastName);

        if (deletedPerson != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(firstName + " " + lastName + " has been deleted");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
