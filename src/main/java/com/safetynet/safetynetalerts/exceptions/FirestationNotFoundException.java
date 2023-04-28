package com.safetynet.safetynetalerts.exceptions;


public class FirestationNotFoundException extends RuntimeException {
    public FirestationNotFoundException() {
        super("Fire station not found");
    }

    public FirestationNotFoundException(String message) {
        super(message);
    }
}
