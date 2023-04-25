package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Firestation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FirestationsRepositoryTest {

    private static List<Firestation> mockListOfAllFirestations;
    private FirestationsRepository firesationRepository;

    @BeforeAll
    static void setUp() throws Exception {
        mockListOfAllFirestations = new ArrayList<>();
        mockListOfAllFirestations.add(new Firestation("1 route saint george", "1"));
        mockListOfAllFirestations.add(new Firestation("2 rue saint antoine", "1"));
        mockListOfAllFirestations.add(new Firestation("2 rue jean", "2"));
        mockListOfAllFirestations.add(new Firestation("1 route dupont", "3"));
    }

    @BeforeEach
    void setUpPerEach() {
        firesationRepository = new FirestationsRepository(mockListOfAllFirestations);
    }

    //
    @Test
    @DisplayName("test addresses covered by fire-station when sorting by station number are found correctly")
    void testSortAdressRelatedToFirestation() {
        // Arrange
        String idFirestation = "1";

        // Act
        List<String> actualAddresses = firesationRepository.findAddressByFirestationNumber(idFirestation);

        // Assert
        assertThat(actualAddresses).hasSize(2);
        assertThat(actualAddresses.get(0)).isEqualTo("1 route saint george");
        assertThat(actualAddresses.get(1)).isEqualTo("2 rue saint antoine");
    }

}
