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
import com.example.mountbook.AppManager;
import com.example.mountbook.Model.Structure;
import com.example.mountbook.R;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements  HutAdapter.ItemClickListener{

    private List<Structure> resultList=new ArrayList<>();
    private HutAdapter.ItemClickListener itemClickListener=this;
    RecyclerView recyclerView;
    Context ctx=this;
    Toolbar toolbar;
    private int tmp;
    private Bundle extras;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        recyclerView=findViewById(R.id.hut_list);

        resultList=AppManager.getInstance().getHutListResult();
        if(!resultList.isEmpty()){
            HutAdapter adapter = new HutAdapter(resultList, itemClickListener); // la visulizzo con l'adpter
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
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_24px);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryVariant, this.getTheme()));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Rifugi");
        toolbar.setNavigationOnClickListener(view -> finish());

        Intent intent=getIntent();
        extras = intent.getExtras();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) { //todo
        Structure structure =resultList.get(clickedItemIndex);
        AppManager.getInstance().setSingleHut(structure);
        Intent i = new Intent(ctx, SingleHutActivity.class);
        i.putExtras(extras);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
