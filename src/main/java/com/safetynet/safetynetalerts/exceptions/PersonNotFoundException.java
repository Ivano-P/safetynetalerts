package com.safetynet.safetynetalerts.exceptions;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException() {
        super("Person not found");
    }

    public PersonNotFoundException(String message) {
        super(message);
    }
}
