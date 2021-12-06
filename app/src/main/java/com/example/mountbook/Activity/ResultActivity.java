package com.example.mountbook.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mountbook.Adapter.HutAdapter;
import com.example.mountbook.Model.Hut;
import com.example.mountbook.R;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    List<Hut> resultList = new ArrayList<Hut>();
    RecyclerView recyclerView;
    Context ctx=this;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        recyclerView=findViewById(R.id.hut_list);
        resultList.add(new Hut("Rifugio1", "info1"));
        resultList.add(new Hut("Rifugio2", "info2"));
        if(!resultList.isEmpty()){
            HutAdapter adapter = new HutAdapter(resultList); // la visulizzo con l'adpter
            TextView text = findViewById(R.id.testo1);
            text.setVisibility(View.INVISIBLE);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        }else{
            TextView text = findViewById(R.id.testo1);
            text.setVisibility(View.VISIBLE);
        }
        toolbar=findViewById(R.id.toolbar1);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryVariant, this.getTheme()));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Rifugi");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ctx, MainActivity.class);
        i.putExtra("redirect", 0);
        startActivity(i);
        finish();
    }
}
