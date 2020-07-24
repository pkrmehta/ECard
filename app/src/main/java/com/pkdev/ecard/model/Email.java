package com.pkdev.ecard.model;

public class Email {
    String email;
    String isprimary;

    public Email(){

    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsprimary() {
        return isprimary;
    }

    public void setIsprimary(String isprimary) {
        this.isprimary = isprimary;
    }

    public Email(String email, String isprimary) {
        this.email = email;
        this.isprimary = isprimary;
    }
}
