package com.safetynet.safetynetalerts.dto;

import java.util.List;

public class PersonInfoAndMedicalRecord {
    private PersonInfo personInfo;
    private int age;
    private List<String> medication;
    private List<String> allergies;

    public PersonInfoAndMedicalRecord(PersonInfo personInfo, int age, List<String> medication, List<String> allergies) {
        this.personInfo = personInfo;
        this.age = age;
        this.medication = medication;
        this.allergies = allergies;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getMedication() {
        return medication;
    }

    public void setMedication(List<String> medication) {
        this.medication = medication;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
