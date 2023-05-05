package com.safetynet.safetynetalerts.dao;

import com.google.gson.Gson;
import com.safetynet.safetynetalerts.model.SafetyNet;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public final class ExtractObject {

    private ExtractObject(){}

    public static SafetyNet safetyNet = null;
    public static SafetyNet extractDataFromJason(){
        File jsonInput = new File("src/main/resources/data/data.json") ;
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(jsonInput)) {
            safetyNet = gson.fromJson(reader, SafetyNet.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return safetyNet;
    }
}