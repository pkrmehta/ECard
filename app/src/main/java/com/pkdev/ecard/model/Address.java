package com.pkdev.ecard.model;

public class Address {
    String address;

    public Address(){}

    public Address(String address, String isCurrent) {
        this.address = address;
        this.isCurrent = isCurrent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(String isCurrent) {
        this.isCurrent = isCurrent;
    }

    String isCurrent;
}
