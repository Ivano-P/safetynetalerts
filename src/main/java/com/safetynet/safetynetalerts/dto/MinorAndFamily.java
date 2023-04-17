package com.safetynet.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"minor", "age", "familyMember"})
public class MinorAndFamily {

    private Person Minor;
    private int age;
    private List<Person> familyMember;

    public MinorAndFamily(Person minor, int age, List<Person> familyMember) {
        Minor = minor;
        this.age = age;
        this.familyMember = familyMember;
    }

    public Person getMinor() {
        return Minor;
    }

    public void setMinor(Person minor) {
        Minor = minor;
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
