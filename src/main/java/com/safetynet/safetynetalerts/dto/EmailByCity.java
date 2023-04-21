package com.safetynet.safetynetalerts.dto;

import java.util.List;

public class EmailByCity {
    private List<String> mail;

    public EmailByCity(List<String> mail) {
        this.mail = mail;
    }

    public List<String> getMail() {
        return mail;
    }

    public void setMail(List<String> mail) {
        this.mail = mail;
    }
}
