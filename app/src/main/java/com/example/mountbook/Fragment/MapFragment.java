package com.example.mountbook.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mountbook.Activity.SingleHutActivity;
import com.example.mountbook.AppManager;
import com.example.mountbook.HttpHandler;
import com.example.mountbook.Model.Structure;
import com.example.mountbook.R;

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
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MapFragment extends Fragment {

    private MapView map = null;
    private final Context ctx= AppManager.getInstance().getCtx();
    CardView card;
    TextView cardTitle;
    TextView cardPos;
    TextView cardNeg;
    private String url;
    private List<Structure> structureList;
    private String jsonString;
    private IMapController imapController;
    private ImageView imageView;

    public MapFragment() {
        // Required empty public constructor
    }
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        card=rootView.findViewById(R.id.cardViewMap);
        cardTitle=rootView.findViewById(R.id.cardTitle2);
        cardNeg=rootView.findViewById(R.id.cardNeg);
        cardPos=rootView.findViewById(R.id.cardPos);
        map=rootView.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        imageView=rootView.findViewById(R.id.imageMap);

        map.setMultiTouchControls(true);
        imapController = map.getController();
        imapController.setZoom(10.0);
        GeoPoint startPoint = new GeoPoint(45.066667, 7.7);
        imapController.setCenter(startPoint);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        card.setOnClickListener(view -> {
            Intent i = new Intent(ctx, SingleHutActivity.class);
            i.putExtra("type",0);
            startActivity(i);
        });

        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(ctx),map);
        mLocationOverlay.enableMyLocation();
        map.getOverlays().add(mLocationOverlay);


        url="/api/v1/find/findAllStructure"; //todo mettere link per recuperare tutti i rifugi
        new FetchDataTask().execute(url);



//        loadMap();


        return rootView;
    }

    private void loadMap(){
        GeoPoint startPoint = centerMap();
        imapController.setCenter(startPoint);
        //your items

        ArrayList<OverlayItem> items = new ArrayList<>();
        //todo prendere lista di tutti i rifugi e estrarre info necessarie oer creare overlayItem

        for (Structure structure : structureList) {
            String label= structure.getAltitude()+" mt.";
            items.add(new OverlayItem(structure.getName(), label, new GeoPoint(structure.getLatitude(), structure.getLongitude())));
        }

//the overlay
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //do something
                        AppManager.getInstance().setSingleHut(structureList.get(index));
                        cardTitle.setText(AppManager.getInstance().getSingleHut().getName());
                        cardNeg.setText(String.valueOf(AppManager.getInstance().getSingleHut().getNeg()));
                        cardPos.setText(String.valueOf(AppManager.getInstance().getSingleHut().getPos()));
                        imageView.setImageResource(AppManager.getInstance().getSingleHut().getImage());
                        card.setVisibility(View.VISIBLE);
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {

                        return false;
                    }
                }, ctx);
        mOverlay.setFocusItemsOnTap(true);

        map.getOverlays().add(mOverlay);

//        card.setOnClickListener(view -> {
//            Intent i = new Intent(ctx, SingleHutActivity.class);
//            i.putExtra("type",0);
//            startActivity(i);
//        });
//
//        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(ctx),map);
//        mLocationOverlay.enableMyLocation();
//        map.getOverlays().add(mLocationOverlay);
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
            jsonString = sh.makeGetCall(url);
            Log.e(TAG, "Response from url: " + jsonString);

            if(jsonString == null){
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
            structureList =new ArrayList<>();
            try{
                JSONArray jsonMainNode = new JSONArray(jsonString);
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
                    double latitude = js.getDouble("latitude");
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
                    switch (r){
                        case 0:
                            image=R.drawable.hut1;
                            break;
                        case 1:
                            image=R.drawable.hut2;
                            break;
                        case 2:
                            image=R.drawable.hut3;
                            break;
                        default:
                            image=R.drawable.hut1;
                            break;
                    }
                    structureList.add(new Structure(id,name,address,open,close,maxNumBed,altitude,latitude,longitude,phone,webSite,email,description,image,34,6,"pernottamento",price,type));
                }
                loadMap();
            }catch(Exception e){
                Log.i("App", "Error parsing data" +e.getMessage());
            }
        }
    }

    private GeoPoint centerMap(){
        double sumLat=0;
        double sumLon=0;
        for (Structure sh: structureList) {
            sumLon+=sh.getLongitude();
            sumLat+=sh.getLatitude();
        }
        return  new GeoPoint((sumLat/ structureList.size()),(sumLon/ structureList.size()));
    }
}