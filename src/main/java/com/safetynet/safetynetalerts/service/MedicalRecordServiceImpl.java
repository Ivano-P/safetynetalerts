package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Primary
public class MedicalRecordServiceImpl implements MedicalRecordService{

    private final MedicalRecordRepository medicalRecordRepository;

    @Autowired
    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository){
        this.medicalRecordRepository = medicalRecordRepository;
    }


    @Override
    public MedicalRecord postNewMedicalRecord(MedicalRecord newMedicalRecordToPost) {
        log.debug("postNewMedicalRecord()");
        return medicalRecordRepository.addNewMedicalRecord(newMedicalRecordToPost);
    }

    @Override
    public MedicalRecord putMedicalRecord(MedicalRecord updatedMedicalRecord) {
        log.debug("putMedicalRecord()");
        return medicalRecordRepository.updateMedicalRecordByFirstAndLastName(updatedMedicalRecord);
    }

    @Override
    public MedicalRecord deleteMedicalRecord(String firstName, String lastName) {
        log.debug("deleteMedicalRecord()");
        return medicalRecordRepository.removeMedicalRecordByName(firstName, lastName);
    }
}
