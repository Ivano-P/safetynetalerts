package com.safetynet.safetynetalerts.dto;

import java.util.List;

public class MinorAndFamilyByAddress {
    private List<MinorAndFamily> MinorsAgesAndFamilyMembers;

    public MinorAndFamilyByAddress(List<MinorAndFamily> minorsAgesAndFamilyMembers) {
        MinorsAgesAndFamilyMembers = minorsAgesAndFamilyMembers;
    }

    public List<MinorAndFamily> getMinorsAgesAndFamilyMembers() {
        return MinorsAgesAndFamilyMembers;
    }

    public void setMinorsAgesAndFamilyMembers(List<MinorAndFamily> minorsAgesAndFamilyMembers) {
        MinorsAgesAndFamilyMembers = minorsAgesAndFamilyMembers;
    }
}
