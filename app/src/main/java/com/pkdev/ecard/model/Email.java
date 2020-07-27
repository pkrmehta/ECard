package com.pkdev.ecard.model;

public class Email {
    String email,type;

    public Email(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Email(String email, String type) {
        this.email = email;
        this.type = type;
    }
}
