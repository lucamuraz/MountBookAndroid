package com.example.mountbook.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mountbook.Activity.SingleHutActivity;
import com.example.mountbook.AppManager;
import com.example.mountbook.Model.Shelter;
import com.example.mountbook.R;

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

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {

    private MapView map = null;
    private final Context ctx= AppManager.getInstance().getCtx();
    CardView card;
    TextView cardTitle;
    TextView cardPos;
    TextView cardNeg;

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
        card.setVisibility(View.INVISIBLE);
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

        loadMap();

//        if (
//        ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED &&
//                 ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED
//                ){
//            loadMap();
//        }else{
//            requestPermissionLauncher.launch(
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            requestPermissionLauncher.launch(
//                    Manifest.permission.ACCESS_FINE_LOCATION);
//            requestPermissionLauncher.launch(
//                    Manifest.permission.ACCESS_COARSE_LOCATION);
//        }
//
        return rootView;
    }

//    private final ActivityResultLauncher<String> requestPermissionLauncher =
//            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
//                if (isGranted) {
//                    loadMap();
//                }
//            });

    private void loadMap(){
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(10.0);
        GeoPoint startPoint = new GeoPoint(45.191018, 7.074824);
        mapController.setCenter(startPoint);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        //your items

        ArrayList<OverlayItem> items = new ArrayList<>();
        //todo prendere lista di tutti i rifugi e estrarre info necessarie oer creare overlayItem
        List<Shelter> shelterList =AppManager.getInstance().getHutListAll();
        for (Shelter shelter : shelterList) {
            items.add(new OverlayItem(shelter.getTitle(), shelter.getDescr(), new GeoPoint(shelter.getLat(), shelter.getLon())));
        }

//the overlay
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //do something
                        AppManager.getInstance().setSingleHut(shelterList.get(index));
                        cardTitle.setText(AppManager.getInstance().getSingleHut().getTitle());
                        cardNeg.setText(String.valueOf(AppManager.getInstance().getSingleHut().getNeg()));
                        cardPos.setText(String.valueOf(AppManager.getInstance().getSingleHut().getPos()));
                        card.setVisibility(View.VISIBLE);
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {

                        return false;
                    }
                }, ctx);

        map.getOverlays().add(mOverlay);

        card.setOnClickListener(view -> {
            Intent i = new Intent(ctx, SingleHutActivity.class);
            startActivity(i);
        });

        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(ctx),map);
        mLocationOverlay.enableMyLocation();
        map.getOverlays().add(mLocationOverlay);
    }
}