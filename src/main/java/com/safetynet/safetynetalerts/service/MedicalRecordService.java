package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.FirestationRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepositoryImpl;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    @Autowired
    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository){
        this.medicalRecordRepository = medicalRecordRepository;
    }


    public void postNewMedicalRecord(MedicalRecord newMedicalRecordToPost) {
        medicalRecordRepository.addNewMedicalRecord(newMedicalRecordToPost);
    }

    public void putMedicalRecord(MedicalRecord updatedMedicalRecord) {
        medicalRecordRepository.updateMedicalRecordByFirstAndLastName(updatedMedicalRecord);
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        medicalRecordRepository.removeMedicalRecordByName(firstName, lastName);
    }
}
