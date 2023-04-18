package com.safetynet.safetynetalerts.dto;

import java.util.List;

public class PhoneNumbersByFirestation {
    List<String> phoneNumber;

    public PhoneNumbersByFirestation(List<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(List<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
