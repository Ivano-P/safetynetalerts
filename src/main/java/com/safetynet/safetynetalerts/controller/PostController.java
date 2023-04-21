package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
    
    @Autowired
    private PersonService personService;

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
}
