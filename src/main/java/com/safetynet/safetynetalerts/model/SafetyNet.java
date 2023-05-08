package com.safetynet.safetynetalerts.model;

import lombok.Data;

import java.util.List;

@Data
public class SafetyNet{
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalrecords;


    public SafetyNet(List<Person> persons, List<Firestation> firestations,
                     List<MedicalRecord> medicalRecords) {
        this.persons = persons;
        this.firestations = firestations;
        this.medicalrecords = medicalRecords;
    }

}
