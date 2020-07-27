package com.pkdev.ecard.model;

public class NotificationCard {
    String icon,name,desc,heading,time,userid;

    public String getIcon() {
        return icon;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public NotificationCard(){

    }

    public NotificationCard(String icon, String name, String desc, String heading, String time,String userid) {
        this.icon = icon;
        this.name = name;
        this.desc = desc;
        this.heading = heading;
        this.time = time;
        this.userid = userid;
    }
}
