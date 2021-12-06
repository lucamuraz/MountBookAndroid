package com.example.mountbook.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.mountbook.R;
import com.example.mountbook.SaveSharedPreferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryVariant, this.getTheme()));

        final Context ctx = this;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    Intent i;
                    if(SaveSharedPreferences.getUserName(ctx).length()!=0){
                        i=new Intent(ctx, MainActivity.class);
                        i.putExtra("redirect", 0);
                    }
                    else{
                        i=new Intent(ctx,LoginActivity.class);
                    }
                    finish();
                    startActivity(i);
                }
            }
        };
        timer.start();
    }
}