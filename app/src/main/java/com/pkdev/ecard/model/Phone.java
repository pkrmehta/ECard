package com.pkdev.ecard.model;

public class Phone {
    String countrycode,number,phoneid,isprimary;

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhoneid() {
        return phoneid;
    }

    public void setPhoneid(String phoneid) {
        this.phoneid = phoneid;
    }

    public String getIsprimary() {
        return isprimary;
    }

    public void setIsprimary(String isprimary) {
        this.isprimary = isprimary;
    }

    public Phone(){

    }
    public Phone(String countrycode, String number, String phoneid, String isprimary) {
        this.countrycode = countrycode;
        this.number = number;
        this.phoneid = phoneid;
        this.isprimary = isprimary;
    }
}
