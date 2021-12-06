package com.example.mountbook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mountbook.Model.Reservation;

import java.util.List;
import com.example.mountbook.R;

public class ReservationAdapter extends
        RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private List<Reservation> reservationList;
    private int height;
    private int minHeight;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.row_layout, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position); // prense il primo elemeto della lista.
        // prendo gli elementi dalla classe viewholder, dal row_layout e faccio le set.
        TextView textView = holder.titolo;
        textView.setText(reservation.getTitle());
        TextView textView1 = holder.informazioni;
        textView1.setText(reservation.getInfo());
//        ImageView imageView = holder.remainder_icon;
//        imageView.setImageResource(R.drawable.ic_bell1_foreground);
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titolo;
        public TextView informazioni;
        public ImageView book_icon;
        public View view;


        public ViewHolder(View v) {
            super(v);
            view=v;
            titolo = v.findViewById(R.id.activity_firstLine);
            informazioni = v.findViewById(R.id.activity_secondLine);
            book_icon = v.findViewById(R.id.book_icon);
        }
    }

    public ReservationAdapter(List<Reservation> reservationList){
        this.reservationList = reservationList;
    }

}
