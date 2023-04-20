package com.safetynet.safetynetalerts.dto;

import java.util.List;

//TODO: remove this class and return a list of minorAndFamily directly to controller, change the necessary service class
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
