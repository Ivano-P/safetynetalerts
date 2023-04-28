package com.safetynet.safetynetalerts.exceptions;

public class MedicalRecordNotFountException extends RuntimeException{
    public MedicalRecordNotFountException() {
        super("Medical Record not found");
    }

    public MedicalRecordNotFountException(String message) {
        super(message);
    }
}
