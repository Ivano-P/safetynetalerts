package com.safetynet.safetynetalerts.exceptions;

public class DuplicateFirestationException extends RuntimeException{
    public DuplicateFirestationException() {
        super("Duplicate firestation found");
    }
    public DuplicateFirestationException(String message) {
        super(message);
    }
}
