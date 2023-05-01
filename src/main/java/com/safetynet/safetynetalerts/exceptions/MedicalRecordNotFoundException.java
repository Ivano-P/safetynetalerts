package com.safetynet.safetynetalerts.exceptions;

public class MedicalRecordNotFoundException extends RuntimeException{
    public MedicalRecordNotFoundException() {
        super("Medical Record not found");
    }

    public MedicalRecordNotFoundException(String message) {
        super(message);
    }
}
