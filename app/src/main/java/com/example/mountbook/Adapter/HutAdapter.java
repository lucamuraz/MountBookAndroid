package com.example.mountbook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mountbook.Model.Hut;
import com.example.mountbook.R;

import java.util.List;

public class HutAdapter extends
        RecyclerView.Adapter<HutAdapter.ViewHolder> {

    private List<Hut> hutList;

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
        Hut hut = hutList.get(position); // prense il primo elemeto della lista.
        // prendo gli elementi dalla classe viewholder, dal row_layout e faccio le set.
        TextView textView = holder.titolo;
        textView.setText(hut.getTitle());
        TextView textView1 = holder.informazioni;
        textView1.setText(hut.getInfo());
//        ImageView imageView = holder.remainder_icon;
//        imageView.setImageResource(R.drawable.ic_bell1_foreground);
    }

    @Override
    public int getItemCount() {
        return hutList.size();
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

    public HutAdapter(List<Hut> hutList){
        this.hutList = hutList;
    }

}
