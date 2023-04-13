package com.safetynet.safetynetalerts.dao;

import com.google.gson.Gson;
import java.io.FileReader;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.SafetyNet;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExtractObject {
    public static SafetyNet safetyNet = null;
    public static SafetyNet extractDataFromJason(){
        File jsonInput = new File("src/main/resources/data/data.json") ;
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(jsonInput)) {
            safetyNet = gson.fromJson(reader, SafetyNet.class);

            List<Person> persons = safetyNet.getPersons();
            List<Firestation> firestations = safetyNet.getFirestations();
            List<MedicalRecord> medicalRecords = safetyNet.getMedicalRecords();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return safetyNet;
    }
}