package com.pkdev.ecard.model;

public class Address {
    String address,type;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Address(){

    }

    public Address(String address, String type) {
        this.address = address;
        this.type = type;
    }
}
