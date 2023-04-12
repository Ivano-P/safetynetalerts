package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.dao.ExtractObject;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.SafetyNet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonRepositoryTest {

    @Test
    @DisplayName("test that list of Persons covered by firestation is being returned " +
            "correctly")
    public void testSortPeopleByFireStation() {
        // Arrange
        String addressCoveredByFirestation = "1 route saint george";
        PersonRepository personRepository = new PersonRepository();


        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", addressCoveredByFirestation, null, null
                , null, null));
        persons.add(new Person("Jane", "Doe", "1 route dupont", null, null
                , null, null));
        persons.add(new Person("Jack", "Doe", addressCoveredByFirestation, null, null
                , null, null));

        SafetyNet safetyNet = new SafetyNet(persons, null, null);

        //ExtracObject returns a static SafetyNet object, hence the use pf MockStatic
        try (MockedStatic<ExtractObject> mockedStaticClass = Mockito.mockStatic(ExtractObject.class)) {
            mockedStaticClass.when(ExtractObject::extractDataFromJason).thenReturn(safetyNet);

            // Act
            ArrayList<Person> sortedPersons = personRepository.sortPeopleByFireStation(addressCoveredByFirestation);

            // Assert
            assertThat(sortedPersons).hasSize(2);
            assertThat(sortedPersons.get(0).getAddress()).isEqualTo(addressCoveredByFirestation);
            assertThat(sortedPersons.get(1).getAddress()).isEqualTo(addressCoveredByFirestation);
        }
    }
}
