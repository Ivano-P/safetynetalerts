package com.safetynet.safetynetalerts.dao;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.SafetyNet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ExtractObject {

    public void extractDataFromJason(){
        File jsonInput = new File("src/main/resources/data/data.json") ;
        try(InputStream inputStream = new FileInputStream(jsonInput)) {
            JsonIterator iterator = JsonIterator.parse(inputStream, 1024);
            Any dataAny = iterator.readAny();
            SafetyNet safetyNet = dataAny.as(SafetyNet.class);

            List<Person> persons = safetyNet.getPersons();
            List<Firestation> firestations = safetyNet.getFirestations();
            List<MedicalRecord> medicalRecords = safetyNet.getMedicalrecords();

            //testing that object is populated correctly
            System.out.println(persons);
            System.out.println(firestations);
            System.out.println(medicalRecords);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printLists(){

    }


}
