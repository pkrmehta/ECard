package com.pkdev.ecard.model;

public class Phone {
    String number,code,type;

    public Phone(){

    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Phone(String number, String code, String type) {
        this.number = number;
        this.code = code;
        this.type = type;
    }
}
