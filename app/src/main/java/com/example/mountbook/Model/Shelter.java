package com.example.mountbook.Model;

public class Shelter {

    //todo
    private String title;
    private String info;
    private String descr;
    private String adress;
    private String services;
    private double lat;
    private double lon;
    private int id;
    private String phone;
    private String email;
    private int alt;
    private int pos;
    private int neg;
    //mete
    //immagine

    public Shelter(int id, String title, String info, String descr, String adress, String services, double lat, double lon, String email, String phone, int alt, int pos, int neg) {
        this.title = title;
        this.info = info;
        this.adress = adress;
        this.descr = descr;
        this.services = services;
        this.lat = lat;
        this.lon = lon;
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.alt = alt;
        this.pos = pos;
        this.neg = neg;
        //immagine
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

    public double getLat() { return lat; }

    public double getLon() { return lon; }

    public void setLat(double lat) { this.lat = lat; }

    public void setLon(double lon) { this.lon = lon; }

    public String getAdress() { return adress; }

    public void setAdress(String adress) { this.adress = adress; }

    public String getDescr() { return descr; }

    public void setDescr(String descr) { this.descr = descr; }

    public String getServices() { return services; }

    public void setServices(String services) { this.services = services; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public int getAlt() { return alt; }

    public void setAlt(int alt) { this.alt = alt; }

    public int getNeg() { return neg; }

    public void setNeg(int neg) { this.neg = neg; }

    public int getPos() { return pos; }

    public void setPos(int pos) { this.pos = pos; }
}
