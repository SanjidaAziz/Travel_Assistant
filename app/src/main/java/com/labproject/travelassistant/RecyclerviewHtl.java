package com.labproject.travelassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class RecyclerviewHtl extends AppCompatActivity {

    RecyclerView recviewH;
    HtlAdapter htlAdapter;
    String selectedDiv;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_htl);


        Bundle bundle=getIntent().getExtras();

        value= bundle.getString("tag");
        
        recviewH=(RecyclerView)findViewById(R.id.recviewHtl);
        recviewH.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<HtlModel> options =
                new FirebaseRecyclerOptions.Builder<HtlModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Hotels").child(value), HtlModel.class)
                        .build();

        htlAdapter=new HtlAdapter(options);
        recviewH.setAdapter(htlAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        htlAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        htlAdapter.stopListening();
    }
}