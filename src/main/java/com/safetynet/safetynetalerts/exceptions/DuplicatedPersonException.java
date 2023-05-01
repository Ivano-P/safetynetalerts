package com.safetynet.safetynetalerts.exceptions;

public class DuplicatedPersonException extends RuntimeException{

    public DuplicatedPersonException() {
        super("Duplicate person found");
    }
    public DuplicatedPersonException(String message){
        super(message);
    }
}
