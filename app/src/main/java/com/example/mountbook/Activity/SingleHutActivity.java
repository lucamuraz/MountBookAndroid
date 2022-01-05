package com.example.mountbook.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import com.example.mountbook.AppManager;
import com.example.mountbook.Model.Reservation;
import com.example.mountbook.Model.Shelter;
import com.example.mountbook.R;
import com.example.mountbook.SaveSharedPreferences;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SingleHutActivity extends AppCompatActivity {

    private TextView hut_email;
    private TextView hut_phone;
    private final Context ctx=this;
    private CardView layer;
    private EditText date;
    private Spinner hosts;
    private TextView datePr;
    private TextView nHostPr;
    private Button edit;
    private Long startDate;
    private Long endDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlehut);
        Shelter shelter = AppManager.getInstance().getSingleHut();

        CardView confirm = findViewById(R.id.confirm);
        CardView book = findViewById(R.id.card_button);
        Button confirmBook = findViewById(R.id.confirm_book);
        layer=findViewById(R.id.layer);
        date=findViewById(R.id.editTextDateB);
        hosts=findViewById(R.id.spinner2B);
        datePr=findViewById(R.id.dataPr);
        nHostPr=findViewById(R.id.hostPr);
        edit=findViewById(R.id.edit_book);
        ImageView exit = findViewById(R.id.exit);

        TextView hut_name = findViewById(R.id.hut_name);
        hut_name.setText(shelter.getTitle());
        TextView hut_info = findViewById(R.id.hut_info);
        hut_info.setText(shelter.getInfo());
        TextView hut_desc = findViewById(R.id.hut_desc);
        hut_desc.setText(shelter.getDescr());
        TextView hut_service = findViewById(R.id.hut_service);
        hut_service.setText(shelter.getServices());
        hut_email=findViewById(R.id.hut_contact1);
        hut_email.setText(shelter.getEmail());
        hut_phone=findViewById(R.id.hut_contact2);
        hut_phone.setText(shelter.getPhone());

        Toolbar toolbar = findViewById(R.id.toolbar7);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_24px);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryVariant, this.getTheme()));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        MapView map = findViewById(R.id.map2);
        map.setTileSource(TileSourceFactory.MAPNIK);

        requestPermissionsIfNecessary(new String[] {
                // if you need to show the current location, uncomment the line below
                // Manifest.permission.ACCESS_FINE_LOCATION,
                // WRITE_EXTERNAL_STORAGE is required in order to show the map
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(9.5);
        GeoPoint startPoint = new GeoPoint(45.191018, 7.074824);
        mapController.setCenter(startPoint);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        ArrayList<OverlayItem> items = new ArrayList<>();
        items.add(new OverlayItem(shelter.getTitle(), shelter.getDescr(), new GeoPoint(shelter.getLat(), shelter.getLon())));
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //do something
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {

                        return false;
                    }
                }, ctx);
        map.getOverlays().add(mOverlay);

        hut_email.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { hut_email.getText().toString() });
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(Intent.createChooser(intent, ""));
        });

        hut_phone.setOnClickListener(view -> {

            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                String phoneNumber= hut_phone.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }else{
                requestPermissionLauncher.launch(
                        Manifest.permission.CALL_PHONE);
            }
        });

        book.setOnClickListener(view -> {
            layer.setVisibility(View.VISIBLE);
        });



        //todo date.setText();

        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder
                .dateRangePicker();
        materialDateBuilder.setTitleText("Seleziona le date");
        materialDateBuilder.setTheme(R.style.MaterialCalendarTheme);

        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        date.setOnClickListener(
                v -> materialDatePicker.show(getSupportFragmentManager(),
                        "MATERIAL_DATE_PICKER"));

//        materialDatePicker.addOnPositiveButtonClickListener(
//                        selection -> date.setText(materialDatePicker.getHeaderText()));

        materialDatePicker.addOnPositiveButtonClickListener((MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>) selection -> {
            date.setText(materialDatePicker.getHeaderText());
            startDate = selection.first;
            endDate = selection.second;
        });

        List<String> nrHost = new ArrayList<>(Arrays.asList("1 ospite", "2 ospiti", "3 ospiti", "4 ospiti", "5 ospiti",
                "6 ospiti", "7 ospiti", "8 ospiti", "9 ospiti", "10 ospiti"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_spinner_item, nrHost);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hosts.setAdapter(adapter);

        edit.setOnClickListener(view -> {
            datePr.setVisibility(View.INVISIBLE);
            nHostPr.setVisibility(View.INVISIBLE);
            date.setVisibility(View.VISIBLE);
            hosts.setVisibility(View.VISIBLE);
            edit.setVisibility(View.INVISIBLE);
        });

        exit.setOnClickListener(view -> layer.setVisibility(View.INVISIBLE));

        confirmBook.setOnClickListener(view -> {
            //todo invio dati per conferma
            if(true){
                //todo toast messaggio
                Date start=new Date(startDate);
                Date end=new Date(endDate);
                int guest=Integer.parseInt(hosts.getSelectedItem().toString().substring(0,1));
                Reservation res=new Reservation(start,guest,shelter,end,0, SaveSharedPreferences.getUserName(ctx));
                AppManager.getInstance().getReservationList().add(res);
                Intent i=new Intent(ctx, MainActivity.class);
                i.putExtra("redirect", 2);
                startActivity(i);
            }else{
                //todo errore
            }
        });

    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(ctx, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    String phoneNumber= hut_phone.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phoneNumber));
                    startActivity(intent);
                }
            });
}
