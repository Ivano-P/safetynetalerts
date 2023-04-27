package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;

public interface MedicalRecordService {
    void postNewMedicalRecord(MedicalRecord newMedicalRecordToPost);

    void putMedicalRecord(MedicalRecord updatedMedicalRecord);

    void deleteMedicalRecord(String firstName, String lastName);
}
