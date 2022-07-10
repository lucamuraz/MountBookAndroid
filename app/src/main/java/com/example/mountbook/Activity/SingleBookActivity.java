package com.example.mountbook.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import com.example.mountbook.AppManager;
import com.example.mountbook.HttpHandler;
import com.example.mountbook.Model.Reservation;
import com.example.mountbook.Model.Structure;
import com.example.mountbook.R;
import com.example.mountbook.SaveSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

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
    Button delete;
    boolean [] rating={false,false,false,false,false,false};
    private String url;
    List<Pair<String,Object>> json= new ArrayList<>();
    private boolean del;
    private Reservation reservation;
    private boolean init=true;
    Structure structure;
    String uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        ctx=this;
        reservation=AppManager.getInstance().getReservation();
        url="/api/v1/shelter/findById?shId="+reservation.getStructureId();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlebook);
        hut_name=findViewById(R.id.prenot_rif);
//        hut_name.setText(shelter.getName());

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
        email.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email.getText().toString() });
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(Intent.createChooser(intent, ""));
        });

        phone=findViewById(R.id.prenot_contact2);
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
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
        });

        eval=findViewById(R.id.layer1);

        evaluate=findViewById(R.id.evaluate);
        evaluate.setOnClickListener(view -> {
            eval.setVisibility(View.VISIBLE);
            evaluate.setVisibility(View.INVISIBLE);
        });

        cat1_t=findViewById(R.id.cat1_t);
        cat1_t.setOnClickListener(view -> {
            if(cat1_f.isChecked()){
                cat1_f.setChecked(false);
            }
            rating[0]=true;
        });

        cat1_f=findViewById(R.id.cat1_f);
        cat1_f.setOnClickListener(view -> {
            if(cat1_t.isChecked()){
                cat1_t.setChecked(false);
            }
            rating[0]=false;
        });

        cat2_t=findViewById(R.id.cat2_t);
        cat2_t.setOnClickListener(view -> {
            if(cat2_f.isChecked()){
                cat2_f.setChecked(false);
            }
            rating[1]=true;
        });

        cat2_f=findViewById(R.id.cat2_f);
        cat2_f.setOnClickListener(view -> {
            if(cat2_t.isChecked()){
                cat2_t.setChecked(false);
            }
            rating[1]=false;
        });

        cat3_t=findViewById(R.id.cat3_t);
        cat3_t.setOnClickListener(view -> {
            if(cat3_f.isChecked()){
                cat3_f.setChecked(false);
            }
            rating[2]=true;
        });

        cat3_f=findViewById(R.id.cat3_f);
        cat3_f.setOnClickListener(view -> {
            if(cat3_t.isChecked()){
                cat3_t.setChecked(false);
            }
            rating[2]=false;
        });

        cat4_t=findViewById(R.id.cat4_t);
        cat4_t.setOnClickListener(view -> {
            if(cat4_f.isChecked()){
                cat4_f.setChecked(false);
            }
            rating[3]=true;
        });

        cat4_f=findViewById(R.id.cat4_f);
        cat4_f.setOnClickListener(view -> {
            if(cat4_t.isChecked()){
                cat4_t.setChecked(false);
            }
            rating[3]=false;
        });

        cat5_t=findViewById(R.id.cat5_t);
        cat5_t.setOnClickListener(view -> {
            if(cat5_f.isChecked()){
                cat5_f.setChecked(false);
            }
            rating[4]=true;
        });

        cat5_f=findViewById(R.id.cat5_f);
        cat5_f.setOnClickListener(view -> {
            if(cat5_t.isChecked()){
                cat5_t.setChecked(false);
            }
            rating[4]=false;
        });

        cat6_t=findViewById(R.id.cat6_t);
        cat6_t.setOnClickListener(view -> {
            if(cat6_f.isChecked()){
                cat6_f.setChecked(false);
            }
            rating[5]=true;
        });

        cat6_f=findViewById(R.id.cat6_f);
        cat6_f.setOnClickListener(view -> {
            if(cat6_t.isChecked()){
                cat6_t.setChecked(false);
            }
            rating[5]=false;
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
            evaluate.setVisibility(View.VISIBLE);
        });

        evalOk=findViewById(R.id.evaluate_ok);
        evalOk.setOnClickListener(view -> {
            url="/api/v1/comment/doComment";
            del=false;
            init=false;
            json.clear();
            json.add(new Pair<>("structure", reservation.getStructureId()));
            json.add(new Pair<>("user", SaveSharedPreferences.getPrefUserId(ctx)));
            json.add(new Pair<>("service",rating[0]));
            json.add(new Pair<>("clear",rating[1]));
            json.add(new Pair<>("ospitality",rating[2]));
            json.add(new Pair<>("food",rating[3]));
            json.add(new Pair<>("location",rating[4]));

            new FetchDataTask().execute(url);
        });

        delete=findViewById(R.id.book_delete);
        delete.setOnClickListener(view -> {
            url="/api/v1/reservation/deleteReservation";
            del=true;
            init=false;
            json.clear();
            json.add(new Pair<>("userId", SaveSharedPreferences.getPrefUserId(ctx)));
            json.add(new Pair<>("reservationId",reservation.getId()));
            new FetchDataTask().execute(url);
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

    private void loadUi(){
        hut_name.setText(structure.getName());
        phone.setText(structure.getTelephoneNumber());
        email.setText(structure.getEmail());
        hut_name.setText(structure.getName());
        uri = "http://maps.google.com/maps?q=loc:" + structure.getLatitude() + "," + structure.getLongitude();
    }

    private class FetchDataTask extends AsyncTask<String, Void, String> {

        private static final String TAG = "con";
        private String jsonStr;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            Toast.makeText(ctx,"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            if(init){
                jsonStr = sh.makeGetCall(url);
            }else{
                jsonStr = sh.makePostCall(url,json);
            }
            Log.e(TAG, "Response from url: " + jsonStr);
            if(jsonStr == null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ctx,
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(String dataFetched) {
            if(init){
                try{
                    JSONArray jsonMainNode = new JSONArray(jsonStr);
                    int jsonArrLength = jsonMainNode.length();
                    DateFormat df1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                    for(int i=0; i < jsonArrLength; i++) {
                        JSONObject js = jsonMainNode.getJSONObject(i);
                        //todo ricostruire shelter e aggiungere alla lista locale
                        int id= js.getInt("id");
                        String name = js.getString("name");
                        String address = js.getString("address");
                        String openStr= js.getString("open");
                        Date open= df1.parse(openStr);
                        String closeStr= js.getString("close");
                        Date close= df1.parse(closeStr);
                        int maxNumBed = js.getInt("maxNumBed");
                        float altitude = BigDecimal.valueOf(js.getDouble("altitude")).floatValue();
                        double longitude = js.getDouble("longitude");
                        double latitude =js.getDouble("latitude");
                        String description = js.getString("description");
                        int type=js.getInt("type");
                        String phone = "";
                        String webSite = "";
                        String email = "";
                        int price = 0;
                        if(type==0){
                            phone = String.valueOf(js.getLong("telephoneNumber"));
                            webSite = js.getString("webSite");
                            email = js.getString("email");
                            price = js.getInt("price");
                        }
                        int r=new Random().nextInt(3);
                        int image;
                        switch (r) {
                            case 0:
                                image = R.drawable.hut1;
                                break;
                            case 1:
                                image = R.drawable.hut2;
                                break;
                            case 2:
                                image = R.drawable.hut3;
                                break;
                            default:
                                image = R.drawable.hut1;
                                break;
                        }
                        structure = new Structure(id,name,address,open,close,maxNumBed,altitude,latitude,longitude,phone,webSite,email,description,image,34,6,"",price,type);
                    }
                    loadUi();
                    init=false;
                }catch(Exception e){
                        Log.i("App", "Error parsing data" +e.getMessage());
                }
            }else if(del){
                AppManager.getInstance().getReservationList().remove(reservation);
                Intent i = new Intent(ctx, MainActivity.class);
                i.putExtra("redirect", 2);
                startActivity(i);
                finish();
            }else{
                String toastMessage = "Valutazione effettuata!";
                Toast mToast = Toast.makeText(ctx, toastMessage, Toast.LENGTH_LONG);
                mToast.show();
                eval.setVisibility(View.INVISIBLE);
            }
        }

    }

}
