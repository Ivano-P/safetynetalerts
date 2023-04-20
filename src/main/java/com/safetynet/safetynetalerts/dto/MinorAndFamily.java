package com.safetynet.safetynetalerts.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetalerts.model.Person;

import java.util.List;

@JsonPropertyOrder({"minor", "age", "familyMember"})
public class MinorAndFamily {

    private Person minor;
    private int age;
    private List<Person> familyMember;

    public MinorAndFamily(Person minor, int age, List<Person> familyMember) {
        this.minor = minor;
        this.age = age;
        this.familyMember = familyMember;
    }

    public Person getMinor() {
        return minor;
    }

    public void setMinor(Person minor) {
        this.minor = minor;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Person> getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(List<Person> familyMember) {
        this.familyMember = familyMember;
    }
}
