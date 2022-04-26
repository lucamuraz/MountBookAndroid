package com.example.mountbook.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Shelter {

    //todo
    private int id;
    private String name;
    private String address;
    private Date open;
    private Date close;
    private int maxNumBed;
    private float altitude;
    private double latitude;
    private double longitude;
    private String telephoneNumber;
    private String webSite;
    private String email;
    private String description;
    private int image;
    private int price;

    private int pos;
    private int neg;
    private String services;

    public Shelter(int id, String name, String address, Date open, Date close, int maxNumBed, float altitude, double longitude, double latitude, String telephoneNumber, String webSite, String email, String description, int image, int pos, int neg, String services, int price) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.address = address;
        this.services = services;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.telephoneNumber = telephoneNumber;
        this.webSite = webSite;
        this.email = email;
        this.altitude = altitude;
        this.pos = pos;
        this.neg = neg;
        this.open = open;
        this.close = close;
        this.maxNumBed = maxNumBed;
        this.price=price;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String openstr=simpleDateFormat.format(open);
        String closestr=simpleDateFormat.format(close);
        return "Periodo di apertura: "+openstr+" - "+closestr+"\n\nAltitudine: "+ altitude +" mt.\n\n"+ "Come raggiungerci: "+address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public void setLatitude(float latitude) { this.latitude = latitude; }

    public void setLongitude(float longitude) { this.longitude = longitude; }

    public String getAddress() { return  address; }

    public void setAddress(String address) { this.address = address; }

    public String getDescr() { return description; }

    public void setDescr(String descr) { this.description = descr; }

    public String getServices() { return services; }

    public void setServices(boolean [] services) {
        String ser="";
        if(services[0]){
            ser+="WiFi\n";
        }
        if(services[1]){
            ser+="Equipment\n";
        }
        if(services[2]){
            ser+="Car\n";
        }
        this.services=ser;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getTelephoneNumber() { return telephoneNumber; }

    public void setTelephoneNumber(String telephoneNumber) { this.telephoneNumber = telephoneNumber; }

    public float getAltitude() { return altitude; }

    public void setAltitude(float altitude) { this.altitude = altitude; }

    public int getNeg() { return neg; }

    public void setNeg(int neg) { this.neg = neg; }

    public int getPos() { return pos; }

    public void setPos(int pos) { this.pos = pos; }

    public Date getOpen() { return open; }

    public Date getClose() { return close; }

    public int getMaxNumBed() { return maxNumBed; }

    public String getWebSite() { return webSite; }

    public int getImage() { return image; }

    public void setOpen(Date open) { this.open = open; }

    public void setClose(Date close) { this.close = close; }

    public void setMaxNumBed(int maxNumBed) { this.maxNumBed = maxNumBed; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public void setWebSite(String webSite) { this.webSite = webSite; }

    public void setImage(int image) { this.image = image; }

    public void setPrice(int price) { this.price = price; }

    public int getPrice() { return price; }
}
