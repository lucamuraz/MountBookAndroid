package com.example.mountbook.Model;

public class Reservation {

    //todo
    private String title;
    private String info;

    public Reservation(String title, String info) {
        this.title = title;
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInfo(String info) {
        this.info = info;
    }



}
