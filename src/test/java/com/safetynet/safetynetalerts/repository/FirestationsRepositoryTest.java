package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.dao.ExtractObject;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.SafetyNet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FirestationsRepositoryTest {

    //
    @Test
    @DisplayName("test addresses covered by fire-station when sorting by station number are found correctly")
    public void testSortAdressRelatedToFirestation() {
        // Arrange
        String idFirestation = "1";
        List<String> expectedAddresses = Arrays
                .asList("1 route saint george", "2 rue saint antoine");

        FirestationsRepository firesationRepository = new FirestationsRepository();

            //add the list of expected addresses that will be passed into mock fireStation lists
        List<Firestation> firestations = new ArrayList<>();
        firestations.add(new Firestation(expectedAddresses.get(0), "1"));
        firestations.add(new Firestation(expectedAddresses.get(1), "1"));

            //ExtracObject returns a static SafetyNet object, hence the use pf MockStatic
        try(MockedStatic<ExtractObject> mockedStaticClass = Mockito.mockStatic(ExtractObject.class)){
           SafetyNet mockedSafetyNet = new SafetyNet(null, firestations, null);

            mockedStaticClass.when(ExtractObject::extractDataFromJason)
            .thenReturn(mockedSafetyNet);

            // Act
            List<String> actualAddresses = firesationRepository.sortAdressRelatedToFirestation(idFirestation);

            // Assert
            assertThat(actualAddresses).isEqualTo(expectedAddresses);
        }
    }

}
