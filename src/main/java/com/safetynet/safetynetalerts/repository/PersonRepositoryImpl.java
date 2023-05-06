package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.exceptions.DuplicatedPersonException;
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.model.Person;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynetalerts.dao.ExtractObject.extractDataFromJason;
@Log4j2
@Repository
@Primary
public class PersonRepositoryImpl implements PersonRepository{

    List<Person> listOfAllPersons;

    //constructor for productions
    public PersonRepositoryImpl() {

        this.listOfAllPersons = extractDataFromJason().getPersons();
    }

    // Dependency injection constructor for testing
    PersonRepositoryImpl(List<Person> listOfAllPersons) {

        this.listOfAllPersons = listOfAllPersons;
    }

    @Override
    public List<Person> findPeopleByFireStationAddress(String addressCovereByFirestation) {

        log.debug("findPeopleByFireStationAddress() " + addressCovereByFirestation);
        ArrayList<Person> peopleHandledByFireStation = new ArrayList<>();
        for (Person person : listOfAllPersons) {
            if (person.getAddress().equals(addressCovereByFirestation)) {
                peopleHandledByFireStation.add(person);
            }
        }
        log.debug("completed findPeopleByFireStationAddress() " + addressCovereByFirestation);
        return peopleHandledByFireStation;
    }

    @Override
    public List<Person> findPeopleByAddress(String address) {

        log.debug("findPeopleByAddress() " + address);
        ArrayList<Person> personsAtSameAddress = new ArrayList<>();
        for (Person person : listOfAllPersons) {
            if (person.getAddress().equals(address)) {
                personsAtSameAddress.add(person);
            }
        }
        if (personsAtSameAddress.isEmpty()){
            log.error("Person not found exception");
            throw new PersonNotFoundException("No person found at the specified address");
        }
        log.debug("completed findPeopleByAddress() " + address);
        return personsAtSameAddress;
    }

    @Override
    public List<Person> findPeopleByName(String firstName, String lastName) {

        log.debug("findPeopleByName() " + firstName + " " + lastName);
        ArrayList<Person> personsWithThatName = new ArrayList<>();
        for (Person person : listOfAllPersons) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                personsWithThatName.add(person);
            }
        }
        if(personsWithThatName.isEmpty()){
            log.error("person not found exception");
            throw new PersonNotFoundException();
        }
        log.debug("completed findPeopleByName() " + firstName + " " + lastName);
        return personsWithThatName;
    }


    @Override
    //returns a list of all persons that lives a city
    public List<Person> findPeopleByCity(String city) {

        log.debug("findPeopleByCity() " + city);
        List<Person> peopleFromCity = new ArrayList<>();
        for (Person person : listOfAllPersons) {
            if (person.getCity().equals(city)) {
                peopleFromCity.add(person);
            }
        }
        log.debug("completed findPeopleByCity() " + city);
        return peopleFromCity;
    }

    @Override
    public Person addPerson(Person personToAdd) {

        log.debug("addPerson() " + personToAdd);
        for(Person person : listOfAllPersons){
            if (person.getFirstName().equals(personToAdd.getFirstName()) && person.getLastName().equals(personToAdd
                    .getLastName())){
                log.error("Duplicate person exception");
                throw new DuplicatedPersonException();
            }
        }
        listOfAllPersons.add(personToAdd);
        log.info("completed addPerson() " + personToAdd);
        return personToAdd;
    }

    //edits Person with same first and last name as person in parsed in as argument.
    @Override
    public Person updatePerson(Person personToEdit) {

        log.debug("updatePerson() " + personToEdit);
        boolean foundPersonToUpdate = false;
        for (Person person : listOfAllPersons) {
            if (personToEdit.getFirstName().equals(person.getFirstName()) && personToEdit.getLastName().equals(person
                    .getLastName())) {
                foundPersonToUpdate = true;
                person.setAddress(personToEdit.getAddress());
                person.setCity(personToEdit.getCity());
                person.setZip(personToEdit.getZip());
                person.setPhone(personToEdit.getPhone());
                person.setEmail(personToEdit.getEmail());
            }
        }
        if (!foundPersonToUpdate){
            log.error("Person not found Exception");
            throw new PersonNotFoundException();
        }
        log.info("completed updatePerson() " + personToEdit);
        return personToEdit;
    }

    /*
    cycling through the listOfAllPeople and stops iterating through the list once the person with the same first and
    last name has been removed
    */
    @Override
    public Person removePerson(String firstName, String lastName) {

        log.debug("removePerson() " + firstName +  " " + lastName);
        Person deletedPerson = null;
        for(int i = 0; i < listOfAllPersons.size(); i++){
            Person person = listOfAllPersons.get(i);
            if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)){

                //use this to return the information of deleted person to body of delete request
                deletedPerson = new Person(person.getFirstName(), person.getLastName(), person.getAddress()
                        , person.getCity(), person.getZip(), person.getPhone(), person.getEmail()
                );

                listOfAllPersons.remove(i);
                break;
            }
        }
        if(deletedPerson == null){
            log.error("Person not found Exception");
            throw new PersonNotFoundException("No person found with the specified first and last name.");
        }
        log.info("Completed removePerson() " + firstName +  " " + lastName);
        return deletedPerson;
    }
}