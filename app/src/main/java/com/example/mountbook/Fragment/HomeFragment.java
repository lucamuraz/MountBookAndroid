package com.example.mountbook.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.example.mountbook.Activity.ResultActivity;
import com.example.mountbook.AppManager;
import com.example.mountbook.HttpHandler;
import com.example.mountbook.Model.Structure;
import com.example.mountbook.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.slider.RangeSlider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {

    Button buttonAll;
    Button submit;
    Spinner hostNr;
    EditText name;
    EditText date;
    List<String> hosts;
    Context ctx;
    String jsonFromServer;
    private String url;
    List<Pair<String,Object>> json= new ArrayList<>();
    List<Structure> results;
    EditText services;
    boolean postCall=true;
    private String[] serviceList= {"Wi-Fi", "Noleggio attrezzatura", "Raggiungibile in macchina"};
    private boolean checked[]= {false, false, false};
    private String jsonStr;
    int guest;
    private String startDate;
    private String endDate;
    private Date date1;
    private Date date2;
    private CheckBox type1;
    private CheckBox type2;
    private RangeSlider rangeSlider;
    private TextView price;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        buttonAll=rootView.findViewById(R.id.button_all);
        submit=rootView.findViewById(R.id.button_go);
        hostNr=rootView.findViewById(R.id.spinner2);
        name=rootView.findViewById(R.id.editTextName);
        date=rootView.findViewById(R.id.editTextDate);
        services=rootView.findViewById(R.id.editTextService);
        type1=rootView.findViewById(R.id.chrif);
        type2=rootView.findViewById(R.id.chbiv);
        rangeSlider=rootView.findViewById(R.id.price);
        price=rootView.findViewById(R.id.price2);

        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder
                .dateRangePicker();
        materialDateBuilder.setTitleText("Seleziona le date");
        materialDateBuilder.setTheme(R.style.MaterialCalendarTheme);

        final MaterialDatePicker<Pair<Long, Long>> materialDatePicker = materialDateBuilder.build();

        date.setOnClickListener(
                v -> materialDatePicker.show(requireActivity().getSupportFragmentManager(),
                        "MATERIAL_DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener((MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>) selection -> {
            date.setText(materialDatePicker.getHeaderText());
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            date1=new Date(selection.first);
            startDate=simpleDateFormat.format(date1);
            date2=new Date(selection.second);
            endDate=simpleDateFormat.format(date2);
        });

        hosts = new ArrayList<>(Arrays.asList("1 ospite","2 ospiti","3 ospiti","4 ospiti","5 ospiti",
                "6 ospiti","7 ospiti","8 ospiti","9 ospiti","10 ospiti"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, hosts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hostNr.setAdapter(adapter);
        hostNr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                guest=i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        services.setOnClickListener(view -> {
            // Set up the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle("Scegli i servizi che desideri");

            // Add a checkbox list
            builder.setMultiChoiceItems(serviceList, checked, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    // The user checked or unchecked a box
                    checked[which]=isChecked;
                }
            });

            // Add OK and Cancel buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int i=0;
                    for (boolean check : checked) {
                        if(check){
                            i++;
                        }
                    }
                    if(i==0){
                        services.setText("");
                    }else if(i==1){
                        services.setText("1 selezionato");
                    }else{
                        services.setText(i+" selezionati");
                    }
                }
            });
            builder.setNegativeButton("Cancel", null);

            // Create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });



        buttonAll.setOnClickListener(view -> {
            url="/api/v1/find/findAllStructure";
            postCall=false;
            new FetchDataTask().execute(url);
        });

        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                List<Float> values=slider.getValues();
                String txt=values.get(0).intValue()+" - "+values.get(1).intValue();
                price.setText(txt);
            }
        });

        submit.setOnClickListener(view -> {
            int typ;
            if(type1.isChecked()&&!type2.isChecked()){
                typ=1;
            }else if(!type1.isChecked()&&type2.isChecked()){
                typ=2;
            }else{
                typ=0;
            }

            List<Float> values=rangeSlider.getValues();

            url="/api/v1/find/findStructure"; //todo mettere link per ricerca rifugi
            postCall=true;
            json.clear();
            json.add(new Pair<>("type",typ));
            json.add(new Pair<>("minPrice",values.get(0).intValue()));
            json.add(new Pair<>("maxPrice",values.get(1).intValue()));
            json.add(new Pair<>("wifi",checked[0]));
            json.add(new Pair<>("equipment",checked[1]));
            json.add(new Pair<>("car",checked[2]));
            json.add(new Pair<>("guest",guest));
            json.add(new Pair<>("dateStart",startDate));
            json.add(new Pair<>("dateEnd",endDate));
            new FetchDataTask().execute(url);
//            Intent i = new Intent(requireActivity(), ResultActivity.class);
//            i.putExtra("type", 1);
//            startActivity(i);
        });

        ctx=rootView.getContext();

        return rootView;

    }

    private class FetchDataTask extends AsyncTask<String, Void, String> {

        private static final String TAG = "con";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();

            if(postCall){
//                jsonStr = sh.makeGetCallJson(url,json);
                jsonStr = sh.makePostCall(url,json);
            }else{
                jsonStr = sh.makeGetCall(url);
            }
            Log.e(TAG, "Response from url: " + jsonStr);
            jsonFromServer = jsonStr;
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String books = jsonObj.toString();
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());

                }
                return jsonFromServer;

            } else {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ctx,
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            if(jsonStr == null){
                requireActivity().runOnUiThread(new Runnable() {
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
            List<Structure> structureList =new ArrayList<>();
            try{
                JSONArray jsonMainNode1 = new JSONArray(jsonStr);
                int jsonArrLength = jsonMainNode1.length();
                JSONArray jsonMainNode;
                if(jsonArrLength==1){
                    jsonMainNode = new JSONArray(jsonStr).getJSONArray(0);
                }else{
                    jsonMainNode = new JSONArray(jsonStr);
                }
                jsonArrLength = jsonMainNode.length();
                DateFormat df1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                for(int i=0; i < jsonArrLength; i++) {
                    JSONObject js = jsonMainNode.getJSONObject(i);
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
                    structureList.add(new Structure(id,name,address,open,close,maxNumBed,altitude,latitude,longitude,phone,webSite,email,description,image,34,6,"",price,type));
                }
                AppManager.getInstance().setHutListResult(structureList);
                Intent i = new Intent(requireActivity(), ResultActivity.class);
                if(postCall){
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                    String startDate1=simpleDateFormat.format(date1);
                    String endDate1=simpleDateFormat.format(date2);
                    i.putExtra("type", 1);
                    i.putExtra("dateStart",startDate1);
                    i.putExtra("dateEnd",endDate1);
                    i.putExtra("guest",guest);
                }else{
                    i.putExtra("type", 0);
                }
                startActivity(i);
            }catch(Exception e){
                Log.i("App", "Error parsing data" +e.getMessage());
            }
        }
    }
}