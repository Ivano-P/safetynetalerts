package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.MedicalRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;

    public void postNewMedicalRecord(MedicalRecord newMedicalRecordToPost) {
        medicalRecordsRepository.addNewMedicalRecord(newMedicalRecordToPost);
    }

    public void putMedicalRecord(MedicalRecord updatedMedicalRecord) {
        medicalRecordsRepository.updateMedicalRecord(updatedMedicalRecord);
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        medicalRecordsRepository.deleteMedicalRecord(firstName, lastName);
    }
}
