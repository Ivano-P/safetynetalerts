package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MedicalRecordServiceImpl implements MedicalRecordService{

    private final MedicalRecordRepository medicalRecordRepository;

    @Autowired
    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository){
        this.medicalRecordRepository = medicalRecordRepository;
    }


    @Override
    public void postNewMedicalRecord(MedicalRecord newMedicalRecordToPost) {
        medicalRecordRepository.addNewMedicalRecord(newMedicalRecordToPost);
    }

    @Override
    public void putMedicalRecord(MedicalRecord updatedMedicalRecord) {
        medicalRecordRepository.updateMedicalRecordByFirstAndLastName(updatedMedicalRecord);
    }

    @Override
    public void deleteMedicalRecord(String firstName, String lastName) {
        medicalRecordRepository.removeMedicalRecordByName(firstName, lastName);
    }
}
