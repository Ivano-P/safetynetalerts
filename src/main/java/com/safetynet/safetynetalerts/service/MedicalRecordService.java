package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;

public interface MedicalRecordService {
    MedicalRecord postNewMedicalRecord(MedicalRecord newMedicalRecordToPost);

    MedicalRecord putMedicalRecord(MedicalRecord updatedMedicalRecord);

    MedicalRecord deleteMedicalRecord(String firstName, String lastName);
}
