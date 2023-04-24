package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    //adds MedicalRecord in, body of of post request
    //TODO: unit test
    @PostMapping("/medicalRecord")
    public ResponseEntity<String> postNewMedicalRecord(@RequestBody MedicalRecord newMedicalRecordToPost){
        medicalRecordService.postNewMedicalRecord(newMedicalRecordToPost);

        return ResponseEntity.ok("New medical record was added for " + newMedicalRecordToPost
                .getFirstName() + " " + newMedicalRecordToPost.getLastName());
    }

    //updates MedicalRecord with information of MedicalRecord in body
    //TODO: unit test
    @PutMapping("/medicalRecord")
    public ResponseEntity<String> putMedicalRecord(@RequestBody MedicalRecord updatedMedicalRecord){
        medicalRecordService.putMedicalRecord(updatedMedicalRecord);

        return ResponseEntity.ok(updatedMedicalRecord.getFirstName() + " " + updatedMedicalRecord
                .getLastName() + " medical record updated.");
    }

    //deletes MedicalRecord using first and last name imputed as parameter
    //TODO: unit test
    @DeleteMapping("/medicalRecord")
    public ResponseEntity<String> deleteMedicalRecord(@RequestParam ("firstName") String firstName,
                                                      @RequestParam ("lastName") String lastName){
        medicalRecordService.deleteMedicalRecord(firstName, lastName);

        return ResponseEntity.ok(firstName + " " + lastName + "'s medical record has been deleted");
    }
}
