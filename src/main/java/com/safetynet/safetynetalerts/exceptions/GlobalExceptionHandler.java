package com.safetynet.safetynetalerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MESSAGE = "message";
    private static final String TIMESTAMP = "timestamp";

    @ExceptionHandler(DuplicateFirestationException.class)
    public ResponseEntity<Object> handleDuplicateFirestationException(DuplicateFirestationException dfe) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(MESSAGE, dfe.getMessage());
        responseBody.put(TIMESTAMP, LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    @ExceptionHandler(FirestationNotFoundException.class)
    public ResponseEntity<Object> handleFirestationNotFoundException(FirestationNotFoundException fnfe){
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(MESSAGE, fnfe.getMessage());
        responseBody.put(TIMESTAMP, LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    @ExceptionHandler(DuplicateMedicalRecordException.class)
    public ResponseEntity<Object> handleDuplicateMedicalRecordException(DuplicateMedicalRecordException dmre){
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(MESSAGE, dmre.getMessage());
        responseBody.put(TIMESTAMP, LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    @ExceptionHandler(MedicalRecordNotFoundException.class)
    public ResponseEntity<Object> handleMedicalRecordNotFountException(MedicalRecordNotFoundException mrnfe){
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(MESSAGE, mrnfe.getMessage());
        responseBody.put(TIMESTAMP, LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    @ExceptionHandler(DuplicatedPersonException.class)
    public ResponseEntity<Object> handleDuplicatePersonException(DuplicatedPersonException dpe){
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(MESSAGE, dpe.getMessage());
        responseBody.put(TIMESTAMP, LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<Object> handlePersonNotFoundException(PersonNotFoundException pnfe){
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(MESSAGE, pnfe.getMessage());
        responseBody.put(TIMESTAMP, LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

}

