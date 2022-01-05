package com.example.mountbook.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mountbook.Activity.SingleBookActivity;
import com.example.mountbook.Activity.SingleHutActivity;
import com.example.mountbook.Adapter.ReservationAdapter;
import com.example.mountbook.AppManager;
import com.example.mountbook.Model.Reservation;
import com.example.mountbook.R;

import java.util.ArrayList;
import java.util.List;

public class BookedFragment extends Fragment implements  ReservationAdapter.ItemClickListener{

    List<Reservation> reservationList = new ArrayList<Reservation>();
    private ReservationAdapter.ItemClickListener itemClickListener=this;
    RecyclerView recyclerView;
    private Context ctx;

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
        final View rootView = inflater.inflate(R.layout.fragment_booked, container, false);
        reservationList=AppManager.getInstance().getReservationList();
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

        ctx=rootView.getContext();
        return rootView;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Reservation reservation=reservationList.get(clickedItemIndex);
        AppManager.getInstance().setReservation(reservation);
        Intent i = new Intent(ctx, SingleBookActivity.class);
        startActivity(i);
    }
}
