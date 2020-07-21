package com.pkdev.ecard.model;

public class Contact {
    String image,name,title,userid,saved;

    public String getSaved() {
        return saved;
    }

    public void setSaved(String saved) {
        this.saved = saved;
    }

    public  Contact(){

    }

    public Contact(String image, String name, String title, String userid, String saved) {
        this.image = image;
        this.name = name;
        this.title = title;
        this.userid = userid;
        this.saved = saved;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
