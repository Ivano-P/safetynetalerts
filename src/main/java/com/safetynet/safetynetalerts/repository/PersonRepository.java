package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynetalerts.dao.ExtractObject.extractDataFromJason;

@Repository
public class PersonRepository {
    List<Person> listOfAllPersons;

    //constructor for productions
    public PersonRepository() {

        this.listOfAllPersons = extractDataFromJason().getPersons();
    }

    // Dependency injection constructor for testing
    PersonRepository(List<Person> listOfAllPersons) {

        this.listOfAllPersons = listOfAllPersons;
    }


    public List<Person> sortPeopleByFireStation(String addressCovereByFirestation){
        ArrayList<Person> peopleHandledByFireStation = new ArrayList<>();

        for (Person person: listOfAllPersons){
            if (person.getAddress().equals(addressCovereByFirestation)){
                peopleHandledByFireStation.add(person);
            }
        }

        return peopleHandledByFireStation;
    }

    public List<Person> sortPeopleByAddress(String address){
        ArrayList<Person> personsAtSameAddress = new ArrayList<>();
        for(Person person : listOfAllPersons){
            if(person.getAddress().equals(address)){
                personsAtSameAddress.add(person);
            }
        }
        return personsAtSameAddress;
    }

    //TODO: unit test
    public List<Person> findPeopleByName(String firstName, String lastName){
        ArrayList<Person> personsWithThatName= new ArrayList<>();
        for (Person person : listOfAllPersons){
            if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)){
                personsWithThatName.add(person);
            }
        }
        return personsWithThatName;
    }

    //TODO: unit test
    //returns a list of all persons that lives a city
    public List<Person> findPeopleByCity(String city){
        List<Person> peopleFromCity = new ArrayList<>();
        for (Person person : listOfAllPersons){
            if (person.getCity().equals(city)){
                peopleFromCity.add(person);
            }
        }
        return peopleFromCity;
    }
}
