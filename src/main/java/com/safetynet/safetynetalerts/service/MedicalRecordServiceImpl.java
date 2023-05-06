package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

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

        //check if person with that name exist before adding persons medical record.
         personRepository.findPeopleByName(newMedicalRecordToPost.getFirstName(), newMedicalRecordToPost.getLastName());

        return medicalRecordRepository.addNewMedicalRecord(newMedicalRecordToPost);
    }

    @Override
    public MedicalRecord putMedicalRecord(MedicalRecord updatedMedicalRecord) {

        log.debug("putMedicalRecord()" + updatedMedicalRecord);
        return medicalRecordRepository.updateMedicalRecordByFirstAndLastName(updatedMedicalRecord);
    }

    @Override
    public MedicalRecord deleteMedicalRecord(String firstName, String lastName) {

        log.debug("deleteMedicalRecord()" + firstName + " " + lastName);
        return medicalRecordRepository.removeMedicalRecordByName(firstName, lastName);
    }
}
