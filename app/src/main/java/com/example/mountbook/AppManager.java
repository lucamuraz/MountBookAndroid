package com.example.mountbook;


import android.content.Context;

import com.example.mountbook.Model.Structure;
import com.example.mountbook.Model.Reservation;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class AppManager {
    private static AppManager singleInstance;
    private NavigationBarView navigationBarView;
    private Context ctx;
    private List<Structure> structureListResult =new ArrayList<>();
    private List<Structure> structureListAll = new ArrayList<>();
    private Structure singleStructure;
    private Reservation reservation;
    private List<Reservation> reservationList;

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public static AppManager getInstance() {
        if (singleInstance == null) {
            singleInstance = new AppManager();
        }
        return singleInstance;
    }

    public NavigationBarView getNavigationBarView() {
        return navigationBarView;
    }

    public void setNavigationBarView(NavigationBarView navigationBarView) {
        this.navigationBarView = navigationBarView;
    }

    public void setHutListResult(List<Structure> structureListResult) {
        this.structureListResult = structureListResult;
    }

    public List<Structure> getHutListResult() {
        return structureListResult;
    }

    public List<Structure> getHutListAll() {
        return structureListAll;
    }

    public void setHutListAll(List<Structure> structureListAll) {
        this.structureListAll = structureListAll;
    }

    public Structure getSingleHut() {
        return singleStructure;
    }

    public void setSingleHut(Structure singleStructure) {
        this.singleStructure = singleStructure;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public List<Reservation> getReservationList() {
        return this.reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}
