package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PersonRepositoryTest {
    private static List<Person> mockListOfAllPersons;
    private PersonRepositoryImpl personRepository;

    @BeforeAll
    static void setUp() throws Exception {
        mockListOfAllPersons = new ArrayList<>();
        mockListOfAllPersons.add(new Person("John", "Doe", "1 route saint george", null, null, null, null));
        mockListOfAllPersons.add(new Person("Jane", "Doe", "1 route dupont", null, null, null, null));
        mockListOfAllPersons.add(new Person("Jack", "Doe", "1 route saint george", null, null, null, null));
        mockListOfAllPersons.add(new Person("Jaky", "Chan", "2 rue jean", null, null, null, null));
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
}
