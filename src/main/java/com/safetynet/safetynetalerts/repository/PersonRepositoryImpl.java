package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Person;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynetalerts.dao.ExtractObject.extractDataFromJason;

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
        ArrayList<Person> peopleHandledByFireStation = new ArrayList<>();

        for (Person person : listOfAllPersons) {
            if (person.getAddress().equals(addressCovereByFirestation)) {
                peopleHandledByFireStation.add(person);
            }
        }

        return peopleHandledByFireStation;
    }

    @Override
    public List<Person> findPeopleByAddress(String address) {
        ArrayList<Person> personsAtSameAddress = new ArrayList<>();
        for (Person person : listOfAllPersons) {
            if (person.getAddress().equals(address)) {
                personsAtSameAddress.add(person);
            }
        }
        return personsAtSameAddress;
    }

    //TODO: unit test
    @Override
    public List<Person> findPeopleByName(String firstName, String lastName) {
        ArrayList<Person> personsWithThatName = new ArrayList<>();
        for (Person person : listOfAllPersons) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                personsWithThatName.add(person);
            }
        }
        return personsWithThatName;
    }

    //TODO: unit test
    @Override
    //returns a list of all persons that lives a city
    public List<Person> findPeopleByCity(String city) {
        List<Person> peopleFromCity = new ArrayList<>();
        for (Person person : listOfAllPersons) {
            if (person.getCity().equals(city)) {
                peopleFromCity.add(person);
            }
        }
        return peopleFromCity;
    }

    //TODO: unit test
    @Override
    public void addPerson(Person personToAdd) {
        listOfAllPersons.add(personToAdd);
    }

    //edits Person with same first and last name as person in parsed in as argument.
    //TODO: unit test
    @Override
    public void updatePerson(Person personToEdit) {
        for (Person person : listOfAllPersons) {
            if (personToEdit.getFirstName().equals(person.getFirstName()) && personToEdit.getLastName().equals(person
                    .getLastName())) {
                person.setAddress(personToEdit.getAddress());
                person.setCity(personToEdit.getCity());
                person.setCity(personToEdit.getCity());
                person.setPhone(personToEdit.getPhone());
                person.setEmail(personToEdit.getEmail());
                break;
            }
        }
    }

    /*
    cycling through the listOfAllPeople and stops iterating through the list once the person with the same first and
    last name has been removed
    */
    @Override
    public Person removePerson(String firstName, String lastName) {
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
        return deletedPerson;
    }
}