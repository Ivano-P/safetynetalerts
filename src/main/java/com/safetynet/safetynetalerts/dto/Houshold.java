package com.safetynet.safetynetalerts.dto;

import java.util.List;

public class Houshold {
    private String address;
    private List<PersonWithMedicalInfo> persons;

    public Houshold(String address, List<PersonWithMedicalInfo> persons) {
        this.address = address;
        this.persons = persons;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PersonWithMedicalInfo> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonWithMedicalInfo> persons) {
        this.persons = persons;
    }
}
