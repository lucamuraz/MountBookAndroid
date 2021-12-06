package com.example.mountbook;


import android.content.Context;

import com.google.android.material.navigation.NavigationBarView;

public class AppManager {
    private static AppManager singleInstance;
    private NavigationBarView navigationBarView;
    private Context ctx;

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
}
