package com.example.mountbook.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.mountbook.AppManager;
import com.example.mountbook.Model.Reservation;
import com.example.mountbook.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class SingleBookActivity extends AppCompatActivity {

    TextView hut_name;
    Toolbar toolbar;
    TextView email;
    TextView phone;
    TextView adress;
    TextView date;
    TextView guest;
    Context ctx;
    CardView eval;
    Button evaluate;
    Button evalOk;
    Button evalKo;
    CheckBox cat1_t;
    CheckBox cat1_f;
    CheckBox cat2_t;
    CheckBox cat2_f;
    CheckBox cat3_t;
    CheckBox cat3_f;
    CheckBox cat4_t;
    CheckBox cat4_f;
    CheckBox cat5_t;
    CheckBox cat5_f;
    CheckBox cat6_t;
    CheckBox cat6_f;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        ctx=this;
        Reservation reservation=AppManager.getInstance().getReservation();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlebook);
        hut_name=findViewById(R.id.prenot_rif);
        hut_name.setText(reservation.getHut().getTitle());

        date=findViewById(R.id.prenot_date);
        DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        String dateTxt= dateFormat.format(reservation.getStartDate()) + " - " + dateFormat.format(reservation.getEndDate());
        date.setText(dateTxt);

        guest=findViewById(R.id.prenot_guest);
        if(reservation.getGuest()==1){
            guest.setText(reservation.getGuest()+ " ospite");
        }else{
            guest.setText(reservation.getGuest()+ " ospiti");
        }

        toolbar=findViewById(R.id.toolbar8);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_24px);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("La tua prenotazione");
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryVariant, this.getTheme()));
        toolbar.setNavigationOnClickListener(view -> finish());

        email=findViewById(R.id.prenot_contact1);
        email.setText(reservation.getHut().getEmail());
        email.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email.getText().toString() });
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(Intent.createChooser(intent, ""));
        });

        phone=findViewById(R.id.prenot_contact2);
        phone.setText(reservation.getHut().getPhone());
        phone.setOnClickListener(view -> {

            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                String phoneNumber= phone.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }else{
                requestPermissionLauncher.launch(
                        Manifest.permission.CALL_PHONE);
            }
        });

        adress=findViewById(R.id.prenot_adress);
        adress.setOnClickListener(view -> {

            String uri = "http://maps.google.com/maps?q=loc:" + reservation.getHut().getLat() + "," + reservation.getHut().getLon();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
        });

        eval=findViewById(R.id.card_eval);

        evaluate=findViewById(R.id.evaluate);
        evaluate.setOnClickListener(view -> {
            eval.setVisibility(View.VISIBLE);
        });

        cat1_t=findViewById(R.id.cat1_t);
        cat1_t.setOnClickListener(view -> {
            if(cat1_f.isChecked()){
                cat1_f.setChecked(false);
            }
        });

        cat1_f=findViewById(R.id.cat1_f);
        cat1_f.setOnClickListener(view -> {
            if(cat1_t.isChecked()){
                cat1_t.setChecked(false);
            }
        });

        cat2_t=findViewById(R.id.cat2_t);
        cat2_t.setOnClickListener(view -> {
            if(cat2_f.isChecked()){
                cat2_f.setChecked(false);
            }
        });

        cat2_f=findViewById(R.id.cat2_f);
        cat2_f.setOnClickListener(view -> {
            if(cat2_t.isChecked()){
                cat2_t.setChecked(false);
            }
        });

        cat3_t=findViewById(R.id.cat3_t);
        cat3_t.setOnClickListener(view -> {
            if(cat3_f.isChecked()){
                cat3_f.setChecked(false);
            }
        });

        cat3_f=findViewById(R.id.cat3_f);
        cat3_f.setOnClickListener(view -> {
            if(cat3_t.isChecked()){
                cat3_t.setChecked(false);
            }
        });

        cat4_t=findViewById(R.id.cat4_t);
        cat4_t.setOnClickListener(view -> {
            if(cat4_f.isChecked()){
                cat4_f.setChecked(false);
            }
        });

        cat4_f=findViewById(R.id.cat4_f);
        cat4_f.setOnClickListener(view -> {
            if(cat4_t.isChecked()){
                cat4_t.setChecked(false);
            }
        });

        cat5_t=findViewById(R.id.cat5_t);
        cat5_t.setOnClickListener(view -> {
            if(cat5_f.isChecked()){
                cat5_f.setChecked(false);
            }
        });

        cat5_f=findViewById(R.id.cat5_f);
        cat5_f.setOnClickListener(view -> {
            if(cat5_t.isChecked()){
                cat5_t.setChecked(false);
            }
        });

        cat6_t=findViewById(R.id.cat6_t);
        cat6_t.setOnClickListener(view -> {
            if(cat6_f.isChecked()){
                cat6_f.setChecked(false);
            }
        });

        cat6_f=findViewById(R.id.cat6_f);
        cat6_f.setOnClickListener(view -> {
            if(cat6_t.isChecked()){
                cat6_t.setChecked(false);
            }
        });

        evalKo=findViewById(R.id.evaluate_an);
        evalKo.setOnClickListener(view -> {
            cat1_t.setChecked(false);
            cat1_f.setChecked(false);
            cat2_t.setChecked(false);
            cat2_f.setChecked(false);
            cat3_t.setChecked(false);
            cat3_f.setChecked(false);
            cat4_t.setChecked(false);
            cat4_f.setChecked(false);
            cat5_t.setChecked(false);
            cat5_f.setChecked(false);
            cat6_t.setChecked(false);
            cat6_f.setChecked(false);
            eval.setVisibility(View.INVISIBLE);
        });

        evalOk=findViewById(R.id.evaluate_ok);
        evalOk.setOnClickListener(view -> {
            //todo
            eval.setVisibility(View.INVISIBLE);
        });
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    String phoneNumber= phone.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phoneNumber));
                    startActivity(intent);
                }
            });


}
