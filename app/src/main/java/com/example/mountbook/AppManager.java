package com.example.mountbook;


import android.content.Context;

import com.example.mountbook.Model.Shelter;
import com.example.mountbook.Model.Reservation;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class AppManager {
    private static AppManager singleInstance;
    private NavigationBarView navigationBarView;
    private Context ctx;
    private List<Shelter> shelterListResult =new ArrayList<>();
    private List<Shelter> shelterListAll = new ArrayList<>();
    private Shelter singleShelter;
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

    public void setHutListResult(List<Shelter> shelterListResult) {
        this.shelterListResult = shelterListResult;
    }

    public List<Shelter> getHutListResult() {
        return shelterListResult;
    }

    public List<Shelter> getHutListAll() {
        return shelterListAll;
    }

    public void setHutListAll(List<Shelter> shelterListAll) {
        this.shelterListAll = shelterListAll;
    }

    public Shelter getSingleHut() {
        return singleShelter;
    }

    public void setSingleHut(Shelter singleShelter) {
        this.singleShelter = singleShelter;
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
