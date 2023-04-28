package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
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
    //TODO: unit test
    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> postNewMedicalRecord(@RequestBody MedicalRecord newMedicalRecordToPost){
        log.debug("postNewMedicalRecord()");
        MedicalRecord addedMedicalRecord =  medicalRecordService.postNewMedicalRecord(newMedicalRecordToPost);

        if( addedMedicalRecord != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(addedMedicalRecord);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    //updates MedicalRecord with information of MedicalRecord in body
    //TODO: unit test
    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> putMedicalRecord(@RequestBody MedicalRecord updatedMedicalRecord){
        log.debug("putMedicalRecord()");
        MedicalRecord medicalRecord = medicalRecordService.putMedicalRecord(updatedMedicalRecord);

        if( medicalRecord != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecord);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //deletes MedicalRecord using first and last name imputed as parameter
    //TODO: unit test
    @DeleteMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(@RequestParam ("firstName") String firstName,
                                                      @RequestParam ("lastName") String lastName){
        log.debug("deleteMedicalRecord()");
        MedicalRecord deletedMedicalRecord = medicalRecordService.deleteMedicalRecord(firstName, lastName);

        if( deletedMedicalRecord != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(deletedMedicalRecord);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
