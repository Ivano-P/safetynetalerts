package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.model.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PersonRepositoryTest {
    private static List<Person> mockListOfAllPersons;
    private PersonRepositoryImpl personRepository;

    @BeforeAll
    static void setUp() throws Exception {
        mockListOfAllPersons = new ArrayList<>();
        mockListOfAllPersons.add(new Person("John", "Doe", "1 route saint george",
                "City1", "12345", "111-111-1111", "john@example.com"));
        mockListOfAllPersons.add(new Person("Jane", "Doe", "1 route dupont",
                "City2", "12346", "222-222-2222", "jane@example.com"));
        mockListOfAllPersons.add(new Person("Jack", "Doe", "1 route saint george",
                "City1", "12345", "333-333-3333", "jack@example.com"));
        mockListOfAllPersons.add(new Person("Jaky", "Chan", "2 rue jean",
                "City3", "12347", "444-444-4444", "jaky@example.com"));
    }

    @BeforeEach()
    void setUpPerEach() {
        personRepository = new PersonRepositoryImpl(mockListOfAllPersons);

    }

    @Test
    @DisplayName("test that list of Persons covered by firestation is being returned correctly")
    void testSortPeopleByFireStation() {
        // Arrange
        String addressCoveredByFirestation = "1 route saint george";

        // Act
        List<Person> sortedPersons = personRepository.findPeopleByFireStationAddress(addressCoveredByFirestation);

        // Assert
        assertThat(sortedPersons).hasSize(2);
        assertThat(sortedPersons.get(0).getAddress()).isEqualTo(addressCoveredByFirestation);
        assertThat(sortedPersons.get(1).getAddress()).isEqualTo(addressCoveredByFirestation);

    }

    @Test
    @DisplayName("test")
    void testSortPeopleByAddress() {
        //Arrange
        String Address = "1 route saint george";

        // Act
        List<Person> actualPersonsAtSameAddress = personRepository.findPeopleByAddress(Address);

        // Assert
        assertThat(actualPersonsAtSameAddress).hasSize(2);

        assertThat(actualPersonsAtSameAddress.get(0).getFirstName()).isEqualTo("John");
        assertThat(actualPersonsAtSameAddress.get(0).getLastName()).isEqualTo("Doe");
        assertThat(actualPersonsAtSameAddress.get(0).getAddress()).isEqualTo("1 route saint george");

        assertThat(actualPersonsAtSameAddress.get(1).getFirstName()).isEqualTo("Jack");
        assertThat(actualPersonsAtSameAddress.get(1).getLastName()).isEqualTo("Doe");
        assertThat(actualPersonsAtSameAddress.get(1).getAddress()).isEqualTo("1 route saint george");
    }

    @Test
    @DisplayName("test that list of persons with specified first and last name is being returned correctly")
    void testFindPeopleByName() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";

        // Act
        List<Person> personsWithThatName = personRepository.findPeopleByName(firstName, lastName);

        // Assert
        assertThat(personsWithThatName).hasSize(1);
        assertThat(personsWithThatName.get(0).getFirstName()).isEqualTo(firstName);
        assertThat(personsWithThatName.get(0).getLastName()).isEqualTo(lastName);
    }

    @Test
    @DisplayName("test that list of persons in specified city is being returned correctly")
    void testFindPeopleByCity() {
        // Arrange
        String city = "City1";

        // Act
        List<Person> peopleFromCity = personRepository.findPeopleByCity(city);

        // Assert
        assertThat(peopleFromCity).hasSize(2);
        assertThat(peopleFromCity.get(0).getCity()).isEqualTo(city);
        assertThat(peopleFromCity.get(1).getCity()).isEqualTo(city);
    }

    @Test
    @DisplayName("test that a new person can be added correctly")
    void testAddPerson() {
        // Arrange
        Person newPerson = new Person("Mark", "Smith", "3 rue paul", "Paris",
                "12349", "01 02 03 04 05 ", "mark@example.com");

        // Act
        Person addedPerson = personRepository.addPerson(newPerson);

        // Assert
        assertThat(addedPerson).isEqualTo(newPerson);
        assertThat(personRepository.listOfAllPersons).contains(newPerson);
    }

    @Test
    @DisplayName("test that a person can be updated correctly")
    void testUpdatePerson() {
        // Arrange
        Person personToUpdate = new Person("John", "Doe", "5 route saint george",
                "City5", "12350", "666-666-6666", "john.new@example.com");

        // Act
        Person updatedPerson = personRepository.updatePerson(personToUpdate);

        // Assert
        assertThat(updatedPerson).isEqualTo(personToUpdate);
        assertThat(personRepository.listOfAllPersons).contains(updatedPerson);
        assertThat(personRepository.listOfAllPersons).doesNotContain(new Person("John", "Doe",
               "1 route saint george", "City1", "12345", "111-111-1111",
                "john@example.com"));
    }

    @Test
    @DisplayName("test that updating a non-existing person throws PersonNotFoundException")
    void testUpdateNonExistingPerson() {
        // Arrange
        Person nonExistingPerson = new Person("NonExisting", "Person", "6 route unknown",
                "City6", "12351", "777-777-7777", "nonexisting@example.com");

        // Act and Assert
        assertThrows(PersonNotFoundException.class, () -> personRepository.updatePerson(nonExistingPerson));
    }

    @Test
    @DisplayName("test that a person can be removed correctly")
    void testRemovePerson() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";

        // Act
        Person deletedPerson = personRepository.removePerson(firstName, lastName);

        // Assert
        assertThat(deletedPerson.getFirstName()).isEqualTo(firstName);
        assertThat(deletedPerson.getLastName()).isEqualTo(lastName);
        assertThat(personRepository.listOfAllPersons).doesNotContain(deletedPerson);
    }

    @Test
    @DisplayName("test that removing a non-existing person throws PersonNotFoundException")
    void testRemoveNonExistingPerson() {
        // Arrange
        String firstName = "NonExisting";
        String lastName = "Person";

        // Act and Assert
        assertThrows(PersonNotFoundException.class, () -> personRepository.removePerson(firstName, lastName));
    }

}
