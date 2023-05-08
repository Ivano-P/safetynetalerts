package com.safetynet.safetynetalerts.data;

import com.google.gson.Gson;
import com.safetynet.safetynetalerts.model.SafetyNet;

import java.io.*;
import java.nio.charset.StandardCharsets;


public final class ExtractObject{

    private ExtractObject(){}

    public static SafetyNet safetyNet = null;
    public static SafetyNet extractDataFromJason() {
        Gson gson = new Gson();

        try {
            // Use InputStreamReader with FileInputStream and specify the encoding as UTF-8
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("src/main/resources/data/data.json"), StandardCharsets.UTF_8);

            // Use BufferedReader to read the input stream
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            safetyNet = gson.fromJson(bufferedReader, SafetyNet.class);


            // ...

        } catch (IOException e) {
            e.printStackTrace();
        }
        return safetyNet;
    }
}