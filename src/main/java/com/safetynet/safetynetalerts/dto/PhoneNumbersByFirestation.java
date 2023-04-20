package com.safetynet.safetynetalerts.dto;

import java.util.List;

public class PhoneNumbersByFirestation {
    List<String> phoneNumbers;

    public PhoneNumbersByFirestation(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
