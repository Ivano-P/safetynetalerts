package com.safetynet.safetynetalerts.dto;

import java.util.List;

public class MinorAndFamilyByAddress {
    private List<MinorAndFamily> minorAndFamilies;

    public MinorAndFamilyByAddress(List<MinorAndFamily> minorsAgesAndFamilyMembers) {
        this.minorAndFamilies = minorsAgesAndFamilyMembers;
    }

    public List<MinorAndFamily> getMinorAndFamilies() {
        return minorAndFamilies;
    }

    public void setMinorAndFamilies(List<MinorAndFamily> minorAndFamilies) {
        this.minorAndFamilies = minorAndFamilies;
    }
}
