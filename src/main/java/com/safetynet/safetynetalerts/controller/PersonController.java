package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.MinorAndFamily;
import com.safetynet.safetynetalerts.dto.PeopleMedicalRecordsAndFirestation;
import com.safetynet.safetynetalerts.dto.PersonInfoAndMedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;
import com.safetynet.safetynetalerts.service.PersonServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Person ", description = "Manages data related to people")
@Log4j2
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Operation(summary = "Get a list of children by address. The list will also specify other family members for each child",
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found",
                            headers = {@Header(name = "No person found at the specified address")})})
    @GetMapping("/childAlert")
    public List<MinorAndFamily> getMinorAndFamilyByAddress(
            @Parameter(description = "home address without zip and city") @RequestParam("address") String address) {

        log.debug("getMinorAndFamilyByAddress()" + address);
        return personService.getMinorsAndFamilyByAddress(address);
    }

    @Operation(summary = "Get a list of people, their medical records, and the fire station that covers them by their address.",
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            headers = {@Header(name = "No person found at the specified address")})})
    @GetMapping("/fire")
    public PeopleMedicalRecordsAndFirestation getPersonMedicalRecordAndFirestation(
            @Parameter(description = "home address without zip and city") @RequestParam("address") String address) {

        log.debug("getPersonMedicalRecordAndFirestation()" + address);
        return personService.getPeopleMedicalRecordsAndFirestationByAddress(address);
    }

    @Operation(summary = "Get a person and their medical information by their first and last name.",
            responses = {@ApiResponse(responseCode = "200", description = "ok"),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            headers = {@Header(name = "Person not found")})})
    @GetMapping("/personInfo")
    public List<PersonInfoAndMedicalRecord> getPersonInfoAndMedicalRecords(
            @Parameter(description = "First name of persons") @RequestParam("firstName") String firstName,
            @Parameter(description = "Last name of persons") @RequestParam("lastName") String lastName) {

        log.debug("getPersonInfoAndMedicalRecords()" + firstName + " " + lastName);
        return personService.getPersonInfoAndMedicalRecordByName(firstName, lastName);
    }

    @Operation(summary = "Get email addresses of people by their city.",
            responses = {@ApiResponse(responseCode = "200", description = "OK")})
    @GetMapping("/communityEmail")
    public List<String> getEmailsOfPeopleFromCity(
            @Parameter(description = "City for residents Email") @RequestParam("city") String city) {

        log.debug("getEmailsOfPeopleFromCity()" + city);
        return personService.getEmailsByCity(city);
    }

    @Operation(summary = "Creat new person",
            responses = {@ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            headers = {@Header(name = "Incomplete request",
                                    description = "Request must contain all fields")})})
    @PostMapping("/person")
    public ResponseEntity<Person> addNewPerson(@RequestBody Person person) {

        log.debug("addNewPerson()" + person);
        Person addedPerson = personService.postNewPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedPerson);
    }

    @Operation(summary = "Update person using first and last name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", headers = {@Header(name = "Good request")},
                            content = @Content(mediaType = "application/json", schema = @Schema(
                                    example = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"address\":\"1509 Culver " +
                                            "St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"123-123-" +
                                            "123\",\"email\":\"john.doe@example.com\"}"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            headers = {@Header(name = "Person not found")}, content = @Content(mediaType = "application/json", schema = @Schema(
                            example = "{\"firstName\":\"Johnathan\",\"lastName\":\"Doe\",\"address\":\"1509 Culver " +
                                    "St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"123-123-" +
                                    "123\",\"email\":\"john.doe@example.com\"}"))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            headers = {@Header(name = "Incomplete request",
                                    description = "Request must contain 'first name', 'last name")},
                            content = @Content(mediaType = "application/json", schema = @Schema(
                                    example = "{\"firstName\":\"\",\"lastName\":\"Doe\",\"address\":\"1509 Culver " +
                                            "St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"123-123-" +
                                            "123\",\"email\":\"john.doe@example.com\"}"))
                    )
            })
    @PutMapping("/person")
    public ResponseEntity<Person> editPerson(@RequestBody Person person) {

        log.debug("editPerson()" + person);
        Person updatedPerson = personService.putPerson(person);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
    }

    @Operation(summary = "Delete person by first and last name",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            headers = {@Header(name = "No Person found with specified first and last name")})})
    @DeleteMapping("/person")
    public ResponseEntity<Void> deletePerson(
            @Parameter(description = "First name of person to delete") @RequestParam("firstName") String firstName,
            @Parameter(description = "Last name of persons to delete") @RequestParam("lastName") String lastName) {

        log.debug("deletePerson()" + firstName + lastName);
        personService.deletePerson(firstName, lastName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
