package com.example.mountbook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mountbook.Model.Structure;
import com.example.mountbook.R;

import java.util.List;

public class HutAdapter extends
        RecyclerView.Adapter<HutAdapter.ViewHolder> {

    private List<Structure> structureList;
    final private ItemClickListener onClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.row_layout1, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Structure structure = structureList.get(position); // prense il primo elemeto della lista.
        // prendo gli elementi dalla classe viewholder, dal row_layout e faccio le set.
        TextView textView = holder.titolo;
        textView.setText(structure.getName());
        TextView textView1 = holder.pos;
        textView1.setText(String.valueOf(structure.getPos()));
        TextView textView2 = holder.neg;
        textView2.setText(String.valueOf(structure.getNeg()));
    }

    @Override
    public int getItemCount() {
        return structureList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        // each data item is just a string in this case
        public TextView titolo;
        public TextView pos;
        public TextView neg;
        public ImageView book_icon;
        public View view;


        public ViewHolder(View v) {
            super(v);
            view=v;
            titolo = v.findViewById(R.id.activity_firstLine);
            pos = v.findViewById(R.id.activity_secondLinePos);
            neg = v.findViewById(R.id.activity_secondLineNeg);
            book_icon = v.findViewById(R.id.book_icon);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }
    }

    public interface ItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public HutAdapter(List<Structure> structureList, ItemClickListener onClickListener){
        this.structureList = structureList;
        this.onClickListener = onClickListener;
    }

}
