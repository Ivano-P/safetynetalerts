package com.safetynet.safetynetalerts;

import com.safetynet.safetynetalerts.dao.ExtractObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetynetalertsApplication {

    public static void main(String[] args) {

        SpringApplication.run(SafetynetalertsApplication.class, args);

        ExtractObject.extractDataFromJason();

    }
}
