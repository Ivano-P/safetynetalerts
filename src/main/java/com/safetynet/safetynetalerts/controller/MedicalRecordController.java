package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService){
        this.medicalRecordService = medicalRecordService;
    }

    //adds MedicalRecord in, body of of post request
    @Operation(summary = "Create new medical record for person")
    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> postNewMedicalRecord(@RequestBody MedicalRecord newMedicalRecordToPost){

        log.debug("postNewMedicalRecord()" + newMedicalRecordToPost);
        MedicalRecord addedMedicalRecord =  medicalRecordService.postNewMedicalRecord(newMedicalRecordToPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedMedicalRecord);

    }

    //updates MedicalRecord with information of MedicalRecord in body
    @Operation(summary = "Update medical record for person")
    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> putMedicalRecord(@RequestBody MedicalRecord updatedMedicalRecord){

        log.debug("putMedicalRecord()" + updatedMedicalRecord);
        MedicalRecord medicalRecord = medicalRecordService.putMedicalRecord(updatedMedicalRecord);
        return ResponseEntity.status(HttpStatus.OK).body(medicalRecord);
    }

    //deletes MedicalRecord using first and last name imputed as parameter
    @Operation(summary = "Delete medical record by person's first and last name")
    @DeleteMapping("/medicalRecord")
    public ResponseEntity<Void> deleteMedicalRecord(@RequestParam ("firstName") String firstName,
                                                      @RequestParam ("lastName") String lastName){

        log.debug("deleteMedicalRecord()" + firstName + " " + lastName);
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
