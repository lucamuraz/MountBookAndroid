package com.example.mountbook.Model;

import java.util.Date;

public class Reservation {

    private String structureName;
    private Structure structure;
    private long structureId;
    private Date startDate;
    private Date endDate;
    private int guest;
    private int id;
    private String user;

    public Reservation(String structureName, long structureId, Date startDate, Date endDate, int guest, int id, String user) {
        this.structureName = structureName;
        this.structureId = structureId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guest = guest;
        this.id = id;
        this.user = user;
    }


    public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public int getGuest() { return guest; }

    public void setGuest(int guest) { this.guest = guest; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Date getEndDate() { return endDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public String getHut() { return structureName; }

    public void setHut(Structure structure) { this.structure = structure; }

    public String getUser() { return user; }

    public void setUser(String user) { this.user = user; }

    public long getStructureId() { return structureId; }

    public String getStructureName() { return structureName; }
}
