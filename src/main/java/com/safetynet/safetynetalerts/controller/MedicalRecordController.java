package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Medical records ", description = "Manages data related to people medical records")
@Log4j2
@RestController
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService){
        this.medicalRecordService = medicalRecordService;
    }

    //adds MedicalRecord in, body of of post request
    @Operation(summary = "Create new medical record for person",
            responses = { @ApiResponse(responseCode = "201", description = "Created"),
                             @ApiResponse(responseCode = "404", description = "Not Found",
                                     headers = {@Header(name = "Person not found" ,
                                             description = "Medical record cannot be created for person that is not in db") }),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            headers = {@Header(name = "Incomplete request" ,
                                    description = "request must contain 'first name', 'last name and 'date of birth'")})})
    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> postNewMedicalRecord(@RequestBody MedicalRecord newMedicalRecordToPost){

        log.debug("postNewMedicalRecord()" + newMedicalRecordToPost);
        MedicalRecord addedMedicalRecord =  medicalRecordService.postNewMedicalRecord(newMedicalRecordToPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedMedicalRecord);

    }

    //updates MedicalRecord with information of MedicalRecord in body
    @Operation(summary = "Update medical record for person",
            responses = { @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found" ,
                            headers = {@Header(name = "Medical Record not found") }),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            headers = {@Header(name = "Incomplete request" ,
                                    description = "request must contain 'first name', 'last name and 'date of birth'")})})
    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> putMedicalRecord(@RequestBody MedicalRecord updatedMedicalRecord){

        log.debug("putMedicalRecord()" + updatedMedicalRecord);
        MedicalRecord medicalRecord = medicalRecordService.putMedicalRecord(updatedMedicalRecord);
        return ResponseEntity.status(HttpStatus.OK).body(medicalRecord);
    }

    //deletes MedicalRecord using first and last name imputed as parameter
    @Operation(summary = "Delete medical record by person's first and last name",
            responses = { @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found",
                            headers = {@Header(name = "No Medical record found with specified first and last name") })})
    @DeleteMapping("/medicalRecord")
    public ResponseEntity<Void> deleteMedicalRecord(
            @Parameter(description = "Person to delete first name") @RequestParam ("firstName") String firstName,
            @Parameter(description = "Person to delete last name") @RequestParam ("lastName") String lastName){

        log.debug("deleteMedicalRecord()" + firstName + " " + lastName);
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
