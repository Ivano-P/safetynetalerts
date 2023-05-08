package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.exceptions.IncompleteRequestException;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Primary
public class MedicalRecordServiceImpl implements MedicalRecordService{

    private final MedicalRecordRepository medicalRecordRepository;
    private final PersonRepository personRepository;

    @Autowired
    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository, PersonRepository personRepository){
        this.medicalRecordRepository = medicalRecordRepository;
        this.personRepository =  personRepository;
    }

    @Override
    public MedicalRecord postNewMedicalRecord(MedicalRecord newMedicalRecordToPost) {

        log.debug("postNewMedicalRecord()" + newMedicalRecordToPost);
        //check that request contains at the least first name, last name and date of birth
        if(newMedicalRecordToPost.getFirstName() == null || newMedicalRecordToPost.getFirstName()
                .isEmpty() || newMedicalRecordToPost.getLastName() == null || newMedicalRecordToPost.getLastName()
                .isEmpty() || newMedicalRecordToPost.getBirthdate() == null || newMedicalRecordToPost.getBirthdate()
                .isEmpty()){
            log.error("Incomplete Request Exception");
            throw new IncompleteRequestException("Incomplete Request, request must contain 'first name', 'last name " +
                    "and 'date of birth");
        }

        //check if person with that name exist before adding persons medical record.
         personRepository.findPeopleByName(newMedicalRecordToPost.getFirstName(), newMedicalRecordToPost.getLastName());

        return medicalRecordRepository.addNewMedicalRecord(newMedicalRecordToPost);
    }

    @Override
    public MedicalRecord putMedicalRecord(MedicalRecord updatedMedicalRecord) {

        log.debug("putMedicalRecord()" + updatedMedicalRecord);
        //check that request contains at the least first name, last name and  date of birth
        if(updatedMedicalRecord.getFirstName() == null || updatedMedicalRecord.getFirstName()
                .isEmpty() || updatedMedicalRecord.getLastName() == null || updatedMedicalRecord.getLastName()
                .isEmpty() || updatedMedicalRecord.getBirthdate() == null || updatedMedicalRecord.getBirthdate()
                .isEmpty()){
            log.error("Incomplete Request Exception");
            throw new IncompleteRequestException("Incomplete Request, request must contain 'first name', 'last name " +
                    "and 'date of birth");
        }
        return medicalRecordRepository.updateMedicalRecordByFirstAndLastName(updatedMedicalRecord);
    }

    @Override
    public MedicalRecord deleteMedicalRecord(String firstName, String lastName) {

        log.debug("deleteMedicalRecord()" + firstName + " " + lastName);
        return medicalRecordRepository.removeMedicalRecordByName(firstName, lastName);
    }
}
