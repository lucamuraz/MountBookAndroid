package com.example.mountbook.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.mountbook.AppManager;
import com.example.mountbook.Fragment.BookedFragment;
import com.example.mountbook.Fragment.HomeFragment;
import com.example.mountbook.Fragment.MapFragment;
import com.example.mountbook.Fragment.ProfileFragment;
import com.example.mountbook.R;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    NavigationBarView bottomNavigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryVariant, this.getTheme()));
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation);
        AppManager.getInstance().setNavigationBarView(bottomNavigationView);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_AUTO);
        bottomNavigationView.setOnItemSelectedListener(navigationItemSelectedListener);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        Bundle extras = intent.getExtras();
//        assert extras != null;
        int tmp = extras.getInt("redirect");
        AppManager.getInstance().setCtx(this);
        setSupportActionBar(toolbar);
        switch (tmp){
            case 1:
                getSupportActionBar().setTitle("Mappa");
                bottomNavigationView.setSelectedItemId(R.id.navigation_map);
                openFragment(MapFragment.newInstance());
                break;
            case 2:
                getSupportActionBar().setTitle("Prenotazioni");
                bottomNavigationView.setSelectedItemId(R.id.navigation_booked);
                openFragment(BookedFragment.newInstance());
                break;
            case 3:
                getSupportActionBar().setTitle("Profilo");
                bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
                openFragment(ProfileFragment.newInstance());
                break;
            default:
                getSupportActionBar().setTitle("Home");
                bottomNavigationView.setSelectedItemId(R.id.navigation_search);
                openFragment(HomeFragment.newInstance());
                break;
        }
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    NavigationBarView.OnItemSelectedListener navigationItemSelectedListener =
            item -> {
                setSupportActionBar(toolbar);
                switch (item.getItemId()) {
                    case R.id.navigation_map:
                        getSupportActionBar().setTitle("Mappa");
                        openFragment(MapFragment.newInstance());
                        return true;
                    case R.id.navigation_search:
                        getSupportActionBar().setTitle("Home");
                        openFragment(HomeFragment.newInstance());
                        return true;
                    case R.id.navigation_booked:
                        getSupportActionBar().setTitle("Prenotazioni");
                        openFragment(BookedFragment.newInstance());
                        return true;
                    case R.id.navigation_profile:
                        getSupportActionBar().setTitle("Profilo");
                        openFragment(ProfileFragment.newInstance());
                        return true;
                }
                return false;
            };

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


}