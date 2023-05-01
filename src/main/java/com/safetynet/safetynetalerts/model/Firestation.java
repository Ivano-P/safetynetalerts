package com.safetynet.safetynetalerts.model;

import lombok.ToString;

import java.util.List;

@ToString
public class Firestation {
    private String address;
    private String station;

    public Firestation(String address, String station) {
        this.address = address;
        this.station = station;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
