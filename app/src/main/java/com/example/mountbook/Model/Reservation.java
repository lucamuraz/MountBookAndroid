package com.example.mountbook.Model;

import java.util.Date;

public class Reservation {

    //todo
    private Shelter shelter;
    private Date startDate;
    private Date endDate;
    private int guest;
    //private int idStanza; //visibile al cliente
    private int id;
    private String user;

    public Reservation(Date startDate, int guest, Shelter shelter, Date endDate, int id, String user) {
        this.shelter = shelter;
        this.endDate = endDate;
        this.id = id;
        this.user = user;
        this.guest = guest;
        this.startDate = startDate;
    }



    public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public int getGuest() { return guest; }

    public void setGuest(int guest) { this.guest = guest; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Date getEndDate() { return endDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public Shelter getHut() { return shelter; }

    public void setHut(Shelter shelter) { this.shelter = shelter; }

    public String getUser() { return user; }

    public void setUser(String user) { this.user = user; }
}
