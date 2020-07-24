package com.pkdev.ecard.model;

public class Work {
    String company,position,start,end;

    public Work(String company, String position, String start, String end) {
        this.company = company;
        this.position = position;
        this.start = start;
        this.end = end;
    }

    public Work(){

    }
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
