package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.exceptions.DuplicateFirestationException;
import com.safetynet.safetynetalerts.exceptions.FirestationNotFoundException;
import com.safetynet.safetynetalerts.model.Firestation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FirestationsRepositoryImplTest {

    private static List<Firestation> mockListOfAllFirestations;
    private FirestationRepositoryImpl firesationRepository;


    @BeforeEach
    void setUpPerEach() {
        mockListOfAllFirestations = new ArrayList<>();
        mockListOfAllFirestations.add(new Firestation("1 route saint george", "1"));
        mockListOfAllFirestations.add(new Firestation("2 rue saint antoine", "1"));
        mockListOfAllFirestations.add(new Firestation("2 rue jean", "2"));
        mockListOfAllFirestations.add(new Firestation("1 route dupont", "3"));
        firesationRepository = new FirestationRepositoryImpl(mockListOfAllFirestations);
    }

    //
    @Test
    @DisplayName("test addresses covered by fire-station when sorting by station number are found correctly")
    void testFindAddressByFirestationNumber() {
        // Arrange
        String idFirestation = "1";

        // Act
        List<String> actualAddresses = firesationRepository.findAddressByFirestationNumber(idFirestation);

        // Assert
        assertThat(actualAddresses).hasSize(2);
        assertThat(actualAddresses.get(0)).isEqualTo("1 route saint george");
        assertThat(actualAddresses.get(1)).isEqualTo("2 rue saint antoine");
    }

    @Test
    @DisplayName("test find fire station number by address when address is found")
    void testFindFirestationNumberByAddress() {
        // Arrange
        String address = "2 rue jean";

        // Act
        String actualFirestationNumber = firesationRepository.findFirestationNumberByAddress(address);

        // Assert
        assertThat(actualFirestationNumber).isEqualTo("2");
    }

    @Test
    @DisplayName("test find fire station number by address when address is not found")
    void testFindFirestationNumberByAddressWhenAddressNotFound() {
        // Arrange
        String address = "address not found";

        // Act & Assert
        assertThrows(FirestationNotFoundException.class, () -> {
            firesationRepository.findFirestationNumberByAddress(address);
        });
    }

    @Test
    @DisplayName("test add fire station when there is no duplicate")
    void testAddFirestation() {
        // Arrange
        Firestation firestationToAdd = new Firestation("2 rue louis", "4");

        // Act
        Firestation actualFirestation = firesationRepository.addFirestation(firestationToAdd);

        // Assert
        assertThat(actualFirestation).isEqualTo(firestationToAdd);
        assertThat(mockListOfAllFirestations).contains(firestationToAdd);
    }

    @Test
    @DisplayName("test add fire station when there is duplicate")
    void testAddFirestationWhenDuplicate() {
        // Arrange
        Firestation firestationToAdd = new Firestation("1 route saint george", "1");

        // Act & Assert
        assertThrows(DuplicateFirestationException.class, () -> {
            firesationRepository.addFirestation(firestationToAdd);
        });
    }

    @Test
    @DisplayName("test update fire station by address when address is found")
    void testUpdateFirestationByAddress() {
        // Arrange
        String addressCoveredByFirestation = "1 route saint george";
        String firestationNumberToUpdate = "5";
        Firestation expectedFirestation = new Firestation(addressCoveredByFirestation, firestationNumberToUpdate);

        // Act
        Firestation actualFirestation = firesationRepository.updateFirestationByAddress(
                addressCoveredByFirestation, firestationNumberToUpdate);

        // Assert
        assertThat(actualFirestation).isEqualTo(expectedFirestation);
        assertThat(firesationRepository.listOfAllFirestations).contains(expectedFirestation);
    }

    @Test
    @DisplayName("test update fire station by address when address is not found")
    void testUpdateFirestationByAddressNotFound() {

        // Arrange
        String addressCoveredByFirestation = "3 route not found";
        String firestationNumberToUpdate = "5";

        // Act
        FirestationNotFoundException exception = assertThrows(FirestationNotFoundException.class,
                () -> firesationRepository.updateFirestationByAddress(addressCoveredByFirestation,
                                firestationNumberToUpdate));
        //Assert
        assertThat(exception.getMessage()).isEqualTo("No firestation found with the specified address");
    }

    @Test
    @DisplayName("test remove fire station by address when address is found")
    void testRemoveFirestationByAddress() {
        // Arrange
        String addressCoveredByFirestation = "1 route saint george";
        Firestation expectedFirestation = new Firestation(addressCoveredByFirestation, "1");
        // Act
        Firestation actualFirestation = firesationRepository.removeFirestationByAddress(addressCoveredByFirestation);

        // Assert
        //assertThat(actualFirestation).isEqualTo(expectedFirestation);
        assertThat(firesationRepository.listOfAllFirestations).doesNotContain(expectedFirestation);
    }

    @Test
    @DisplayName("test remove fire station by address when address is not found")
    void testRemoveFirestationByAddressNotFound() {
        // Arrange
        String addressCoveredByFirestation = "3 route not found";
        // Act
        FirestationNotFoundException exception = assertThrows(FirestationNotFoundException.class,
                        () -> firesationRepository.removeFirestationByAddress(addressCoveredByFirestation));
        //Assert
        assertThat(exception.getMessage()).isEqualTo("No firestation found with the specified address");
    }

}
