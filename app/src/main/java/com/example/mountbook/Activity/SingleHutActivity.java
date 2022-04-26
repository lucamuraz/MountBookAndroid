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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import com.example.mountbook.AppManager;
import com.example.mountbook.HttpHandler;
import com.example.mountbook.Model.Reservation;
import com.example.mountbook.Model.Shelter;
import com.example.mountbook.R;
import com.example.mountbook.SaveSharedPreferences;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SingleHutActivity extends AppCompatActivity {

    private TextView hut_email;
    private TextView hut_service;
    private TextView hut_phone;
    private final Context ctx=this;
    private CardView layer;
    private EditText date;
    private Spinner hosts;
    private TextView datePr;
    private TextView nHostPr;
    private TextView alert;
    private Button edit;
    private Long startDate;
    private Button ok;
    private Long endDate;
    private String url;
    private List<Pair<String,Object>> json= new ArrayList<>();
    private Reservation res;
    private boolean post=true;
    private Shelter shelter;
    private String jsonStr="";
    private String start;
    private String end;
    private ImageView cover;
    private TextView price;
    private Date date1;
    private Date date2;
    private TextView priceTxt;
    int guest;
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent1=getIntent();
        Bundle extras = intent1.getExtras();
        int tmp = extras.getInt("type");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlehut);
        shelter = AppManager.getInstance().getSingleHut();

        CardView confirm = findViewById(R.id.confirm);
        CardView book = findViewById(R.id.card_button);
        Button confirmBook = findViewById(R.id.confirm_book);
        ok=findViewById(R.id.ok_book);
        layer=findViewById(R.id.layer);
        date=findViewById(R.id.editTextDateB);
        hosts=findViewById(R.id.spinner2B);
        datePr=findViewById(R.id.dataPr);
        nHostPr=findViewById(R.id.hostPr);
        edit=findViewById(R.id.edit_book);
        alert=findViewById(R.id.alert);
        ImageView exit = findViewById(R.id.exit);
        price=findViewById(R.id.totPrice);
        priceTxt=findViewById(R.id.priceTxt2);

        TextView hut_name = findViewById(R.id.hut_name);
        hut_name.setText(shelter.getName());
        TextView hut_info = findViewById(R.id.hut_info);
        hut_info.setText(shelter.getDescription());
        TextView hut_desc = findViewById(R.id.hut_desc);
        hut_desc.setText(shelter.getDescr());
        hut_service = findViewById(R.id.hut_service);
        hut_service.setText(shelter.getServices());
        hut_email=findViewById(R.id.hut_contact1);
        hut_email.setText(shelter.getEmail());
        hut_phone=findViewById(R.id.hut_contact2);
        hut_phone.setText(shelter.getTelephoneNumber());

        cover=findViewById(R.id.imageView2);
        cover.setImageResource(shelter.getImage());

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
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(9.5);
        GeoPoint startPoint = new GeoPoint(shelter.getLatitude(), shelter.getLongitude());
        mapController.setCenter(startPoint);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        ArrayList<OverlayItem> items = new ArrayList<>();
        items.add(new OverlayItem(shelter.getName(), shelter.getDescr(), new GeoPoint(shelter.getLatitude(), shelter.getLongitude())));
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

        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder
                .dateRangePicker();
        materialDateBuilder.setTitleText("Seleziona le date");
        materialDateBuilder.setTheme(R.style.MaterialCalendarTheme);

        final MaterialDatePicker<Pair<Long, Long>> materialDatePicker = materialDateBuilder.build();

        date.setOnClickListener(
                v -> materialDatePicker.show(getSupportFragmentManager(),
                        "MATERIAL_DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener((MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>) selection -> {
            date.setText(materialDatePicker.getHeaderText());
            date1=new Date(selection.first);
            date2=new Date(selection.second);
            if(date1.before(shelter.getOpen())||date2.after(shelter.getClose())||date1.before(new Date())){
                alert.setVisibility(View.VISIBLE);
            }else{
                alert.setVisibility(View.INVISIBLE);
//                }
            }
        });

        List<String> nrHost = new ArrayList<>(Arrays.asList("1 ospite", "2 ospiti", "3 ospiti", "4 ospiti", "5 ospiti",
                "6 ospiti", "7 ospiti", "8 ospiti", "9 ospiti", "10 ospiti"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_spinner_item, nrHost);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hosts.setAdapter(adapter);


        if(tmp==0){ //caso del rifugio in cui non ho ancora scleto la data e gli ospiti
            datePr.setVisibility(View.INVISIBLE);
            nHostPr.setVisibility(View.INVISIBLE);
            date.setVisibility(View.VISIBLE);
            hosts.setVisibility(View.VISIBLE);
            edit.setVisibility(View.INVISIBLE);
            price.setVisibility(View.INVISIBLE);
            priceTxt.setVisibility(View.INVISIBLE);
            confirmBook.setVisibility(View.INVISIBLE);
        }else{
            datePr.setText(extras.getString("dateStart")+" / "+extras.getString("dateEnd"));
            nHostPr.setText(String.valueOf(extras.getInt("guest")));
            LocalDate d=LocalDate.parse(extras.getString("dateStart"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate d2=LocalDate.parse(extras.getString("dateEnd"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            try {
                date1=simpleDateFormat1.parse(extras.getString("dateStart"));
                date2=simpleDateFormat1.parse(extras.getString("dateEnd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int day= Period.between(d,d2).getDays();
            int pr=shelter.getPrice()*extras.getInt("guest")*day;
            price.setText(String.valueOf(pr)+" €");
            price.setVisibility(View.VISIBLE);
            priceTxt.setVisibility(View.VISIBLE);
            ok.setVisibility(View.INVISIBLE);
        }

        edit.setOnClickListener(view -> {
            datePr.setVisibility(View.INVISIBLE);
            nHostPr.setVisibility(View.INVISIBLE);
            date.setVisibility(View.VISIBLE);
            hosts.setVisibility(View.VISIBLE);
            edit.setVisibility(View.INVISIBLE);
            confirmBook.setVisibility(View.INVISIBLE);
            ok.setVisibility(View.VISIBLE);
            price.setVisibility(View.INVISIBLE);
            priceTxt.setVisibility(View.INVISIBLE);
        });

        exit.setOnClickListener(view -> {
            layer.setVisibility(View.INVISIBLE);
            if(tmp==0){
                datePr.setVisibility(View.INVISIBLE);
                nHostPr.setVisibility(View.INVISIBLE);
                date.setVisibility(View.VISIBLE);
                hosts.setVisibility(View.VISIBLE);
                edit.setVisibility(View.INVISIBLE);
                price.setVisibility(View.INVISIBLE);
                priceTxt.setVisibility(View.INVISIBLE);
                confirmBook.setVisibility(View.INVISIBLE);
            }else{
                datePr.setVisibility(View.VISIBLE);
                nHostPr.setVisibility(View.VISIBLE);
                date.setVisibility(View.INVISIBLE);
                hosts.setVisibility(View.INVISIBLE);
                edit.setVisibility(View.VISIBLE);
                datePr.setText(extras.getString("dateStart")+" - "+extras.getString("dateEnd"));
                nHostPr.setText(String.valueOf(extras.getInt("guest")));
                LocalDate d=LocalDate.parse(extras.getString("dateStart"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate d2=LocalDate.parse(extras.getString("dateEnd"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                try {
                    date1=simpleDateFormat1.parse(extras.getString("dateStart"));
                    date2=simpleDateFormat1.parse(extras.getString("dateEnd"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int day= Period.between(d,d2).getDays();
                int pr=shelter.getPrice()*extras.getInt("guest")*day;
                price.setText(String.valueOf(pr));
                price.setVisibility(View.VISIBLE);
                priceTxt.setVisibility(View.VISIBLE);
                ok.setVisibility(View.INVISIBLE);
            }
            alert.setVisibility(View.INVISIBLE);
        });

        url="http://10.0.2.2:8081/api/v1/service/getServicesForShelter?shelterId="+shelter.getId();
        post=false;
        new FetchDataTask().execute(url);

        confirmBook.setOnClickListener(view -> {
            url="http://10.0.2.2:8081/api/v1/reservation/doReservation"; //todo mettere link per prenotare
            post=true;

            //Date date2=new Date(startDate);
            start=simpleDateFormat.format(date1);
            //Date date1=new Date(endDate);
            end=simpleDateFormat.format(date2);
            guest=Integer.parseInt(hosts.getSelectedItem().toString().substring(0,1));
            json.clear();
            json.add(new Pair<>("user",SaveSharedPreferences.getPrefUserId(ctx)));
            json.add(new Pair<>("guests",guest));
            json.add(new Pair<>("firstDay",start));
            json.add(new Pair<>("lastDay",end));
            json.add(new Pair<>("shelter",shelter.getId()));

            new FetchDataTask().execute(url);
        });

        ok.setOnClickListener(view -> {
            if(date1!=null && date2!=null && hosts.getSelectedItem()!=null){
                alert.setVisibility(View.INVISIBLE);
                start=simpleDateFormat.format(date1);
                end=simpleDateFormat.format(date2);
                LocalDate d=LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate d2=LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                int day= Period.between(d,d2).getDays();
                int pr=shelter.getPrice()*Integer.parseInt(hosts.getSelectedItem().toString().substring(0,1))*day;
                price.setText(String.valueOf(pr)+" €");
                price.setVisibility(View.VISIBLE);
                confirmBook.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);
                ok.setVisibility(View.INVISIBLE);
            }else{
                alert.setVisibility(View.VISIBLE);
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

    private class FetchDataTask extends AsyncTask<String, Void, String> {

        private static final String TAG = "con";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            Toast.makeText(ctx,"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            if(post){
                jsonStr = sh.makePostCall(url,json);
            }else{
                jsonStr = sh.makeGetCall(url);
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
            //parse the JSON data and then display
            //parseJSON(dataFetched);
            if(post){
                try{
                    JSONArray jsonMainNode = new JSONArray(jsonStr);
                    int jsonArrLength = jsonMainNode.length();
                    for(int i=0; i < jsonArrLength; i++) {
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                        int id= jsonChildNode.getInt("id");
                        Date st=simpleDateFormat.parse(jsonChildNode.getString("firstDay"));
                        Date en=simpleDateFormat.parse(jsonChildNode.getString("lastDay"));
                        res=new Reservation(shelter.getName(),shelter.getId(), 0,st,en,guest,id, SaveSharedPreferences.getUserName(ctx));
                    }
                }catch (Exception e){
                    Log.i("App", "Error parsing data" +e.getMessage());
                }
                AppManager.getInstance().getReservationList().add(res);
                Intent i=new Intent(ctx, MainActivity.class);
                i.putExtra("redirect", 2);
                startActivity(i);
            }else{
                try{
                    JSONArray jsonMainNode = new JSONArray(jsonStr);
                    int jsonArrLength = jsonMainNode.length();
                    for(int i=0; i < jsonArrLength; i++) {
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                        //todo ricostruire shelete e aggiungere alla lista locale
                        boolean [] res={true,true,true};
                        res[0] = jsonChildNode.getBoolean("wifi");
                        res[1] = jsonChildNode.getBoolean("equipment");
                        res[2] = jsonChildNode.getBoolean("car");
                        shelter.setServices(res);
                    }
                }catch (Exception e){
                    Log.i("App", "Error parsing data" +e.getMessage());
                }
                hut_service.setText(shelter.getServices());
            }
        }
    }
}
