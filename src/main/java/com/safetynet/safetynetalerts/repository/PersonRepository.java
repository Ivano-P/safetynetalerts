package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynetalerts.dao.ExtractObject.extractDataFromJason;

@Repository
public class PersonRepository {
    public ArrayList<Person> sortPeopleByFireStation(String addressCovereByFirestation){
        List<Person> persons = extractDataFromJason().getPersons();
        ArrayList<Person> peopleHandledByFireStation = new ArrayList<>();

        for (Person person: persons){
            if (person.getAddress().equals(addressCovereByFirestation)){
                peopleHandledByFireStation.add(person);
            }
        }

        return peopleHandledByFireStation;
    }

}
