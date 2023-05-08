package com.safetynet.safetynetalerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// The GlobalExceptionHandler class is a central place to handle exceptions across the whole application.
// @ControllerAdvice allows exception handlers to be shared across multiple controller classes.
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MESSAGE = "message";
    private static final String TIMESTAMP = "timestamp";

    /*
    These methods are called when their specific Exception is thrown in the application.
        1-they Create a new HashMap to store the response body.

        2-they Add a 'message' key to the responseBody with the exception message.

        3- they Add a 'timestamp' key to the responseBody with the current date and time.

        4-they Return a ResponseEntity with a http status and the responseBody.
    */

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

    @ExceptionHandler(IncompleteRequestException.class)
    public ResponseEntity<Object> handleIncompleteRequestException(IncompleteRequestException ire){
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(MESSAGE, ire.getMessage());
        responseBody.put(TIMESTAMP, LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

}

