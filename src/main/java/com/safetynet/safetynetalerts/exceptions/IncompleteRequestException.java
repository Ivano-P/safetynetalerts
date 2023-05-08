package com.safetynet.safetynetalerts.exceptions;

public class IncompleteRequestException extends RuntimeException{
    public IncompleteRequestException(){
        super("Incomplete request");
    }
    public IncompleteRequestException(String message){
        super(message);
    }
}
