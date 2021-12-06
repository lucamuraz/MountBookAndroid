package com.example.mountbook.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mountbook.Adapter.ReservationAdapter;
import com.example.mountbook.Model.Reservation;
import com.example.mountbook.R;

import java.util.ArrayList;
import java.util.List;

public class BookedFragment extends Fragment {

    List<Reservation> reservationList = new ArrayList<Reservation>();
    RecyclerView recyclerView;

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
        reservationList.add(new Reservation("Rifugio1", "data1"));
        reservationList.add(new Reservation("Rifugio2", "data2"));
        if(!reservationList.isEmpty()){
            ReservationAdapter adapter = new ReservationAdapter(reservationList); // la visulizzo con l'adpter
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
        return rootView;
    }
}
