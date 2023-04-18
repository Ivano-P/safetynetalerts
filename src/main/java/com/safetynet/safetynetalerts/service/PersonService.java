package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.MinorAndFamilyByAddress;
import com.safetynet.safetynetalerts.dto.MinorAndFamily;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.MedicalRecordsRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    //to get dob to calculate age
    @Autowired
    MedicalRecordsRepository medicalRecordsRepository;

    public MinorAndFamilyByAddress getListMinorsAndFamilyByAddress(String address){
        List<MinorAndFamily> minorsAgeAndFamily = new ArrayList<>();
        List<Person> personsAtSameAddress = personRepository.sortPeopleByAddress(address);


        //List with age for each person from list 'personAtSameAddress'
        List<Integer> ages = medicalRecordsRepository.calculateAges( medicalRecordsRepository
                .convertListOfStringsToListOfDateOfBirth(medicalRecordsRepository
                        .checkAgesInMedicalRecords(personsAtSameAddress)));

        // Iterate over all ages and create MinorAndFamily instances for minors
        for (int i = 0; i < ages.size(); i++) {
            if(ages.get(i) < 19){
                List<Person> familyMembersAtSameAddress = new ArrayList<>();
                // adds all other family member except the minor on index i of personsAtSameAddress to new list
                for (int j = 0; j < personsAtSameAddress.size(); j++) {
                    if (j != i) {
                        familyMembersAtSameAddress.add(personsAtSameAddress.get(j));
                    }
                }
                // Create and add a new MinorAndFamily instance to the list
                minorsAgeAndFamily.add(new MinorAndFamily(personsAtSameAddress.get(i), ages.get(i)
                        ,familyMembersAtSameAddress)
                );
            }
        }
        return new MinorAndFamilyByAddress(minorsAgeAndFamily);
    }

}
