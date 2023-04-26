package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Person;

import java.util.List;

public interface PersonRepository {

    List<Person> findPeopleByFireStationAddress(String addressCovereByFirestation);

    List<Person> findPeopleByAddress(String address);

    List<Person> findPeopleByName(String firstName, String lastName);

    List<Person> findPeopleByCity(String city);

    void addPerson(Person personToAdd);

    void updatePerson(Person personToEdit);

    Person removePerson(String firstName, String lastName);
}
