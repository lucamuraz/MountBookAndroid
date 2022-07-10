package com.example.mountbook.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mountbook.Activity.SingleBookActivity;
import com.example.mountbook.Adapter.ReservationAdapter;
import com.example.mountbook.AppManager;
import com.example.mountbook.HttpHandler;
import com.example.mountbook.Model.Reservation;
import com.example.mountbook.R;
import com.example.mountbook.SaveSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookedFragment extends Fragment implements  ReservationAdapter.ItemClickListener{

    List<Reservation> reservationList = new ArrayList<Reservation>();
    private ReservationAdapter.ItemClickListener itemClickListener=this;
    RecyclerView recyclerView;
    private Context ctx;
    private String url;
    private String jsonStr;
    private View rootView;

    public BookedFragment() {
        // Required empty public constructor
    }
    public static BookedFragment newInstance() {
        return new BookedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_booked, container, false);
        ctx=rootView.getContext();
        String userId=SaveSharedPreferences.getPrefUserId(ctx);
        url="/api/v1/reservation/getReservationForUser?userId="+userId; //todo mettere link per recuperare tutti le prenotazioni
        new FetchDataTask().execute(url);
//        reservationList=AppManager.getInstance().getReservationList();
//        loadUi();
        return rootView;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Reservation reservation=reservationList.get(clickedItemIndex);
        AppManager.getInstance().setReservation(reservation);
        Intent i = new Intent(ctx, SingleBookActivity.class);
        startActivity(i);
    }

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
            jsonStr = sh.makeGetCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
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
            try{
                if(jsonStr!=null){
                    JSONArray jsonMainNode = new JSONArray(jsonStr);
                    int jsonArrLength = jsonMainNode.length();
                    DateFormat df1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                    for(int i=0; i < jsonArrLength; i++) {
                        JSONObject js = jsonMainNode.getJSONObject(i);
                        //todo ricostruire reservations e aggiungere alla lista locale
                        int id = js.getInt("reservationId");
                        int guest = js.getInt("guest");
                        String openStr= js.getString("firstDay");
                        Date start= df1.parse(openStr);
                        String closeStr= js.getString("lastDay");
                        Date end= df1.parse(closeStr);
                        String name= js.getString("name");
                        long shId=js.getLong("structureId");
                        String user= SaveSharedPreferences.getUserName(ctx);
                        reservationList.add(new Reservation(name,shId,start,end,guest,id,user));
                    }
                }
                AppManager.getInstance().setReservationList(reservationList);
                loadUi();
            }catch(Exception e){
                Log.i("App", "Error parsing data" +e.getMessage());

            }
        }

    }

    private void loadUi(){
        if(reservationList!= null && !reservationList.isEmpty()){
            ReservationAdapter adapter = new ReservationAdapter(reservationList, itemClickListener); // la visulizzo con l'adpter
            TextView text = rootView.findViewById(R.id.testo);
            text.setVisibility(View.INVISIBLE);
            recyclerView = rootView.findViewById(R.id.book_list);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        }else{
            TextView text = rootView.findViewById(R.id.testo);
            text.setVisibility(View.VISIBLE);
        }
    }
}
